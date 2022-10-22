package de.ostfalia.s1.lamp;

import java.util.List;

public class RequestObject {
    public boolean on ;
    public int bri;
    public double [] xy;
    public String name;


    public RequestObject(boolean on, float bri, List<Double> list, String name) {
        this.on = on;
        this.bri = (int) bri;
        double[] array = {list.get(0), list.get(1)};
        this.xy = array;
        this.name = name;
    }
}
