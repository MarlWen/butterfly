package org.example.butterfly.core;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        TickTock.start();
        for (int i = 0 ; i <20; i++){
            System.out.println("Checking sent at: "
                    + LocalDateTime.ofInstant(Instant.now(),
                    ZoneId.systemDefault())
                    + "Counter:"
                    + TickTock.secondsCounter.get());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        TickTock.stop();
        System.out.println( "Hello World!" );
    }
}
