package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class BicycleDetailView {
    @Inject
    BicycleService bs;

    @Getter
    @Setter
    int channel;

    public Bicycle getStatus(){
        return bs.getLast(channel);
    }

//    public Bicycle getStatus(){
//        return bs.getLast(channel);
//    }

    public List<Bicycle> getAllBicycleByBicycleChannel(int channelBicycle) {
        return bs.getByChannel(channelBicycle);
    }
}
