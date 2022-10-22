package de.ostfalia.s1.lamp.boundary;

import org.primefaces.component.colorpicker.ColorPicker;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@ManagedBean(name = "lampControllerView")
@Named
@ViewScoped
public class LampControllerView implements Serializable {

    private String name = "lampe1";
    private boolean toggleSwitch;
    private int brightness = 50;
    private String color = "9c9de0";
    private String colorList = "9c9de0";



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

    public void colorChanged(ValueChangeEvent e) {
//        ColorPicker picker = (ColorPicker) e.getComponent();
//        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Color changed: " + picker.getValue(), null);
//        FacesContext.getCurrentInstance().addMessage(null, msg);
        color = e.getNewValue().toString();
        colorList = getRGBtoXY(Color.decode(color)).toString();
//        colorList = Color.decode(color).toString();
    }

    public static List<Double> getRGBtoXY(Color c) {
        // For the hue bulb the corners of the triangle are:
        // -Red: 0.675, 0.322
        // -Green: 0.4091, 0.518
        // -Blue: 0.167, 0.04
        double[] normalizedToOne = new double[3];
        float cred, cgreen, cblue;
        cred = c.getRed();
        cgreen = c.getGreen();
        cblue = c.getBlue();
        normalizedToOne[0] = (cred / 255);
        normalizedToOne[1] = (cgreen / 255);
        normalizedToOne[2] = (cblue / 255);
        float red, green, blue;

        // Make red more vivid
        if (normalizedToOne[0] > 0.04045) {
            red = (float) Math.pow(
                    (normalizedToOne[0] + 0.055) / (1.0 + 0.055), 2.4);
        } else {
            red = (float) (normalizedToOne[0] / 12.92);
        }

        // Make green more vivid
        if (normalizedToOne[1] > 0.04045) {
            green = (float) Math.pow((normalizedToOne[1] + 0.055)
                    / (1.0 + 0.055), 2.4);
        } else {
            green = (float) (normalizedToOne[1] / 12.92);
        }

        // Make blue more vivid
        if (normalizedToOne[2] > 0.04045) {
            blue = (float) Math.pow((normalizedToOne[2] + 0.055)
                    / (1.0 + 0.055), 2.4);
        } else {
            blue = (float) (normalizedToOne[2] / 12.92);
        }

        float X = (float) (red * 0.649926 + green * 0.103455 + blue * 0.197109);
        float Y = (float) (red * 0.234327 + green * 0.743075 + blue * 0.022598);
        float Z = (float) (red * 0.0000000 + green * 0.053077 + blue * 1.035763);

        float x = X / (X + Y + Z);
        float y = Y / (X + Y + Z);

        double[] xy = new double[2];
        xy[0] = x;
        xy[1] = y;
        List<Double> xyAsList = new ArrayList<>(2);
        for (Double d:xy) {
            xyAsList.add(d);
        }
        return xyAsList;
    }
}
