package de.ostfalia.s3.entity;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class Datacollector implements Serializable {

        @Inject
        BicycleData db;

        public String getCounter(){
            return Integer.toString(db.getCounter());
        }

}