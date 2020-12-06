package org.example.butterfly.core;

import org.example.butterfly.core.butterfly.core.event.Event;
import org.jctools.queues.MpscArrayQueue;

import java.util.concurrent.atomic.AtomicBoolean;

public class Throttler implements Runnable {
    private ThreadLocal<BucketsPool> buckets = ThreadLocal.withInitial(()->{return new BucketsPool(2048);});
    private Thread executor;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private MpscArrayQueue<Event> queue;
    public static long startTime;
    private int elapsedSeconds = Integer.MIN_VALUE;
    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        while (true){

        }
    }

    public void updateTime(long time){
        if (time > startTime){
            elapsedSeconds += (time - startTime)/1000;
        }
    }

    public void start(){
        if (executor == null){
            executor = new Thread(new Throttler(),"Throttler");
        };
        executor.start();
        isRunning.set(true);
    }

    public boolean stop(){
        return isRunning.compareAndSet(true,false);
    }
}
