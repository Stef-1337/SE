package de.ostfalia.s1.lamp.boundary;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

//@ManagedBean(name = "lampControllerView")
@Named
@ViewScoped
public class LampControllerView implements Serializable {

    private String name = "lampe1";
    private boolean toggleSwitch;
    private int brightness;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void localeChanged(ValueChangeEvent e) {
        name = e.getNewValue().toString();
    }

    public boolean isToggleSwitch() {
        return toggleSwitch;
    }

    public void setToggleSwitch(boolean toggleSwitch) {
        this.toggleSwitch = toggleSwitch;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public void onChange() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You have selected: " + brightness, null));
    }
}
