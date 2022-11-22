package de.ostfalia.s3.control;

import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s1.lamp.HueColor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommandParameterData {

    private String name = "";
    private boolean on = false;
    private int intensity;
    private int intensityStep;
    private int time;
    private HueColor color = new ColorService().getColors().get(0);
    private List<HueColor> colors;

    public CommandParameterData (){
        colors = new ArrayList<>();
        colors.add(new ColorSelector().getColors().get(0));
    }

}
