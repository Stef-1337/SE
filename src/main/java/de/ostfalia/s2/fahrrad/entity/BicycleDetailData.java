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

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

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

    public static BicycleDetailData DEFAULT(List<Bicycle> data, long step){
        BicycleDetailData detailData = new BicycleDetailData(data);
        System.out.println("Default");

        data.sort(Comparator.comparing(Bicycle::getTimestamp));

        LocalDateTime last = LocalDateTime.MIN;

        double rotations = 0, count = 0; //avg

        for(int i = 0; i < data.size(); i++){
            Bicycle bike = data.get(i);
            rotations += bike.getRotations_per_second();
            count++;

            if(bike.getTimestamp().isAfter(last.plus(step, ChronoUnit.MILLIS))){
                double average = rotations / count;

                detailData.getValues().add(average);
                detailData.getIntervals().add(FORMATTER.format(bike.getTimestamp()));

                last = bike.getTimestamp();
                count = 0;
                rotations = 0;
            }
        }

        return detailData;
    }

    public static BicycleDetailData DISTANCE(List<Bicycle> data, long step) {
        BicycleDetailData detailData = new BicycleDetailData(data);

        LocalDateTime start, end = null;

        double distance = 0;

        data.sort(Comparator.comparing(Bicycle::getTimestamp));

        for (int i = 0; i < data.size(); i++) {
            Bicycle bike = data.get(i);

            if (end == null || bike.getTimestamp().isAfter(end)) {
                end = bike.getTimestamp().plus(step, ChronoUnit.MILLIS);
                start = bike.getTimestamp();

                if (end != null) {
                    final double finalDistance = distance;
                    detailData.getValues().add(finalDistance);
                    detailData.getIntervals().add(start.format(FORMATTER));
                    distance = 0;
                }
            }

            double f = (double) bike.getRotations_per_second() / 4;
            distance += f * 2.111;
        }

        return detailData;
    }
}
