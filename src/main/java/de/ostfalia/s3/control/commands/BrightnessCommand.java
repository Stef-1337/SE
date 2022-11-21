package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

import java.util.Arrays;
import java.util.List;

public class BrightnessCommand extends AbstractCommand {

    private float brightness;

    public BrightnessCommand(AbstractLampController controller, String name, float brightness) {
        super(controller, name);
        this.brightness = brightness;
    }

    @Override
    public void execute(AbstractLampController controller) {
        controller.brightnessChanged(brightness);
    }

    @Override
    public List<String> getConfig() {
        return List.of("Brightness: " + brightness);
    }
}
