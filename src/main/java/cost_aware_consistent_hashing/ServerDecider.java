package cost_aware_consistent_hashing;

import static cost_aware_consistent_hashing.Constants.*;

import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class ServerDecider {
    final HashFunction hashFunction = Hashing.hmacMd5("myKey".getBytes());
    final int NUM_REPLICAS = 5;
    private final SortedMap<Integer, Integer> map = new TreeMap<>();

    //Initialize map with replicas 
    public ServerDecider(){
        int count = 0;
        for(int i = 0; i < NUM_SERVERS; i++){
            for(int j = 0; j < NUM_REPLICAS; j++){
                map.put(hashFunction.hashLong(count).asInt(), i);
                count++;
            }
        }
    } 

    public int hash(AlgorithmType algorithmType, Task task, WorkerInfo[] workerInfos){
        int out;
        switch(algorithmType){
            case MODULO : out = moduloHash(task); break;
            case CONSISTENT : out = consistentHash(false, task, workerInfos); break;
            case BOUNDED_LOAD : out = consistentHash(true, task, workerInfos); break;
            default : throw new RuntimeException(String.format("Algorithm Type %s, did not match any configured type", algorithmType));
        }
        return out;
    }
    
    private int moduloHash(Task task){
        return Math.abs(task.getId().hashCode()) % NUM_SERVERS;
    }

    /*
    * Use a tree map to get a sorted order of the elements (imagine a circle)
    * Map the task to a point on the cirlce and then find the next largest
    * For bounded loads we will use the info in workerInfos to decide if we should skip and move on
    */
    private int consistentHash(boolean boundedLoad, Task task, WorkerInfo[] workerInfos){
        int hash = hashFunction.hashBytes(task.getId().toString().getBytes()).asInt();
        if(!boundedLoad){
            if(!map.containsKey(hash)){
                SortedMap<Integer, Integer> tailMap = map.tailMap(hash);
                hash = tailMap.isEmpty() ? map.firstKey() : tailMap.firstKey();
              }
              return map.get(hash);
        }
        return 0;
    }
}
