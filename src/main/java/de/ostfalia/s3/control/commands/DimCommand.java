package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.HueColor;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DimCommand extends AbstractThreadCommand {
    Boolean to;
    Float brightness;
    String color;
    HueColor hueColor;

    public DimCommand(AbstractLampController controller, String name, Boolean to, Float brightness, HueColor hueColor) {
        super(controller, name);
        this.to = to;
        this.brightness = brightness;
        this.hueColor = hueColor;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Thread thread = new Thread(() -> {
            Float brightnessChange = brightness;
            Boolean change = true;
            new LampCommand(controller, "lamp", to, brightness, hueColor);
            try {
                while (!getThread().isInterrupted()) {
                    while (brightnessChange > 0 && change == true) {
                        new BrightnessCommand(controller, "brightness", brightnessChange -= 5).execute(controller);
                        Thread.sleep(100);
                        System.out.println(1);
                        System.out.println("1      " + brightnessChange);
                        System.out.println(controller.getAdapter().getLampe());
                        if (brightnessChange <= 6) {
                            change = false;
                        }
                    }
                    while (brightnessChange < 100 && change == false) {
                        new BrightnessCommand(controller, "brightness", brightnessChange += 5).execute(controller);
                        Thread.sleep(100);
                        System.out.println(2);
                        System.out.println("2      " + brightnessChange);
                        System.out.println(controller.getAdapter().getLampe());
                        if (brightnessChange >= 94) {
                            change = true;
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("abbgebrochen");;
            }
        });
        runThread(thread);
    }

    @Override
    public List<String> getConfig() {
        return List.of("Empty");
    }
}

