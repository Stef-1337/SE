package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.HueColor;


import java.util.List;

public class DimCommand extends AbstractThreadCommand {
    private Boolean to;
    private Float brightness;
    private HueColor hueColor;

    private int time;

    public DimCommand(AbstractLampController controller, String name, Boolean to, Float brightness, HueColor hueColor, int time) {
        super(controller, name);
        this.to = to;
        this.brightness = brightness;
        this.hueColor = hueColor;
        this.time = time;
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
                        Thread.sleep(time);
                        if (brightnessChange <= 6) {
                            change = false;
                        }
                    }
                    while (brightnessChange < 100 && change == false) {
                        new BrightnessCommand(controller, "brightness", brightnessChange += 5).execute(controller);
                        Thread.sleep(time);
                        if (brightnessChange >= 94) {
                            change = true;
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("abbgebrochen");
                ;
            }
        });
        runThread(thread);
    }

    @Override
    public List<String> getConfig() {
        return List.of("Dim: " + time);
    }
}

