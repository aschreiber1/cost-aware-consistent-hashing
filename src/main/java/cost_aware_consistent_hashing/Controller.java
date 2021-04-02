package cost_aware_consistent_hashing;

import lombok.Getter;
import static cost_aware_consistent_hashing.Constants.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Getter
public class Controller {
    DataGenerator dataGenerator = new DataGenerator();
    
    public ExperimentResults runExperiment(DataSetType dataSetType, AlgorithmType algorithmType) throws InterruptedException{
        System.out.println(String.format("Starting Experiment with Dataset Type %s", dataSetType));
        ExperimentResults results = new ExperimentResults();
        DataSet dataSet = dataGenerator.getDataset(dataSetType);
        ServerDecider serverDecider = new ServerDecider();

        WorkerInfo[] workerInfos = new WorkerInfo[NUM_SERVERS];
        BlockingQueue<Task>[] queues = new ArrayBlockingQueue[NUM_SERVERS];

        for(int i=0; i < NUM_SERVERS; i++){
            workerInfos[i] = new WorkerInfo();
            queues[i] = new ArrayBlockingQueue<>(BATCH_SIZE);
            new Thread(new Worker(queues[i], workerInfos[i])).start();
        }

        final long startTime = System.currentTimeMillis();

        int numBatches = NUM_TASKS/BATCH_SIZE;
        for(int i=0; i < numBatches; i++){
            System.out.println(String.format("Working on Batch %d of %d", i, numBatches));
            //publish batch of tasks to appropriate queues
            for(int j = 0; j < BATCH_SIZE; j++){
                Task task = dataSet.getTasks().removeFirst();
                //Thie currenty uses regular hashing, we should implement consistent hashing
                int serverNum = serverDecider.hash(algorithmType, task, workerInfos);
                queues[serverNum].add(task);
            }
            //wait for all queues to be cleared
            while(true){
                boolean allQueuesCleared = true;
                for(BlockingQueue<Task> queue : queues){
                    if(!queue.isEmpty()){
                        allQueuesCleared = false;
                        break;
                    }
                }
                if(allQueuesCleared){
                    break;
                }
                Thread.sleep(1L); //backoff 
            }
        }

        final long endTime = System.currentTimeMillis();

        results.setDataSetType(dataSetType);
        results.setTotalTime(endTime-startTime);

        System.out.println(String.format("Experiment with Dataset Type %s took %d", dataSetType, endTime-startTime));

        return results;
    }
}
