package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class BicycleDetailView implements Serializable {

    @Inject
    BicycleService bs;

    @Getter
    @Setter
    Bicycle selectedOne;

    @Getter
    @Setter
    Bicycle selectedTwo;

    @Getter
    @Setter
    Integer channel;

    @Getter
    @Setter
    Integer channel2;

    @Getter
    @Setter
    long step = 3600000;

    @Getter
    @Setter
    long factor = 1;

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

    @PostConstruct
    public void init() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        time = sdfDate.format(now);
    }



    public Bicycle getStatus(){
        return bs.getLast(channel);
    }

//    public Bicycle getStatus(){
//        return bs.getLast(channel);
//    }

    public List<Bicycle> getAllBicycleByBicycleChannel(int channelBicycle) {
        return bs.getByChannel(channelBicycle);
    }

    public List<Bicycle> getBicycle() {
        return bs.getAll();
    }

    public List<Bicycle> getBicycles(){
        return bs.getAll();
    }

    public Bicycle getLast(int channelBicycle) {
        return bs.getLast(channelBicycle);
    }

    public int test(){
        return selectedOne != null ? selectedOne.getChannel() : null;
    }

}
