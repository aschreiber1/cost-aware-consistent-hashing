package cost_aware_consistent_hashing;

import lombok.Getter;
import lombok.Setter;

/**
 * Contains the summary results per experment
 * We should add extra metrics here that would be interesting 
 */

@Getter
@Setter
public class ExperimentResults {
    private DataSetType dataSetType;
    private Long totalTime; // number of milliseconds for experiment
}
