package de.ostfalia.s2.fahrrad.data;

import de.ostfalia.s2.fahrrad.data.template.DataOperation;
import de.ostfalia.s2.fahrrad.entity.Bicycle;

import java.util.List;

public class DataOperationOhneGlattung extends DataOperation {
    @Override
    public List<Bicycle> smooth(List<Bicycle> data) {
        return data;
    }

}
