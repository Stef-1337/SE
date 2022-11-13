package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
    Bicycle selectedOne;

    @Getter
    @Setter
    Bicycle selectedTwo;

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
    long factor = 1;

    @Getter
    @Setter
    TimeUnit timeUnit = TimeUnit.HOURS;

    @Getter
    @Setter
    String type = "DEFAULT";

    @Getter
    @Setter
    Kennzahl keyFigure = Kennzahl.ROTATIONS;

    @Getter
    @Setter
    List<Kennzahl> keyFigures = Arrays.stream(Kennzahl.values()).toList();

    @Getter
    @Setter
    String info;

    @Getter
    @Setter
    Boolean smoothed;

    @Getter
    @Setter
    private List<Date> timeRange;

    @Getter
    @Setter
    private String time;

    @Getter
    @Setter
    private double total;

    @Getter
    @Setter
    private double average;
    @Getter
    @Setter
    private double total2;

    @Getter
    @Setter
    private double average2;

    @Getter
    @Setter
    private Boolean isAverage = true;

    @Getter
    @Setter
    private Boolean isAverage2 = true;

    @PostConstruct
    public void init() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        time = sdfDate.format(now);

//        Time z = new Time (6, 0, 0);
//
//        long l = now.getTime() - z.getTime();
//
//        String s = sdfDate.format(l);
//        DateFormat
//        timeRange.add(now);
//        timeRange.add(s.);
    }

    public void resetStep() {
        step = -1;
        System.out.println("Step reset ???");
    }


    public Bicycle getStatus() {
        return bs.getLast(channel);
    }

    public List<Bicycle> getAllBicycleByBicycleChannel(int channelBicycle) {
        return bs.getByChannel(channelBicycle);
    }

    public List<Bicycle> getBicycle() {
        return bs.getAll();
    }

    public List<Bicycle> getBicycles() {
        return bs.getAll();
    }

    public Bicycle getLast(int channelBicycle) {
        return bs.getLast(channelBicycle);
    }

    public int test() {
        return selectedOne != null ? selectedOne.getChannel() : null;
    }

    public String pleaseSelect() {
        return "keine Auswahl getroffen";
    }


    public String getNum( double d1, double d2, boolean b) {
        String pattern = "#.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
            if (b) {
                return decimalFormat.format(d1) + " " + keyFigure.getUnitA();
            } else {
                return decimalFormat.format(d2) + " " + keyFigure.getUnitB();
            }
    }

}
