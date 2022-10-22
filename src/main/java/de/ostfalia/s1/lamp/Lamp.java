package de.ostfalia.s1.lamp;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;

public class Lamp implements ILamp, Serializable {

    private String name = "lampe1";
    private boolean toggleSwitch;
    private float intensity = 50;
    private float intensityPercentage = 50;
    private String hex = "9c9de0";
    private String colorList = "9c9de0";
    private Color color;

    @Override
    public void switchOn() throws IOException {
        toggleSwitch = true;
    }

    @Override
    public void switchOn(float intensity) throws IOException {
        toggleSwitch = true;
        this.setIntensity(intensity);
    }

    @Override
    public void switchOn(Color color) throws IOException {
        this.setColor(color);
    }

    @Override
    public void switchOff() throws IOException {
        toggleSwitch = false;
    }

    public void setColor(String hex) throws IOException {
        setColor(Color.decode(hex));
    }

    @Override
    public void setColor(Color color) throws IOException {
        this.color = color;
    }

    @Override
    public void setIntensity(float intensity) throws IOException {
        this.intensity = intensity;
        this.intensityPercentage = (float) (intensity/2.5);
    }

    @Override
    public Color getColor() throws IOException {
        return this.color;
    }

    @Override
    public float getIntensity() throws IOException {
        return this.intensityPercentage;
    }

    @Override
    public boolean getState() throws IOException {
        return toggleSwitch;
    }

    public void setState(Boolean toggleSwitch) throws IOException {
        this.toggleSwitch = toggleSwitch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
