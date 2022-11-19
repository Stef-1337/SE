package de.ostfalia.s3.boundary;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.ToggleEvent;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import static javax.faces.context.FacesContext.*;

@Named
@RequestScoped
public class PanelView {

    public void onClose(CloseEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed", "Closed panel id:'" + event.getComponent().getId() + "'");
        getCurrentInstance().addMessage(null, message);
    }

    public void onToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
        getCurrentInstance().addMessage(null, message);
    }
}