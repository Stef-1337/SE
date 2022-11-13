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
import java.util.concurrent.TimeUnit;

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
