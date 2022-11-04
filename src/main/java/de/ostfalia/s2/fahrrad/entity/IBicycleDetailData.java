package de.ostfalia.s2.fahrrad.entity;

import lombok.Getter;

public interface IBicycleDetailData {

    // interval, value
    Pair<Long, Double> getData();

     @Getter
     class Pair<T, U> {
        public T t;
        public U u;

        public Pair(T t, U u){
            this.t = t;
            this.u = u;
        }
    }

}
