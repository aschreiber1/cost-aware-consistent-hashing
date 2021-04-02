package cost_aware_consistent_hashing;

import static cost_aware_consistent_hashing.Constants.*;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class ServerDecider {
    final HashFunction hashFunction = Hashing.hmacMd5("myKey".getBytes());
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

    public int hash(AlgorithmType algorithmType, Task task, WorkerInfo[] workerInfos, BlockingQueue<Task>[] queues){
        int out;
        switch(algorithmType){
            case MODULO : out = moduloHash(task); break;
            case CONSISTENT : out = consistentHash(task); break;
            case BOUNDED_LOAD : out = boundedLoad(task, workerInfos, queues); break;
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
    */
    private int consistentHash(Task task){
        int hash = hashFunction.hashBytes(task.getId().toString().getBytes()).asInt();
        if(!map.containsKey(hash)){
            SortedMap<Integer, Integer> tailMap = map.tailMap(hash);
            hash = tailMap.isEmpty() ? map.firstKey() : tailMap.firstKey();
        }
        return map.get(hash);
    }

    /*
    * Implement Consistent Hashing with Bounded Load
    * Use constant Epsilon, to make sure that load per server is less than (1+eplsilon) the average load
    */
    private int boundedLoad(Task task, WorkerInfo[] workerInfos, BlockingQueue<Task>[] queues){
        int hash = hashFunction.hashBytes(task.getId().toString().getBytes()).asInt();
        SortedMap<Integer, Integer> tailMap = map.tailMap(hash);
        for(Map.Entry<Integer, Integer> entry : tailMap.entrySet()){
            int server = entry.getValue();
            if(queues[server].size() <= (1+EPSILON)*((double)BATCH_SIZE/NUM_SERVERS)){
                return server;
            }
        }
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            int server = entry.getValue();
            if(queues[server].size() <= (1+EPSILON)*((double)BATCH_SIZE/NUM_SERVERS)){
                return server;
            }
        }
        //in case we went all the way around and did not find a server (should be impossible)
        return map.get(tailMap.firstKey());
    }
}
