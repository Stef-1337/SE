package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.HueColor;

import java.awt.*;
import java.util.List;

public class LampCommand extends AbstractCommand {
    Boolean to;
    Float brightness;
    String color;
    HueColor hueColor;

    public LampCommand(AbstractLampController controller, String name, Boolean to, Float brightness, HueColor hueColor) {
        super(controller, name);
        this.to = to;
        this.brightness = brightness;
        this.hueColor = hueColor;
    }

    @Override
    public void execute(AbstractLampController controller) {
        new StateCommand(controller, "state", to).execute(controller);
        new BrightnessCommand(controller, "brightness", brightness).execute(controller);
        new ColorCommand(controller, "color", hueColor).execute(controller);

    }

    @Override
    public java.util.List<String> getConfig() {
        return List.of("State: " + to + " Brightness: " + brightness + " HueColor: " + hueColor);
    }
}
