package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.entity.KennzahlType;
import lombok.Getter;


public enum Kennzahl {
    ROTATIONS(new KennzahlRotations(), "Umdrehungen/sec", ""),
    SPEED(new KennzahlSpeed(), "km/h", ""),
    DISTANCE(new KennzahlDistance(), "m/s", ""),
    TIME(new KennzahlTime(), "min", "");

    @Getter
    private KennzahlType type;

    @Getter
    private String unitA;

    @Getter
    private String unitB;

    private Kennzahl(KennzahlType type, String unitA, String unitB){
        this.type = type;
        this.unitA = unitA;
        this.unitB = unitB;
    }

    public void apply(BicycleDetailData data){
        type.apply(data);
    }
}
