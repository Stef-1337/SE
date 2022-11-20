package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

import java.util.ArrayList;
import java.util.List;

public class FlashCommand extends AbstractCommand {
    Boolean to;
    Float brightness;
    String color;
    public FlashCommand(AbstractLampController controller, String name, Boolean to, Float brightness, String color) {
        super(controller, name);
        this.to = to;
        this.brightness = brightness;
        this.color = color;
    }

    @Override
    public void execute(AbstractLampController controller) {
        try {
            while (true){
                new StateCommand(controller,"state", true).execute(controller);
                System.out.println("an");
                Thread.sleep(4000);
                new StateCommand(controller, "state", false).execute(controller);
                System.out.println("aus");
                Thread.sleep(400);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
