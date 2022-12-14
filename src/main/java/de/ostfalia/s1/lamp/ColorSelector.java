package de.ostfalia.s1.lamp;

import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ColorSelector {

    @Getter
    private List<HueColor> colorList;

    @Getter
    private HashMap<String, HueColor> colors;

    public ColorSelector() {
        colorList = List.of(new HueColor("white", "#FFFFFF", 255, 255, 255, (float) 0.3227, (float) 0.3287, Colors.WHITE),
                new HueColor("pink", "#FFC0CB", 255, 192, 203, (float) 0.3947, (float) 0.3114, Colors.PINK),
                new HueColor("purple", "#800080", 128, 0, 128, (float) 0.3826, (float) 0.1597, Colors.PURPLE),
                new HueColor("blue", "#0000FF", 0, 0, 255, (float) 0.167, (float) 0.04, Colors.BLUE),
                new HueColor("cyan", "#00FFFF", 0, 255, 255, (float) 0.2857, (float) 0.2744, Colors.CYAN),
                new HueColor("green", "#008000", 0, 128, 0, (float) 0.4091, (float) 0.518, Colors.GREEN),
                new HueColor("yellow", "#FFFF00", 255, 255, 0, (float) 0.4325, (float) 0.5007, Colors.YELLOW),
                new HueColor("orange", "#FFA500", 255, 165, 0, (float) 0.5567, (float) 0.4091, Colors.ORANGE),
                new HueColor("red", "	#FF0000", 255, 0, 0, (float) 0.675, (float) 0.322, Colors.RED));

        colors = new HashMap<>();
        colorList.forEach(color -> colors.put(color.getName(), color));
    }

    public HueColor getColor(String color) {
        return colors.get(color);
    }

}
