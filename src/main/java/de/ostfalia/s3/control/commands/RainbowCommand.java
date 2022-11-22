package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s1.lamp.HueColor;

import java.util.List;

public class RainbowCommand extends AbstractThreadCommand {

    Boolean to;
    Float brightness;

    HueColor hueColor;

    int time;


    public RainbowCommand(AbstractLampController controller, String name, Boolean to, Float brightness, HueColor hueColor, int time) {
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
                    ColorSelector colorSelector = new ColorSelector();
                    new LampCommand(controller, "rot", true, brightness, colorSelector.getColors().get(8)).execute(controller);
                    System.out.println(controller.getAdapter().getLampe());
                    Thread.sleep(time);
                    new LampCommand(controller, "rot", true, brightness, colorSelector.getColors().get(7)).execute(controller);
                    System.out.println(controller.getAdapter().getLampe());
                    Thread.sleep(time);
                    new LampCommand(controller, "rot", true, brightness, colorSelector.getColors().get(6)).execute(controller);
                    System.out.println(controller.getAdapter().getLampe());
                    Thread.sleep(time);
                    new LampCommand(controller, "rot", true, brightness, colorSelector.getColors().get(5)).execute(controller);
                    System.out.println(controller.getAdapter().getLampe());
                    Thread.sleep(time);
                    new LampCommand(controller, "rot", true, brightness, colorSelector.getColors().get(4)).execute(controller);
                    System.out.println(controller.getAdapter().getLampe());
                    Thread.sleep(time);
                    new LampCommand(controller, "rot", true, brightness, colorSelector.getColors().get(2)).execute(controller);
                    System.out.println(controller.getAdapter().getLampe());
                    Thread.sleep(time * 2);
                    new LampCommand(controller, "rot", true, brightness, colorSelector.getColors().get(4)).execute(controller);
                    System.out.println(controller.getAdapter().getLampe());
                    Thread.sleep(time);
                    new LampCommand(controller, "rot", true, brightness, colorSelector.getColors().get(5)).execute(controller);
                    System.out.println(controller.getAdapter().getLampe());
                    Thread.sleep(time);
                    new LampCommand(controller, "rot", true, brightness, colorSelector.getColors().get(6)).execute(controller);
                    System.out.println(controller.getAdapter().getLampe());
                    Thread.sleep(time);
                    new LampCommand(controller, "rot", true, brightness, colorSelector.getColors().get(7)).execute(controller);
                    System.out.println(controller.getAdapter().getLampe());
                    Thread.sleep(time);
                    new LampCommand(controller, "rot", true, brightness, colorSelector.getColors().get(8)).execute(controller);
                    System.out.println(controller.getAdapter().getLampe());
                    Thread.sleep(time * 2);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        runThread(thread);
    }

    @Override
    public List<String> getConfig() {
        return List.of("Rainbow: " + " Time between: " + time);
    }
}
