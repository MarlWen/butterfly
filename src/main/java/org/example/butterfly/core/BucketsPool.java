package org.example.butterfly.core;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BucketsPool {
    private HashMap<String,LeakyBucket> pool;
    private final static Logger LOGGER = Logger.getLogger("ButterFly.LeakyBucketsPool");

    public BucketsPool(int capacity){
        pool = new HashMap<>((capacity<2048)?2048:capacity);
    }

    public LeakyBucket createBucket(String key, int rate, int volume){
        if(pool.containsKey(key)){
            pool.remove(key);
        }
        LOGGER.log(Level.INFO,"Key collision occurs on key:".concat(key));
        LeakyBucket bucket = new LeakyBucket(rate,volume);
        pool.put(key, bucket);
        return bucket;
    }

    public boolean deleteBucket (String key){
        if (pool.containsKey(key)){
            pool.remove(key);
            return true;
        }
        return false;
    }

    public boolean updateBucket (String key, int rate, int volume){
        if(pool.containsKey(key)){
            pool.get(key)
                    .setVolume(volume)
                    .setRate(rate);
            return true;
        }
        return false;
    }
}
