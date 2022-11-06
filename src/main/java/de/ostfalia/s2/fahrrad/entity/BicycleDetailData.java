package de.ostfalia.s2.fahrrad.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
public class BicycleDetailData {

    private List<String> intervals;
    private List<Object> values;
    private List<Bicycle> daten;

    public BicycleDetailData(List<Bicycle> daten) {
        intervals = new ArrayList<>();
        values = new ArrayList<>();
        this.daten = daten;
    }

    public int getSize() {
        return intervals.size() < values.size() ? intervals.size() : values.size();
    }

    public String getIntervalString(int index) {
        return intervals.get(index);
    }

    public Object getValue(int index) {
        return values.get(index);
    }

    public static BicycleDetailData DISTANCE(List<Bicycle> data) {
        BicycleDetailData detailData = new BicycleDetailData(data);

        long interval = 1000 * 60 * 60;

        LocalDateTime start, end = null;

        double distance = 0;

        Collections.sort(data, Comparator.comparing(Bicycle::getTimestamp));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (int i = 0; i < data.size(); i++) {
            Bicycle bike = data.get(i);

            if (end == null || bike.getTimestamp().isAfter(end)) {
                end = bike.getTimestamp().plus(interval, ChronoUnit.MILLIS);
                start = bike.getTimestamp();

                if (end != null) {
                    final double finalDistance = distance;
                    detailData.getValues().add(finalDistance);
                    detailData.getIntervals().add(start.format(formatter));
                    distance = 0;
                }
            }

            double f = Double.valueOf(bike.getRotations_per_second()) / 4;
            distance += f * 2.111;
        }

        return detailData;
    }

    public static BicycleDetailData SPEED(List<Bicycle> data){
        BicycleDetailData detailData = new BicycleDetailData(data);
        BicycleDetailData distanceData = DISTANCE(data);
        long interval = 1000 * 60 * 60;

        LocalDateTime start, end = null;

        double speed = 0;

        Collections.sort(data, Comparator.comparing(Bicycle::getTimestamp));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            Bicycle bike = data.get(i);
            count++;
            if (end == null || bike.getTimestamp().isAfter(end)) {
                end = bike.getTimestamp().plus(interval, ChronoUnit.MILLIS);
                start = bike.getTimestamp();

                if (end != null) {
                    double distance = (double) distanceData.getValue(i);
                    speed = distance/ count;
                    final double finalSpeed = speed;
                    detailData.getValues().add(finalSpeed);
                    detailData.getIntervals().add(start.format(formatter));
                    count = 0;
                }
            }
        }
        return detailData;
    }

}
