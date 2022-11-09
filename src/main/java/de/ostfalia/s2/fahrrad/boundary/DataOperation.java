package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.entity.KennzahlType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public abstract class DataOperation {

    private List<Bicycle> data;
    private KennzahlType type;
    private List<ResultBike> result;
    private Double total, average;

    public List<ResultBike> operateData(KennzahlType type, List<Bicycle> data, long step){
        init(type, data);
        smooth();
        calculateSteps(step);
        calculateTotal();
        calculateAverage();

        return result;
    }

    public void init(KennzahlType type, List<Bicycle> data){
        this.data = data;
        this.type = type;
    }

    public void calculateTotal(){
        total = type.getTotal(data);
    }

    public void calculateAverage(){
        average = type.getAverage(data);
    }

    public void smooth(){

    }

    public void calculateSteps(long step){
        result = new ArrayList<>();

        LocalDateTime last = data.get(0).getTimestamp();

        Double value = 0.0, active = 0.0;
        int count = 0;

        for (int i = 0; i < data.size(); i++) {
            Bicycle bike = data.get(i);
            value += bike.getRotations_per_second();
            if(bike.getRotations_per_second() > 0) active++;
            count++;

            if (bike.getTimestamp().isAfter(last.plus(step, ChronoUnit.MILLIS))) {
                double average = value / count;

                ResultBike result = new ResultBike(bike.getChannel(), step, count, bike.getTimestamp(), average, active);
                this.result.add(result);

                last = bike.getTimestamp();
                count = 0;
                value = 0.0;
                active = 0.0;
            }
        }
    }

    public List<Bicycle> getData() {
        return data;
    }
}
