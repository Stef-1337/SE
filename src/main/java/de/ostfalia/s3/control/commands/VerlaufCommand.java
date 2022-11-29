package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;


import java.util.List;

public class VerlaufCommand extends AbstractThreadCommand {

    private int time;

    public VerlaufCommand(AbstractLampController controller, String name, int time) {
        super(controller, name);
        this.time = time;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Thread thread = new Thread(() -> {
            Float brightnessChange = controller.getAdapter().getIntensity();
            Boolean change = true;
            try {
                while (!getThread().isInterrupted()) {
                    while (brightnessChange > 4 && change == true) {
                        new BrightnessCommand(controller, "brightness", brightnessChange -= 5).execute(controller);
                        Thread.sleep(time);
                        if (brightnessChange <= 6) {
                            change = false;
                        }
                    }
                    while (brightnessChange < 96 && change == false) {
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

