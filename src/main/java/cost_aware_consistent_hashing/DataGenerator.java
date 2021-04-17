package cost_aware_consistent_hashing;

import static cost_aware_consistent_hashing.Constants.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.distribution.CauchyDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.ZipfDistribution;

public class DataGenerator {
    private Random random = new Random();
    
    //Generate a Dataset type from one of the known datasettypes
    public DataSet getDataset(DataSetType dataSetType) throws IOException{
        DataSet dataset;

        switch(dataSetType){
            case CONSTANT: dataset = constantDataSet(); break;
            case NORMAL: dataset = normalDataSet(); break;
            case UNIFORM: dataset = uniformDataSet(); break;
            case CAUCHY: dataset = caucyDataSet(); break;
            case ZIPF: dataset = zipfDataSet(); break;
            case CRYPTO: dataset = cryptoDataSet(); break;
            default: throw new RuntimeException(String.format("Dataset Type %s, did not match any configured type", dataSetType));
        }
        dataset.setType(dataSetType);

        return dataset;
    }

    //genreate normal distribution by sampling from a normal distribution 
    private DataSet normalDataSet(){
        //create normal distribution with mean .05 and sd .05/3 so that 99.7% of events are < .1
        NormalDistribution normalDistribution = new NormalDistribution(TARGET_MEAN, 50/3D);
        DataSet dataSet = new DataSet();
        Task[] tasks = new Task[NUM_DISTINCT_TASKS];
        //generate costs from sampling from normal distribution
        for(int i=0; i < NUM_DISTINCT_TASKS; i++){
            UUID uuid = UUID.randomUUID();
            Long cost = (long) Math.max(1, normalDistribution.sample()); //make sure no 0 cost events
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
        CauchyDistribution cauchyDistribution = new CauchyDistribution(TARGET_MEAN, 5);
        DataSet dataSet = new DataSet();
        Task[] tasks = new Task[NUM_DISTINCT_TASKS];
        //generate costs from sampling from normal distribution
        for(int i=0; i < NUM_DISTINCT_TASKS; i++){
            UUID uuid = UUID.randomUUID();
            Long cost = (long) Math.max(1, cauchyDistribution.sample()); //make sure no 0 cost events
            tasks[i] = new Task(cost, uuid);
        }
        //generate task multiplities from sampling from uniform distribution
        for(int i=0; i < NUM_TASKS; i++){
            dataSet.getTasks().add(tasks[random.nextInt(NUM_DISTINCT_TASKS)]);
        }
        return dataSet;
    }

    private DataSet zipfDataSet(){
        //this produces a mean of close to 50 which is what the others have
        ZipfDistribution zipfDistribution = new ZipfDistribution(NUM_DISTINCT_TASKS, 1.15);
        DataSet dataSet = new DataSet();
        Task[] tasks = new Task[NUM_DISTINCT_TASKS];
        //generate costs from sampling from normal distribution
        for(int i=0; i < NUM_DISTINCT_TASKS; i++){
            UUID uuid = UUID.randomUUID();
            Long sample = Math.max(1L, zipfDistribution.sample()); //make sure no 0 cost events
            tasks[i] = new Task(sample, uuid);
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
            Long upperBound = TARGET_MEAN*2+1;
            Long cost = (long) random.nextInt(upperBound.intValue());
            tasks[i] = new Task(cost, uuid);
        }
        //generate task multiplities from sampling from uniform distribution
        for(int i=0; i < NUM_TASKS; i++){
            dataSet.getTasks().add(tasks[random.nextInt(NUM_DISTINCT_TASKS)]);
        }
        return dataSet;
    }

    /**
     * Dataset with all 50 mills time, i.e. dataset like what was discussed in the paper
     */
    private DataSet constantDataSet(){
        DataSet dataSet = new DataSet();
        Task[] tasks = new Task[NUM_DISTINCT_TASKS];
        //generate taks with all 50 mills
        for(int i=0; i < NUM_DISTINCT_TASKS; i++){
            UUID uuid = UUID.randomUUID();
            tasks[i] = new Task(TARGET_MEAN, uuid);
        }
        for(int i=0; i < NUM_TASKS; i++){
            dataSet.getTasks().add(tasks[random.nextInt(NUM_DISTINCT_TASKS)]);
        }
        return dataSet;
    }

    /**
     * Get crypto dataset
     * @throws IOException
     */
    private DataSet cryptoDataSet() throws IOException{
        DataSet dataSet = new DataSet();
        String[] headers = {"symbol", "shares_traded"};
        Reader in = new FileReader("src/main/resources/crypto.csv");
        Map<Integer, UUID> map = new HashMap<>();
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
          .withHeader(headers)
          .withFirstRecordAsHeader()
          .parse(in);
        List<Double> costs = new ArrayList<>();
        int count = 0;
        for (CSVRecord record : records) {
            costs.add(Double.parseDouble(record.get("shares_traded")));
            map.put(count, UUID.randomUUID());
            count++;
        }
        Double mean = costs.stream().mapToDouble(x->x).average().getAsDouble();
        Double multiplier = TARGET_MEAN/mean;
        for(int i=0; i < NUM_TASKS; i++){
            int randInt = random.nextInt(costs.size());            
            Double cost = costs.get(randInt)*multiplier;
            Task task = new Task(cost.longValue(), map.get(randInt));
            dataSet.getTasks().add(task);
        }
        mean = dataSet.getTasks().stream().map(x->x.getCost())
        .mapToDouble(x->x).average().getAsDouble();
        return dataSet;
    }
}
