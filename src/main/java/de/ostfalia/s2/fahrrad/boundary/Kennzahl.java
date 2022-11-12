package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.entity.KennzahlType;
import lombok.Getter;


public enum Kennzahl {
    ROTATIONS(new KennzahlRotations(), "rps", "Umdrehungen"),
    SPEED(new KennzahlSpeed(), "m/s", ""),
    DISTANCE(new KennzahlDistance(), "m/sec", "m"),
    TIME(new KennzahlTime(), "%", "sec");

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
