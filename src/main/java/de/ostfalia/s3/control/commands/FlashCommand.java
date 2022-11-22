package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.HueColor;

import java.util.ArrayList;
import java.util.List;

public class FlashCommand extends AbstractThreadCommand {
    private Boolean to;
    private Float brightness;
    private HueColor hueColor;
    private int time;

    public FlashCommand(AbstractLampController controller, String name, Boolean to, Float brightness, HueColor hueColor, int time) {
        super(controller, name);
        this.to = to;
        this.brightness = brightness;
        this.hueColor = hueColor;
        this.time = time;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Thread thread = new Thread(() -> {
            try {
                while (!getThread().isInterrupted()) {
                    new StateCommand(controller, "state", true).execute(controller);
                    Thread.sleep(time);
                    new StateCommand(controller, "state", false).execute(controller);
                    Thread.sleep(time);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        runThread(thread);
    }

    @Override
    public List<String> getConfig() {
        return List.of("Flash: " + time);
    }
}
