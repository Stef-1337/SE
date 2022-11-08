package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.entity.KennzahlType;
import lombok.Getter;


public enum Kennzahl {
    ROTATIONS(null),
    SPEED(null),
    DISTANCE(null),
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
