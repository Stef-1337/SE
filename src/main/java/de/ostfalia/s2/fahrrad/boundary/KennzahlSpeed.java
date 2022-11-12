package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.entity.KennzahlType;

import javax.xml.transform.Result;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

public class KennzahlSpeed implements KennzahlType {

    private double getSpeed(double rotations, long step){
        double f = rotations / 4;
        double distance = f * 2.111;

        return (distance / step) * 3.6;
    }

    @Override
    public void apply(BicycleDetailData detailData) {
        for(ResultBike bike : detailData.getDaten()){
            double rotations = bike.getRotations() * bike.getNumbers();

            detailData.addInterval(bike.getTimestamp());
            detailData.getValues().add(getSpeed(rotations, bike.getStep()));
        }
      //  Kennzahl.DISTANCE.apply(detailData);
//        List<Bicycle> data = detailData.getDaten();
//
//        LocalDateTime last = data.get(0).getTimestamp();
//
//        double speed = 0;
//        double distance = 0;
//        double count = 0;
//        data.sort(Comparator.comparing(Bicycle::getTimestamp));
//
//        for (int i = 0; i < data.size(); i++) {
//            Bicycle bike = data.get(i);
//            double f = (double) bike.getRotations_per_second() / 4;
//            distance += f * 2.111;
//            count++;
//            if (bike.getTimestamp().isAfter(last.plus(detailData.getStep(), ChronoUnit.MILLIS))) {
//            //    speed = (double) detailData.getValue(i) / count;
//                speed = distance / count;
//                detailData.getValues().add(speed);
//                detailData.getIntervals().add(BicycleDetailData.FORMATTER.format(bike.getTimestamp()));
//                count = 0;
//                distance = 0;
//                last = bike.getTimestamp();
//            }
//        }
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
