package cost_aware_consistent_hashing;

import static cost_aware_consistent_hashing.Constants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class ServerDecider {
    //use the MD5 hash function for consistent hashing as it "mixes" well
    final HashFunction hashFunction = Hashing.hmacMd5("myKey".getBytes());
    private final TreeMap<Integer, Integer> map = new TreeMap<>();
    private List<HashFunction> rehashFunctions = new ArrayList<>();
    private Random random = new Random();

    //Initialize map with replicas 
    public ServerDecider(){
        int count = 0;
        //map servers into the treemap so that we can use them in consistent hashing
        for(int i = 0; i < NUM_SERVERS; i++){
            for(int j = 0; j < NUM_REPLICAS; j++){
                map.put(hashFunction.hashLong(count).asInt(), i);
                count++;
            }
        }
        //add rehash functions for our rehash function strategy 
        for(Integer i = 0; i < NUM_SERVERS; i++){
            rehashFunctions.add(Hashing.hmacMd5(i.toString().getBytes()));
        }
    } 

    public int hash(AlgorithmType algorithmType, Task task, WorkerInfo[] workerInfos, BlockingQueue<Task>[] queues){
        int out;
        switch(algorithmType){
            case MODULO : out = moduloHash(task); break;
            case CONSISTENT : out = consistentHash(task); break;
            case BOUNDED_LOAD : out = boundedLoad(task, workerInfos, queues); break;
            case REHASH : out = rehash(task, queues); break;
            default : throw new RuntimeException(String.format("Algorithm Type %s, did not match any configured type", algorithmType));
        }
        return out;
    }
    
    //Basic Algos 101 modulus hash
    private int moduloHash(Task task){
        return Math.abs(task.getId().hashCode()) % NUM_SERVERS;
    }

    /*
    Use a tree map to get a sorted order of the servers (imagine the servers being circle)
    Map the task to a point on the cirlce and then find the server that is closet, but larger to 
    the hash. The key thing here is we are using the same hash function to map both the servers
    and the tasks to the cirlce. 
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

    /*
    * Like bounded loads however instead of going around the cirlce to find next element
    * rehash to a random element and then check again 
    */
    private int rehash(Task task, BlockingQueue<Task>[] queues){
        for(int i=0; i < NUM_SERVERS; i++){
            int hash = rehashFunctions.get(i).hashBytes(task.getId().toString().getBytes()).asInt();
            Entry<Integer, Integer> entry = map.ceilingEntry(hash);
            int server = entry == null ? map.floorEntry(hash).getValue() : entry.getValue();
            if(queues[server].size() <= (1+EPSILON)*((double)BATCH_SIZE/NUM_SERVERS)){
                return server;
            }
        }
        return random.nextInt(NUM_SERVERS); //default to random server if everything is full
    }
}
