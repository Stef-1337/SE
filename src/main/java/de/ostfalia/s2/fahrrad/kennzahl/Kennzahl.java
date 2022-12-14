package de.ostfalia.s2.fahrrad.kennzahl;

import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlDistance;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlRotations;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlSpeed;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlTime;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlType;
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

    private Kennzahl(KennzahlType type, String unitA, String unitB) {
        this.type = type;
        this.unitA = unitA;
        this.unitB = unitB;
    }
}
