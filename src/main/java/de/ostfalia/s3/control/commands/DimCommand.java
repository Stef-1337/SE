package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

public class DimCommand extends AbstractCommand {
    Boolean to;
    Float brigthness;
    String color;

    public DimCommand(AbstractLampController controller, String name, Boolean to, Float brightness, String color) {
        super(controller, name);
        this.to = to;
        this.brigthness = brightness;
        this.color = color;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Float brigthnessChange = brigthness;
        Boolean change = true;
        new LampCommand(controller, "lamp", to, brigthness, color);
        try {
            while (true) {
                while (brigthnessChange > 0 && change == true) {
                    new BrightnessCommand(controller, "brightness", brigthnessChange -= 5);
                    Thread.sleep(100);
                    if (brigthnessChange <= 5) {
                        change = false;
                    }
                }
                while (brigthnessChange < 100 && change == false) {
                    new BrightnessCommand(controller, "brightness", brigthnessChange += 5);
                    Thread.sleep(100);
                    if (brigthnessChange >= 95) {
                        change = true;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

