package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.entity.KennzahlType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class KennzahlRotations implements KennzahlType {

    private double getRotations(double rotations){
        return rotations / 4;
    }

    @Override
    public void apply(BicycleDetailData detailData) {
        for(ResultBike bike : detailData.getDaten()){
            detailData.getValues().add(getRotations(bike.getRotations()));
            detailData.getIntervals().add(BicycleDetailData.FORMATTER.format(bike.getTimestamp()));
        }

//        List<Bicycle> data = detailData.getDaten();
//
//        LocalDateTime last = data.get(0).getTimestamp();
//
//        double rotations = 0, count = 0; //avg
//
//        for (int i = 0; i < data.size(); i++) {
//            Bicycle bike = data.get(i);
//            rotations += bike.getRotations_per_second();
//            count++;
//
//            if (bike.getTimestamp().isAfter(last.plus(detailData.getStep(), ChronoUnit.MILLIS))) {
//                double average = rotations / count;
//
//                detailData.getValues().add(average);
//                detailData.getIntervals().add(BicycleDetailData.FORMATTER.format(bike.getTimestamp()));
//
//                last = bike.getTimestamp();
//                count = 0;
//                rotations = 0;
//            }
//        }
    }

    @Override
    public double getTotal(List<Bicycle> data) {
        double total = 0;
        for(Bicycle bike : data){
            total += getRotations(bike.getRotations_per_second());
        }

        return total;
    }

    @Override
    public double getAverage(List<Bicycle> data) {
        return getTotal(data) / data.size();
    }
}
