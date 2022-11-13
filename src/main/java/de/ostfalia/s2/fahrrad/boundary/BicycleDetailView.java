package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.kennzahl.Kennzahl;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Named("bicycleDetailView")
@SessionScoped
public class BicycleDetailView implements Serializable {

    @Inject
    @Getter
    @Setter
    BicycleService bs;

    @Getter
    @Setter
    Integer channel = 7;

    @Getter
    @Setter
    Integer channel2;

    @Getter
    @Setter
    long step = -1;//3600000;

    @Getter
    @Setter
    String s = "1";

    @Getter
    @Setter
    TimeUnit timeUnit = TimeUnit.HOURS;

    @Getter
    @Setter
    Kennzahl keyFigure = Kennzahl.ROTATIONS;

    @Getter
    @Setter
    List<Kennzahl> keyFigures = Arrays.stream(Kennzahl.values()).toList();

    @Getter
    @Setter
    Boolean smoothed;

    @Getter
    @Setter
    private List<Date> timeRange;

    @Getter
    @Setter
    private double total, average, total2, average2;

    @Getter
    @Setter
    private Boolean isAverage = true, isAverage2 = true;

    public void resetStep() {
        step = -1;
    }

    public List<Bicycle> getBicycles() {
        return bs.getAll();
    }

    public String getNum(double d1, double d2, boolean b) {
        String pattern = "#.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        if (b) {
            return decimalFormat.format(d1) + " " + keyFigure.getUnitA();
        } else {
            return decimalFormat.format(d2) + " " + keyFigure.getUnitB();
        }
    }

    public void stepChanged(ValueChangeEvent e) {
        s = e.getNewValue().toString();
        step = Long.parseLong(e.getNewValue().toString());
    }

}
