package de.ostfalia.s3.control;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s1.lamp.HueColor;
import lombok.Getter;
import lombok.Setter;

import javax.faces.event.ValueChangeEvent;
import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CommandParameterData {

    private AbstractLampController controller;

    private ColorSelector colorSelector;

    private String name = "";
    private boolean on = false;
    private int intensity;
    private int intensityStep;
    private int time;
    private String colorName;
    private HueColor color;
    private List<HueColor> colorList;
    private Integer channel1;
    private Integer channel2;
    private HueColor color2;

    public CommandParameterData (AbstractLampController controller, ColorSelector selector){
        this.controller = controller;

        this.colorSelector = selector;
        colorList = new ArrayList<>();
        colorName = "";
    }

//    public List<HueColor> getColors(){
//        List<HueColor> colors = new ArrayList<>();
//        colorList.forEach(color -> colors.add(colorSelector.getColor(color)));
//
//        return colors;
//    }

//    public HueColor getColor(){
//        return colorSelector.getColor(colorName);
//    }





}
