package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


@Named
@RequestScoped
public class BicycleOverview {
    @Inject
    BicycleService bs;
    private Bicycle b;

    public Bicycle getB() {
        return b;
    }

    public void setB(Bicycle b) {
        this.b = b;
    }

    public void loadInfo() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Das Laden dieser Seite dauert länger wegen der Datenbankverbindung über VPN."));
    }

    public List<Bicycle> getBicycle() {
        return bs.getAll();
    }

    public Bicycle getLast(int channelBicycle) {
        return bs.getLast(channelBicycle);
    }
}
