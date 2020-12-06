package org.example.butterfly.core;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

public class TickTock extends TimerTask {
    public static AtomicLong secondsCounter  = new AtomicLong();
    private static final Timer timer = new Timer(true);

    public void run(){
        System.currentTimeMillis();
        secondsCounter.incrementAndGet();
    }
    public static void start(){
        timer.scheduleAtFixedRate(new TickTock(),0,250);
    }

    public static void stop(){
        timer.cancel();
    }

    public static long now(){
        return secondsCounter.get();
    }
}
