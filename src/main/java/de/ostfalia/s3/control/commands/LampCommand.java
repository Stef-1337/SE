package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

import java.awt.*;

public class LampCommand extends AbstractCommand{
    Boolean to;
    Float brigthness;
    String color;

    public LampCommand(AbstractLampController controller, String name, Boolean to, Float brightness, String color) {
        super(controller, name);
        this.to = to;
        this.brigthness = brightness;
        this.color = color;
    }

    @Override
    public void execute(AbstractLampController controller) {
        controller.switchChanged(to);
        controller.brightnessChanged(brigthness);
        controller.colorChanged(color);
    }
}
