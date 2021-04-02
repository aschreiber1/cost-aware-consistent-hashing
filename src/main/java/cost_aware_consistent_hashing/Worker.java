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
    public void run() {
        try {
            while (true) {
                Task task = queue.take();
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
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
