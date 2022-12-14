package de.ostfalia.s2.fahrrad.kennzahl.strategy;

import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.entity.ResultBike;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlType;

import java.util.List;

public class KennzahlSpeed implements KennzahlType {

    private double getSpeed(double rotations, long step){
        double f = rotations / 4;
        double distance = f * 2.111;

        return (distance / step);
    }

    @Override
    public void apply(BicycleDetailData detailData) {
        for(ResultBike bike : detailData.getDaten()){
            double rotations = bike.getRotations() * bike.getNumbers();

            detailData.addInterval(bike.getTimestamp());
            detailData.getValues().add(getSpeed(1000 * rotations, bike.getStep()));
        }
    }

    @Override
    public double getTotal(List<Bicycle> data) {
        double total = 0;
        for(Bicycle bike : data){
            total += getSpeed(bike.getRotations_per_second(), 1);
        }

        return total;
    }

    @Override
    public double getAverage(List<Bicycle> data) {
        return getTotal(data) / data.size();
    }

}
