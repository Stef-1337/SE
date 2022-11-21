package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.HueColor;

import java.awt.*;
import java.util.List;

public class ColorCommand extends AbstractCommand {

    private String color;
    private HueColor hueColor;

    public ColorCommand(AbstractLampController controller, String name, HueColor hueColor) {
        super(controller, name);
        this.hueColor = hueColor;
    }

    @Override
    public void execute(AbstractLampController controller) {
        controller.colorChanged(String.valueOf(hueColor));
    }

    @Override
    public List<String> getConfig() {
        return List.of("Empty");
    }
}
