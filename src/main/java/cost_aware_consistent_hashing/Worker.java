package cost_aware_consistent_hashing;

import java.util.concurrent.BlockingQueue;

import lombok.Getter;

import static cost_aware_consistent_hashing.Constants.*;

@Getter
public class Worker implements Runnable {
    private BlockingQueue<Task> queue;
    private WorkerInfo workerInfo;
    
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
                Thread.sleep(task.getCost());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
