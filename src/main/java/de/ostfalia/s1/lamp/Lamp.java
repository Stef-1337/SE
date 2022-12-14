package de.ostfalia.s1.lamp;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;


@Named
@SessionScoped
public class Lamp implements ILamp, Serializable {

    private String name;
    private boolean state;
    private float intensity;
    private float intensityPercentage;
    private Color color;
    private String colorName;
    private String hex;
    private int rgbR;
    private int rgbG;
    private int rgbB;
    private float x;
    private float y;
    private Colors cat;

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public int getRgbR() {
        return rgbR;
    }

    public void setRgbR(int rgbR) {
        this.rgbR = rgbR;
    }

    public int getRgbG() {
        return rgbG;
    }

    public void setRgbG(int rgbG) {
        this.rgbG = rgbG;
    }

    public int getRgbB() {
        return rgbB;
    }

    public void setRgbB(int rgbB) {
        this.rgbB = rgbB;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Colors getCat() {
        return cat;
    }

    public void setCat(Colors cat) {
        this.cat = cat;
    }

    public boolean isState() {
        return state;
    }

    @Override
    public void switchOn() {
        state = true;
    }

    @Override
    public void switchOn(float intensity) {
        state = true;
        this.setIntensity(intensity);
    }

    @Override
    public void switchOn(Color color) {
        this.setColor(color);
    }

    @Override
    public void switchOff() {
        state = false;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(String color) {
        String[] strings = color.split(" ");
        System.out.println(strings[0]);
        setColorName(strings[0]);
        setHex(strings[1]);
        setRgbR(Integer.parseInt(strings[2]));
        setRgbG(Integer.parseInt(strings[3]));
        setRgbB(Integer.parseInt(strings[4]));
        setX(Float.parseFloat(strings[5]));
        setY(Float.parseFloat(strings[6]));
        setCat(Colors.valueOf(strings[7]));
        setColor(Color.getColor(strings[0]));
    }

    @Override
    public float getIntensity() {
        return this.intensity;
    }

    @Override
    public void setIntensity(float intensity) {
        this.intensity = intensity;
        this.intensityPercentage = (float) (intensity / 2.5);
    }

    public float getIntensityPercentage() {
        return this.intensityPercentage;
    }

    public void setIntensityPercentage(float intensityPercentage) {
        this.intensityPercentage = intensityPercentage;
    }

    @Override
    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Lamp{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", intensity=" + intensity +
                ", intensityPercentage=" + intensityPercentage +
                ", color=" + color +
                ", colorName='" + colorName + '\'' +
                ", hex='" + hex + '\'' +
                ", rgbR=" + rgbR +
                ", rgbG=" + rgbG +
                ", rgbB=" + rgbB +
                ", x=" + x +
                ", y=" + y +
                ", cat=" + cat +
                '}';
    }

}
