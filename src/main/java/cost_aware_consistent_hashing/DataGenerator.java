package cost_aware_consistent_hashing;

import static cost_aware_consistent_hashing.Constants.*;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.math3.distribution.CauchyDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

public class DataGenerator {
    private Random random = new Random();
    
    public DataSet getDataset(DataSetType dataSetType){
        DataSet dataset;

        switch(dataSetType){
            case NORMAL: dataset = normalDataSet(); break;
            case UNIFORM: dataset = uniformDataSet(); break;
            case CAUCHY: dataset = caucyDataSet(); break;
            default: throw new RuntimeException(String.format("Dataset Type %s, did not match any configured type", dataSetType));
        }

        return dataset;
    }

    private DataSet normalDataSet(){
        //create normal distribution with mean .05 and sd .05/3 so that 99.7% of events are < .1
        NormalDistribution normalDistribution = new NormalDistribution(.05, .05/3);
        DataSet dataSet = new DataSet();
        Task[] tasks = new Task[NUM_DISTINCT_TASKS];
        //generate costs from sampling from normal distribution
        for(int i=0; i < NUM_DISTINCT_TASKS; i++){
            UUID uuid = UUID.randomUUID();
            Double cost = Math.max(0.001, normalDistribution.sample()); //make sure no 0 cost events
            tasks[i] = new Task(cost, uuid);
        }
        //generate task multiplities from sampling from uniform distribution
        for(int i=0; i < NUM_TASKS; i++){
            dataSet.getTasks().add(tasks[random.nextInt(NUM_DISTINCT_TASKS)]);
        }
        return dataSet;
    }

    private DataSet caucyDataSet(){
        //See https://keisan.casio.com/exec/system/1180573167 for what percentiles of this distribution look like
        CauchyDistribution cauchyDistribution = new CauchyDistribution(.05, .005);
        DataSet dataSet = new DataSet();
        Task[] tasks = new Task[NUM_DISTINCT_TASKS];
        //generate costs from sampling from normal distribution
        for(int i=0; i < NUM_DISTINCT_TASKS; i++){
            UUID uuid = UUID.randomUUID();
            Double cost = Math.max(0.001, cauchyDistribution.sample()); //make sure no 0 cost events
            tasks[i] = new Task(cost, uuid);
        }
        //generate task multiplities from sampling from uniform distribution
        for(int i=0; i < NUM_TASKS; i++){
            dataSet.getTasks().add(tasks[random.nextInt(NUM_DISTINCT_TASKS)]);
        }
        return dataSet;
    }

    private DataSet uniformDataSet(){
        DataSet dataSet = new DataSet();
        Task[] tasks = new Task[NUM_DISTINCT_TASKS];
        //generate costs from sampling from unifrom distribution over (0,.1)
        for(int i=0; i < NUM_DISTINCT_TASKS; i++){
            UUID uuid = UUID.randomUUID();
            Double cost = ((double) random.nextInt(101))/1000;
            tasks[i] = new Task(cost, uuid);
        }
        //generate task multiplities from sampling from uniform distribution
        for(int i=0; i < NUM_TASKS; i++){
            dataSet.getTasks().add(tasks[random.nextInt(NUM_DISTINCT_TASKS)]);
        }
        return dataSet;
    }
}
