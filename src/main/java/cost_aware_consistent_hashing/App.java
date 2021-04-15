package cost_aware_consistent_hashing;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import static cost_aware_consistent_hashing.Constants.*;

/**
 * Currently unused but logic to run all the experiments and save the results could go here
 *
 */
public class App 
{
    private static final String[] HEADERS = {"dataSetType", "algorithm", "totalTime",
        "maxLoadDiff", "costKurtosis", "costVariance", "latency_p25", "latency_p50", "latency_p75",
        "latency_p100", "cost_p25", "cost_p50", "cost_p75", "cost_p100", "queued_p25", "queued_p50",
        "queued_p75", "queued_p100" };

    public static void main( String[] args ) throws InterruptedException, IOException
    {
        DataGenerator dataGenerator = new DataGenerator();
        Controller controller = new Controller();
        List<ExperimentResults> results = new ArrayList<>();

        for(DataSetType dataSetType : DataSetType.values()) {
            for(int i = 0; i < NUM_EXPERIMENTS; i++){
                DataSet dataSet = dataGenerator.getDataset(dataSetType);
                for(AlgorithmType algorithmType : AlgorithmType.values()){
                    ExperimentResults result = controller.runExperiment(dataSet, algorithmType);
                    results.add(result);
                }
            }
       }
        writeCSV(results);
    }

    /**
     * 
     * Write results to CSV, messy I know but for this just trying to get this done
     * @throws IOException
     */
    private static void writeCSV(List<ExperimentResults> results) throws IOException{
        Long currentTime = System.currentTimeMillis();
        String fileName = String.format("results_%s.csv", currentTime);
        FileWriter out = new FileWriter(fileName);
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            for(ExperimentResults result : results){
                printer.printRecord(result.getDataSetType(), result.getAlgorithmType(), result.getTotalTime(), 
                result.getMaxLoadDiff(), result.getCostKurtosis(), result.getCostVariance(),
                result.getLatencyPercentiles().get(25), result.getLatencyPercentiles().get(50),
                result.getLatencyPercentiles().get(75),result.getLatencyPercentiles().get(100),
                result.getCostsPercentiles().get(25), result.getCostsPercentiles().get(50),
                result.getCostsPercentiles().get(75),result.getCostsPercentiles().get(100),
                result.getQueuedPercentiles().get(25), result.getQueuedPercentiles().get(50),
                result.getQueuedPercentiles().get(75),result.getQueuedPercentiles().get(100));
            }
        }
        System.out.println(String.format("Saved File %s", fileName));
    }
}
