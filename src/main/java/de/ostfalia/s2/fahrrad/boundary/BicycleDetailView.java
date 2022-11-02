package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class BicycleDetailView {
    @Inject
    BicycleService bs;

    @Getter
    @Setter
    int channel;

    public Bicycle getBicycle(){
        return bs.getLast(channel);
    }
}
