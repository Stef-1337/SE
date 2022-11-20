package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

import java.awt.*;

public class LampCommand extends AbstractCommand{
    Boolean to;
    Float brightness;
    String color;

    public LampCommand(AbstractLampController controller, String name, Boolean to, Float brightness, String color) {
        super(controller, name);
        this.to = to;
        this.brightness = brightness;
        this.color = color;
    }

    @Override
    public void execute(AbstractLampController controller) {
         new StateCommand(controller, "state", to).execute(controller);
        new BrightnessCommand(controller, "brightness", brightness).execute(controller);
        new  ColorCommand(controller,"color", color).execute(controller);

    }
}
