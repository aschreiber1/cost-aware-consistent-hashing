package cost_aware_consistent_hashing;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Contains the summary results per experment
 * We should add extra metrics here that would be interesting 
 */

@Getter
@Setter
@ToString
public class ExperimentResults {
    private DataSetType dataSetType;
    private AlgorithmType algorithmType;
    private Double costVariance;
    private Double costKurtosis;
    private Long totalTime; // number of milliseconds for experiment
    private int maxLoadDiff; //difference between the max loaded server and the min loaded server at any point in the experiment
    private Map<Integer, Double> latencyPercentiles; //will contain percentiles of latency
    private Map<Integer, Double> queuedPercentiles; //will contain percentiles of how long things were queued
    private Map<Integer, Double> costsPercentiles; //percentils of costs of the dataset 
}
