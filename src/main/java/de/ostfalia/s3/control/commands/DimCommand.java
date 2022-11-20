package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

public class DimCommand extends AbstractCommand {
    Boolean to;
    Float brightness;
    String color;

    public DimCommand(AbstractLampController controller, String name, Boolean to, Float brightness, String color) {
        super(controller, name);
        this.to = to;
        this.brightness = brightness;
        this.color = color;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Float brightnessChange = brightness;
        Boolean change = true;
        new LampCommand(controller, "lamp", to, brightness, color);
        try {
            while (true) {
                while (brightnessChange > 0 && change == true) {
                    new BrightnessCommand(controller, "brightness", brightnessChange -= 5);
                    Thread.sleep(100);
                    if (brightnessChange <= 6) {
                        change = false;
                    }
                }
                while (brightnessChange < 100 && change == false) {
                    new BrightnessCommand(controller, "brightness", brightnessChange += 5);
                    Thread.sleep(100);
                    if (brightnessChange >= 94) {
                        change = true;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

