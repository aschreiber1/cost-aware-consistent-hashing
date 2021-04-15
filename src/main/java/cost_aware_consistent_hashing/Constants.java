package cost_aware_consistent_hashing;

public final class Constants {
    
    private Constants() {
        // restrict instantiation
    }

    //how many threads to use
    public static final int NUM_SERVERS = 10;
    //For contrived datasets, how many tasks are there
    public static final int NUM_TASKS = 10000;
    //For contrived datasets, how many of the tasks are distinct (we will then randomally generate repeats)
    public static final int NUM_DISTINCT_TASKS = 2500;
    //Used to tell the workers to stop draining the queues at the end of the experiment
    public static final Long STOP_WORKER = -1L;
    //how many tasks to publish at once
    public static final int BATCH_SIZE = 100;
    //how long to wait after publishing a batch until the next one is submitted
    public static final Long BATCH_TIME = 500L; 
    //Epsilon, as in the parameter descibed in the paper Consistent Hasing with Bounded Loads
    public static final Double EPSILON = .3;
    //In consistent hasing we generally dont just map the servers in a circle but instead insert duplicates as well
    //this improves even load and scaling behaivor 
    public static final int NUM_REPLICAS = 5;
    //how effective is cache, in percentage reduction in total task time 
    public static final Double CACHE_EFFECTIVENESS = .75;
    //number of times to run each experiment
    public static final int NUM_EXPERIMENTS = 1; 

}
