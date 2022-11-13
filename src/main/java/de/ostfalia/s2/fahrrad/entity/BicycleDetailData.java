package de.ostfalia.s2.fahrrad.entity;

import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class BicycleDetailData {

    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM HH:mm:ss");

    private KennzahlType type;

    private List<String> intervals;
    private List<Object> values;
    private List<ResultBike> daten;

    private String name;
    private Long step;

    public BicycleDetailData(List<ResultBike> daten, String name) {
        intervals = new ArrayList<>();
        values = new ArrayList<>();

        this.daten = daten;
        this.name = name;
    }

    public BicycleDetailData(List<ResultBike> daten, String name, long step, KennzahlType type){
        this(daten, name);

        this.step = step;
        this.type = type;

        daten.sort(Comparator.comparing(ResultBike::getTimestamp));

        if(daten.size() > 0){
            type.apply(this);
        }
    }

    public void addInterval(LocalDateTime timestamp){
        getIntervals().add(FORMATTER.format(timestamp));
    }

    public int getSize() {
        return Math.min(intervals.size(), values.size());
    }

    public String getIntervalString(int index) {
        return intervals.get(index);
    }

    public Object getValue(int index) {
        return values.get(index);
    }

}
