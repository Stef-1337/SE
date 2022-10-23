package de.ostfalia.s1.lamp;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lamp implements ILamp, Serializable {

    private String name = "Stehlampe rechts";
    private boolean toggleSwitch = false;
    private float intensity = 127;
    private float intensityPercentage = 50;
    private String hex = "9c9de0";
    private String colorList = "9c9de0";
    private Color color;
    private List<HueColor> hueColorList;

    public Lamp() {
        initColors();
    }

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

    @Override
    public Color getColor() throws IOException {
        return this.color;
    }

    public void setColor(String hex) throws IOException {
        setColor(Color.decode(hex));
    }

    @Override
    public void setColor(Color color) throws IOException {
        this.color = color;
    }

    @Override
    public float getIntensity() throws IOException {
        return this.intensity;
    }

    @Override
    public void setIntensity(float intensity) throws IOException {
        this.intensity = intensity;
        this.intensityPercentage = (float) (intensity / 2.5);
    }

    public float getIntensityPercentage() throws IOException {
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

    public List<HueColor> getHueColorList() {
        return hueColorList;
    }

    public void setHueColorList(List<HueColor> hueColorList) {
        this.hueColorList = hueColorList;
    }

    public void initColors() {
        List<HueColor> h = new ArrayList<>();
        String line = "";
        final String delimiter = ";";
        try {
            String filePath = "src/main/java/de/ostfalia/s1/lamp/Farbcodes.csv";
            FileReader fileReader = new FileReader(filePath);

            BufferedReader reader = new BufferedReader(fileReader);
            while ((line = reader.readLine()) != null)   //loops through every line until null found
            {
                String[] token = line.split(delimiter);
                h.add(new HueColor(token[0], token[1], Integer.valueOf(token[2]), Integer.valueOf(token[3]),
                        Integer.valueOf(token[4]), Float.valueOf(token[5]), Float.valueOf(token[6]), token[7]));// separate every token by comma
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setHueColorList(h);
    }
}
