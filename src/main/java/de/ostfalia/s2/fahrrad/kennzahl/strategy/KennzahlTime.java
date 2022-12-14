package de.ostfalia.s2.fahrrad.kennzahl.strategy;

import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.entity.ResultBike;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlType;

import java.util.List;

public class KennzahlTime implements KennzahlType {


    @Override
    public void apply(BicycleDetailData detailData) {
        for (ResultBike bike : detailData.getDaten()) {
            double active = bike.getActive();

            double minuten = active / 60;

            detailData.getValues().add(minuten);
            detailData.addInterval(bike.getTimestamp());
        }
    }

    @Override
    public double getTotal(List<Bicycle> data) {
        double total = 0;
        for (Bicycle bike : data) {
            if (bike.getRotations_per_second() > 0) total++;
        }
        return total;
    }

    @Override
    public double getAverage(List<Bicycle> data) {
        return 100 * (getTotal(data) / data.size());
    }

}
