package de.ostfalia.s3.control;

import de.ostfalia.s1.lamp.HueColor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

@Getter
@Setter
public class CommandParameterData {

    private String name = "";
    private boolean on = false;
    private int intensity;
    private int intensityStep;
    private int time;
    private List<HueColor> colors;

}
