package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

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
}
