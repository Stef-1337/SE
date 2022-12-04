package de.ostfalia.s3.entity;

import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.kennzahl.Kennzahl;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlDistance;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlSpeed;


import java.time.LocalDateTime;
import java.util.*;

@ Singelton
public class Datacollector {
    int id;
    List<Bicycle> list;
    int time;

    public Datacollector(int id, int time){
        BicycleService bs = null;
        this.list = bs.getFahrradDaten(id, LocalDateTime.now(), LocalDateTime.now().minusMinutes(time), 1);
        this.id = id;
    }

    public double speed(){
        KennzahlSpeed tmp = null;
        return tmp.getAverage(list);
    }

    public double distanz(){
        KennzahlDistance tmp = null;
        return tmp.getTotal(list);
    }



}