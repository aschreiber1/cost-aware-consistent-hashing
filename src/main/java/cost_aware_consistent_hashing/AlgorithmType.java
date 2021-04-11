package cost_aware_consistent_hashing;

/*
* What algorithm to use when deciding which "host" to map a task to
*/
public enum AlgorithmType {
    MODULO, CONSISTENT, BOUNDED_LOAD, REHASH,
}
