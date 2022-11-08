package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.entity.KennzahlType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KennzahlTime implements KennzahlType {


    @Override
    public void apply(BicycleDetailData detailData) {
        List<Bicycle> data = detailData.getDaten();
        double time = 0;
        LocalDateTime last = data.get(0).getTimestamp();

        Collections.sort(data, Comparator.comparing(Bicycle::getTimestamp));


        for (int i = 0; i < data.size(); i++) {
            Bicycle bike = data.get(i);
            if (bike.getRotations_per_second() > 0) {
                time++;
            }

            if (bike.getTimestamp().isAfter(last.plus(detailData.getStep(), ChronoUnit.MILLIS))) {
                detailData.getValues().add(time);
                detailData.getIntervals().add(BicycleDetailData.FORMATTER.format(bike.getTimestamp()));
                time = 0;
                last = bike.getTimestamp();
            }
        }
    }
}
