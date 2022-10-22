package de.ostfalia.s1.lamp.boundary;

import org.primefaces.component.colorpicker.ColorPicker;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
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
    private int brightness = 50;
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void nameChanged(ValueChangeEvent e) {
        name = e.getNewValue().toString();
    }

    public boolean isToggleSwitch() {
        return toggleSwitch;
    }

    public void setToggleSwitch(boolean toggleSwitch) {
        this.toggleSwitch = toggleSwitch;
    }

    public void switchChanged(ValueChangeEvent e) {
        toggleSwitch = Boolean.valueOf(e.getNewValue().toString());
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public void brightnessChanged(ValueChangeEvent e) {
        brightness = Integer.valueOf(e.getNewValue().toString());
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void colorChanged(AjaxBehaviorEvent e) {
        ColorPicker picker = (ColorPicker) e.getComponent();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Color changed: " + picker.getValue(), null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        color = e.getComponent().toString();
    }
}
