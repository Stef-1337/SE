package de.ostfalia.s2.fahrrad.kennzahl.strategy;

import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.entity.ResultBike;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlType;

import java.util.List;

public class KennzahlDistance implements KennzahlType {

    private double getDistance(double rotations) {
        double f = rotations / 4;
        return f * 2.111;
    }

    @Override
    public void apply(BicycleDetailData detailData) {
        for (ResultBike bike : detailData.getDaten()) {
            double rotations = bike.getRotations() * bike.getNumbers();
            double distance = getDistance(rotations);

            detailData.addInterval(bike.getTimestamp());
            detailData.getValues().add(distance);
        }
    }

    @Override
    public double getTotal(List<Bicycle> data) {
        double total = 0;

        for (Bicycle bike : data) {
            total += getDistance(bike.getRotations_per_second());
        }

        return total;
    }

    @Override
    public double getAverage(List<Bicycle> data) {
        return getTotal(data) / data.size();
    }
}
