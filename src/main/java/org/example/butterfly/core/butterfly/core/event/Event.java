package org.example.butterfly.core.butterfly.core.event;

enum EventType{
    tick,
    createBucket,
    deleteBucket,
    updateBucket,
    consume
}
public abstract class Event {
    short version;
    public EventType type;
    public long timeStamp;
    public void Event(){
        version = 1;
        timeStamp = System.currentTimeMillis();
    }
}


