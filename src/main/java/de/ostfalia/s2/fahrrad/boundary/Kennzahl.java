package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.entity.KennzahlType;
import lombok.Getter;


public enum Kennzahl {
    ROTATIONS(new KennzahlRotations()),
    SPEED(new KennzahlSpeed()),
    DISTANCE(new KennzahlSpeed()),
    TIME(null);

    @Getter
    private KennzahlType type;

    private Kennzahl(KennzahlType type){
        this.type = type;
    }

    public void apply(BicycleDetailData data){
        type.apply(data);
    }
}
