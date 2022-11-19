package de.ostfalia.s3.control;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

@Getter
@Setter
public class CommandParameterData {

    private String name;
    private boolean on;
    private double intensity;
    private double intensityStep;
    private List<Color> colors;

}
