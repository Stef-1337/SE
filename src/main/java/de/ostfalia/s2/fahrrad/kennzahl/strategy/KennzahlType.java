package de.ostfalia.s2.fahrrad.kennzahl.strategy;

import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;

import java.util.List;

public interface KennzahlType {

    void apply(BicycleDetailData detailData);
    double getTotal(List<Bicycle> data);
    double getAverage(List<Bicycle> data);

}
