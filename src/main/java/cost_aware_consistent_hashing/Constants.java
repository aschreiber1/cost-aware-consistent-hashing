package cost_aware_consistent_hashing;

public final class Constants {
    
    private Constants() {
        // restrict instantiation
    }

    public static final int NUM_SERVERS = 10;
    public static final int NUM_TASKS = 10000;
    public static final int NUM_DISTINCT_TASKS = 500;
    public static final Long STOP_WORKER = -1L;
    public static final int BATCH_SIZE = 100;

}