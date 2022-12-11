package de.ostfalia.s3.entity;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class BicycleData {

int counter;

//@Schedule(second = "*/1",minute = "*",hour = "*",persistent = false)
public void call(){
    System.out.println("TEST"+counter);
    counter++;
}
    public int getCounter() {
        return counter;
    }
}