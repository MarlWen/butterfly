package org.example.butterfly.core.butterfly.core.event;

public class TickEvent extends Event {
    public void TickEvent(){
        type = EventType.tick;
    }
}
