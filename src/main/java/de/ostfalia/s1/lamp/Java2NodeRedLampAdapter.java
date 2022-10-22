package de.ostfalia.s1.lamp;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;


@Named
@SessionScoped
public class Java2NodeRedLampAdapter implements ILamp, Serializable {

    Lamp lampe = new Lamp();

    @Override
    public void switchOn() throws IOException {
        lampe.switchOn();
    }

    @Override
    public void switchOn(float intensity) throws IOException {
        lampe.switchOn();
    }

    @Override
    public void switchOn(Color color) throws IOException {
        lampe.switchOn();
    }

    @Override
    public void switchOff() throws IOException {
        lampe.switchOn();
    }

    @Override
    public void setColor(Color color) throws IOException {
        lampe.setColor(color);
    }

    @Override
    public void setIntensity(float intensity) throws IOException {
        lampe.setIntensity(intensity);
    }

    @Override
    public Color getColor() throws IOException {
        return lampe.getColor();
    }

    @Override
    public float getIntensity() throws IOException {
        return lampe.getIntensity();
    }

    @Override
    public boolean getState() throws IOException {
        return lampe.getState();
    }
}
