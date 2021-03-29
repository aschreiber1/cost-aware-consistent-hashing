package cost_aware_consistent_hashing;

import lombok.Getter;
import lombok.Setter;

/**
 * Contains the summary results per experment
 */

@Getter
@Setter
public class ExperimentResults {
    private DataSetType dataSetType;
    private Long totalTime; // number of milliseconds for experiment
}
