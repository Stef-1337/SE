package de.ostfalia.s2.fahrrad.data;

import de.ostfalia.s2.fahrrad.data.template.DataOperation;
import de.ostfalia.s2.fahrrad.entity.Bicycle;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class DataOperationMitGlattung extends DataOperation {

    @Override
    public List<Bicycle> smooth(List<Bicycle> data) {
        List<Bicycle> bicycles = new ArrayList<>();
        for (int i = 0; i < data.size() - 1; i++) {
            final int AVG = 3;

            Bicycle origin = data.get(i), bike = new Bicycle();

            bike.setChannel(origin.getChannel());
            bike.setTimestamp(origin.getTimestamp());
            bike.setRotations_per_second(bike.getRotations_per_second());

            double rotations = bike.getRotations_per_second();

            double total = 0;
            int count = 0;
            for (int offset = -AVG; offset < AVG; offset++) {
                int newI = i + offset;
                if (newI > 0 && newI < data.size()) {
                    total += data.get(newI).getRotations_per_second();
                    count++;
                }
            }

            double avg = total / count;

            if (rotations > avg * 2 || rotations < avg * 0.5)
                bike.setRotations_per_second((int) Math.round(avg));

            bicycles.add(bike);
        }
        return bicycles;
    }
}
