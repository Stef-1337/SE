package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;

public class BicycleOverview {
    @Inject
    BicycleService bs;

    public void loadInfo(){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Das Laden dieser Seite dauert länger wegen der Datenbankverbindung über VPN."));
    }

    public List<Bicycle> getBicycle(){
        return bs.getAll();
    }
}
