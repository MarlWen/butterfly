package org.example.butterfly.core;

import org.example.butterfly.core.butterfly.core.event.*;

public class LeakyBucket {
    private int remainings;
    private int bucketSize;
    private int refillRate;
    private int timeOfLastRefill = Integer.MIN_VALUE;
    private long[][] passesPerSec = new long[16][2];
    private static final long BIT_MASK = 0x0000000F;

    public LeakyBucket setRate(int rate){
        if (rate > 0) this.refillRate = rate;
        else throw new IllegalArgumentException("Argument rate: must be positive integer.");
        return this;
    }

    public LeakyBucket setSize(int size){
        if (size > 0 ){
            this.bucketSize = size;
        } else {
            throw new IllegalArgumentException("Argument size: must be a positive integer.");
        }
        return this;
    }

    public LeakyBucket tryRefilling(int now){

        if (now > timeOfLastRefill){
            int refillUpTo = remainings + refillRate * (now - timeOfLastRefill);
            remainings = refillUpTo >= bucketSize ? bucketSize : refillUpTo;
            timeOfLastRefill = now;
        }
        return this;

    }


    public boolean consume(Event e){
        if (e == null) throw new NullPointerException("One Event object must be passed in");
        int now = (int)(e.timeStamp - Throttler.startTime);
        now = now > 0 ? now : 0;
        tryRefilling(now);
        if (remainings <= 0) return false;
        --remainings;
        //statistics
        short index = (short) (now & BIT_MASK);
        if (passesPerSec[index][0] != now){
            passesPerSec[index][0] = now;
            passesPerSec[index][1] = 1;
        }else{
            passesPerSec[index][1]++;
        }

        return true;
    }

    public LeakyBucket(int rate, int size){
        if (rate > 0 && size > 0 && size > rate){
            this.remainings = this.refillRate = rate;
            this.bucketSize = size;
        } else {
            throw new IllegalArgumentException("arguments 'rate' and 'volume' must be greater than zero");
        }

    }


    public int passesPerSecond(){
        long current = TickTock.now();
        int sum = 0;
        int n = 0;
        for (int i = 0; i <16 ; i++){
            if (passesPerSec[i][0] >= current - 16){
                n++;
                sum += passesPerSec[i][1];
            }
        }
        return (n>0)? sum / n: 0;
    }

}
