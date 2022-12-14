package de.ostfalia.s2.fahrrad.data.template;

import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlType;
import de.ostfalia.s2.fahrrad.entity.ResultBike;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public abstract class DataOperation implements Serializable {

    private List<Bicycle> data;
    private List<ResultBike> result;

    @Getter
    private double total, average;

    private KennzahlType type;

    public List<ResultBike> operateData(KennzahlType type, List<Bicycle> data, long step) {
        init(type, data);
        this.data = smooth(data);
        calculateSteps(step);
        calculateTotal();
        calculateAverage();

        return result;
    }

    public void init(KennzahlType type, List<Bicycle> data) {
        this.data = new ArrayList<>();
        this.type = type;
    }

    public void calculateTotal() {
        total = type.getTotal(data);
    }

    public void calculateAverage() {
        average = type.getAverage(data);
    }

    public abstract List<Bicycle> smooth(List<Bicycle> data);

    public void calculateSteps(long step) {
        result = new ArrayList<>();

        if (data.size() > 0) {
            LocalDateTime last = data.get(0).getTimestamp();

            Double value = 0.0, active = 0.0;
            int count = 0;

            for (int i = 0; i < data.size(); i++) {
                Bicycle bike = data.get(i);
                value += bike.getRotations_per_second();
                if (bike.getRotations_per_second() > 0) active++;
                count++;

                if (i == data.size() - 1 || bike.getTimestamp().isAfter(last.plus(step, ChronoUnit.MILLIS))) {
                    double average = value / count;

                    ResultBike result = new ResultBike(bike.getChannel(), step, count, bike.getTimestamp(), average, active);
                    this.result.add(result);

                    last = bike.getTimestamp();
                    count = 0;
                    value = 0.0;
                    active = 0.0;
                }
            }
        } else System.out.println("No data found");
    }

    public List<Bicycle> getData() {
        return data;
    }

}
