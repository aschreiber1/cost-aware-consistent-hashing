package cost_aware_consistent_hashing;

import java.util.ArrayDeque;
import java.util.Deque;

import lombok.Getter;
import lombok.Setter;

import static cost_aware_consistent_hashing.Constants.*;

@Getter
@Setter
//Here a datasets is just a queue of tasks to be done
public class DataSet {
    //Queue of tasks
    private Deque<Task> tasks = new ArrayDeque<>(NUM_TASKS);
}
