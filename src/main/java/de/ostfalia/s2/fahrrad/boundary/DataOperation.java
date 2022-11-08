package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.entity.Bicycle;

import java.util.List;

public abstract class DataOperation {

    private List<Bicycle> data;

    public DataOperation(){

    }

    public void init(List<Bicycle> data){
        this.data = data;
    }

    public int total(){
        return data.stream().mapToInt(Bicycle::getRotations_per_second).sum();
    }

    public void smooth(){

    }

    public List<Bicycle> getData() {
        return data;
    }
}
