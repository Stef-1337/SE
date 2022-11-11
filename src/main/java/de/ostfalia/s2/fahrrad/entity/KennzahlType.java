package de.ostfalia.s2.fahrrad.entity;

import java.util.List;

public interface KennzahlType {

    void apply(BicycleDetailData detailData);
    double getTotal(List<Bicycle> data);
    double getAverage(List<Bicycle> data);

}
