package de.ostfalia.s3.entity;

import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlDistance;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlSpeed;

import java.time.LocalDateTime;
import java.util.*;


public class Datacollector {
    int id;

    int time;
    private static Datacollector datacollector = null;
    BicycleService bs = new BicycleService();

    public Datacollector(int id, int time){
        this.id = id;
        this.time = time;
        System.out.println("private");
    }

    public static Datacollector getInstance(int id, int time){
        if (datacollector == null) {
            datacollector = new Datacollector(id, time);
            System.out.println("private");
        }
        System.out.println("static");
        return datacollector;
    }


    public double speed(){
        List<Bicycle> list =  bs.getFahrradDaten(id, LocalDateTime.now(), LocalDateTime.now().minusMinutes(time), 1);
        KennzahlSpeed tmp = new KennzahlSpeed();
        return tmp.getAverage(list);
    }


    public double distanz(int channel){
        if (channel != id) {
            setId(channel);
        }
            List<Bicycle> list = bs.getFahrradDaten(id, LocalDateTime.now(), LocalDateTime.now().minusMinutes(time), 1);
        KennzahlDistance tmp = new KennzahlDistance();
        return tmp.getTotal(list);
    }

    public void setId(int id) {
        this.id = id;
    }
}