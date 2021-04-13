package cost_aware_consistent_hashing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

import lombok.Getter;

import static cost_aware_consistent_hashing.Constants.*;

@Getter
public class Worker implements Runnable {
    private BlockingQueue<Task> queue;
    private WorkerInfo workerInfo;
    private HashSet<UUID> cache = new HashSet<>();
    
    public Worker(BlockingQueue<Task> queue, WorkerInfo workerInfo) {
        this.queue = queue;
        this.workerInfo = workerInfo;
    }
    /**
     * Workers have a simple life cycle. While they are running repeate the following steps
     * 1. Try to take a task from the queue, if it is empty wait until their is a task
     * 2. If the task is the stope worker trigger, shut down
     * 3. Check if the task exists in the cache
     * 4. If it is not in the cache, sleep for the cost and then add it to the cache 
     * 5. If it is in the cahce, sleep for (1-CACHE_EFFECTIVENESS)*Cost. I.e. cache effectiveness of 1 reduces sleep time to 0
     * 
     */
    public void run() {
        try {
            while (true) {
                Task task = queue.take();
                task.setDequeuedTime(System.currentTimeMillis());
                if (task.getCost().equals(STOP_WORKER)) {
                    return;
                }
                if(!cache.contains(task.getId())){
                    Thread.sleep(task.getCost());
                    cache.add(task.getId());
                }
                else{
                    Thread.sleep((long)(task.getCost()*(1-CACHE_EFFECTIVENESS)));
                }
                task.setFinishTime(System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
