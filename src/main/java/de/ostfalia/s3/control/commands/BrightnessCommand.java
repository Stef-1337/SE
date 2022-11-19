package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

public class BrightnessCommand extends AbstractCommand{

private int brightness;

    public BrightnessCommand(AbstractLampController controller, String name, int brightness) {
        super(controller, name);
        this.brightness = brightness;
    }

    @Override
    public void execute(AbstractLampController controller) {

    }
}
