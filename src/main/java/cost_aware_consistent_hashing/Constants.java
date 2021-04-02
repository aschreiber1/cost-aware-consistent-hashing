package cost_aware_consistent_hashing;

public final class Constants {
    
    private Constants() {
        // restrict instantiation
    }

    public static final int NUM_SERVERS = 10;
    public static final int NUM_TASKS = 10000;
    public static final int NUM_DISTINCT_TASKS = 2500;
    public static final Long STOP_WORKER = -1L;
    public static final int BATCH_SIZE = 100;
    public static final Double EPSILON = .3;
    public static final int NUM_REPLICAS = 5;
    //how effective is cache, in percentage reduction in total task time 
    public static final Double CACHE_EFFECTIVENESS = .75;

}
