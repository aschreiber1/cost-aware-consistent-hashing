package cost_aware_consistent_hashing;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Task {
    //amount of time to sleep for, for each task
    private Double cost;
    //used to identify which host to map the job to
    private UUID id;
}
