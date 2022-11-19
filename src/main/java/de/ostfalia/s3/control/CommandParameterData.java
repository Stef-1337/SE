package de.ostfalia.s3.control;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

@Getter
@Setter
public class CommandParameterData {

    private String name = "";
    private boolean on = false;
    private double intensity = -1;
    private double intensityStep;
    private List<Color> colors;

}
