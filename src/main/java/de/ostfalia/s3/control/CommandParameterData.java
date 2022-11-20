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
    private int intensity;
    private int intensityStep;
    private List<String> colors;

}
