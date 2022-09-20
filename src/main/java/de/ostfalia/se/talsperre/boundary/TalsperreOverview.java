package de.ostfalia.se.talsperre.boundary;

import de.ostfalia.se.talsperre.control.TalsperreService;
import de.ostfalia.se.talsperre.control.TalsperrendatenService;
import de.ostfalia.se.talsperre.entity.Talsperre;
import de.ostfalia.se.talsperre.entity.Talsperrendaten;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


/**
 * Backend Bean für die Talsperren Übersicht.
 */
@Named
@RequestScoped
public class TalsperreOverview {

    @Inject
    TalsperreService tss;

    @Inject
    TalsperrendatenService tsds;


    public void loadInfo(){
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Das Laden dieser Seite dauert länger wegen der Datenbankverbindung über VPN."));

    }

    public List<Talsperre> getTalsperren(){
        return tss.getAll();
    }
    public Talsperrendaten getLast(int idTalsperre){
        return tsds.getLast(idTalsperre);
    }



}
