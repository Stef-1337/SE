package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.HueColor;

import java.util.ArrayList;
import java.util.List;

public class FlashCommand extends AbstractThreadCommand {
    Boolean to;
    Float brightness;
    String color;
    HueColor hueColor;
    public FlashCommand(AbstractLampController controller, String name, Boolean to, Float brightness, HueColor hueColor) {
        super(controller, name);
        this.to = to;
        this.brightness = brightness;
        this.hueColor = hueColor;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Thread thread = new Thread(() -> {
            try {
                while (!getThread().isInterrupted()){
                    //new LampCommand(controller, "lamp", to, brightness, hueColor).execute(controller);
                    new StateCommand(controller,"state", true).execute(controller);
                    System.out.println("an");
                    Thread.sleep(200);
                    new StateCommand(controller, "state", false).execute(controller);
                    System.out.println("aus");
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
       runThread(thread);
    }

    @Override
    public List<String> getConfig() {
        return List.of("Empty");
    }
}
