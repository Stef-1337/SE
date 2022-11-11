package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.entity.KennzahlType;

import javax.xml.transform.Result;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

public class KennzahlDistance implements KennzahlType {

    @Override
    public void apply(BicycleDetailData detailData) {
        for(ResultBike bike : detailData.getDaten()){
            double rotations = bike.getRotations() * bike.getNumbers();
            double f = rotations / 4;
            double distance = f * 2.111;

            detailData.addInterval(bike.getTimestamp());
            detailData.getValues().add(distance);
        }

//        //TODO logik
//        List<Bicycle> data = detailData.getDaten();
//        LocalDateTime last = data.get(0).getTimestamp();
//
//        double distance = 0;
//
//        data.sort(Comparator.comparing(Bicycle::getTimestamp));
//
//        for (int i = 0; i < data.size(); i++) {
//            Bicycle bike = data.get(i);
//            double f = (double) bike.getRotations_per_second() / 4;
//            distance += f * 2.111;
//
//            if (bike.getTimestamp().isAfter(last.plus(detailData.getStep(), ChronoUnit.MILLIS))) {
//                    detailData.getValues().add(distance);
//                    detailData.getIntervals().add(BicycleDetailData.FORMATTER.format(bike.getTimestamp()));
//                    distance = 0;
//                last = bike.getTimestamp();
//            }
//        }
    }

    @Override
    public double getTotal(List<Bicycle> data) {
        return 0;
    }

    @Override
    public double getAverage(List<Bicycle> data) {
        return 0;
    }
}
