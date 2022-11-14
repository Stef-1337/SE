
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Named("bicycleDetailView")
@SessionScoped
@Getter
@Setter
public class BicycleDetailView implements Serializable {

    @Inject
    private BicycleService bs;

    private Integer channel = 7;

    private Integer channel2 = -1;

    private String channel2Label = "no selection";

    private long step = -1;//3600000;

    private String s = "automatisch festgelegt";

    private TimeUnit timeUnit = TimeUnit.HOURS;

    private Kennzahl keyFigure = Kennzahl.ROTATIONS;

    private List<Kennzahl> keyFigures = Arrays.stream(Kennzahl.values()).toList();

    private Boolean smoothed;

    private List<Date> timeRange;

    private double total, average, total2, average2;

    private Boolean isAverage = true, isAverage2 = true;

    public void resetStep() {
        step = -1;
        s = "automatisch festgelegt";
        timeUnit = TimeUnit.HOURS;
    }

    public List<Bicycle> getBicycles(Boolean b) {
        return bs.getAll(b);
    }

    public String getNum(int i, double d1, double d2, boolean b) {
        String pattern = "#.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        if (channel2 == -1 && i == 2) {
            return "0";
        }
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

    public void setNow (){
        timeRange = new ArrayList<>();
        Date d12 = java.sql.Timestamp.valueOf(LocalDateTime.now().minusHours(12));
        timeRange.add(d12);
        Date d = java.sql.Timestamp.valueOf(LocalDateTime.now());
        timeRange.add(d);
    }

}

