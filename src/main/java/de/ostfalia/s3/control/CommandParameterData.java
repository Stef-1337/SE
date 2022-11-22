package de.ostfalia.s3.control;

import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s1.lamp.HueColor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommandParameterData {

    private ColorSelector colorSelector;

    private String name = "";
    private boolean on = false;
    private int intensity;
    private int intensityStep;
    private int time;
    private String colorName;
    private List<String> colorList;

    public CommandParameterData (ColorSelector selector){
        this.colorSelector = selector;
        colorList = new ArrayList<>();
        colorName = "";
    }

    public List<HueColor> getColors(){
        List<HueColor> colorList = new ArrayList<>();

        this.colorList.forEach(color -> colorList.add(colorSelector.getColor(color)));

        return this.colorList.stream().map(name -> colorSelector.getColor(name)).toList();
    }

    public HueColor getColor(){
        return colorSelector.getColor(colorName);
    }





}
