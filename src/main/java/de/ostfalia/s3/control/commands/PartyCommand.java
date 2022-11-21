package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s1.lamp.HueColor;

import java.util.List;
import java.util.Random;

public class PartyCommand extends AbstractThreadCommand {
    Boolean to;
    Float brightness;
    String color;
    HueColor hueColor;

    public PartyCommand(AbstractLampController controller, String name, Boolean to, Float brightness, HueColor hueColor) {
        super(controller, name);
        this.to = to;
        this.brightness = brightness;
        this.hueColor = hueColor;
    }

    @Override
    public void execute(AbstractLampController controller) {


        Thread thread = new Thread(() -> {
            new LampCommand(controller, "lamp", to, brightness, hueColor).execute(controller);
            int change = 0;
            int count = 0;

            Boolean curTo = to;
            Float curBrightness = brightness;
            HueColor curHuecolor = hueColor;
            try {
                while (!getThread().isInterrupted()) {
                    while (curBrightness > 5 && change == 0) {
                        new BrightnessCommand(controller, "brightness", curBrightness -= 5).execute(controller);
                        Thread.sleep(1);
                        System.out.println(curBrightness);
                        System.out.println(controller.getAdapter().getLampe());
                        if (curBrightness == brightness - 15 || curBrightness <= 6) {
                            System.out.println("change 1");
                            Random random = new Random();
                            change = random.nextInt(3);
                            if (curBrightness <= 5){
                                change = 1;
                            }
                            System.out.println(change);
                        }
                    }
                    if (change == 1) {
                        curBrightness = brightness / 3;
                        new StateCommand(controller, "state", false).execute(controller);
                        System.out.println(controller.getAdapter().getLampe());
                        Thread.sleep(1);
                        //new StateCommand(controller, "state", true).execute(controller);
                        //new ColorCommand(controller, "color", hueColor).execute(controller);
                        System.out.println(controller.getAdapter().getLampe());
                        //new BrightnessCommand(controller, "brightness", curBrightness).execute(controller);
                        new LampCommand(controller, "lamp", true, curBrightness, hueColor).execute(controller);
                        System.out.println(controller.getAdapter().getLampe());
                        Random random = new Random();
                        change = random.nextInt(3);
                        System.out.println("change 2");
                        System.out.println(change);
                        System.out.println(controller.getAdapter().getLampe());
                    }
                    while (curBrightness < 90 && change == 2) {
                        new BrightnessCommand(controller, "brightness", curBrightness += 10).execute(controller);
                        System.out.println(curBrightness);
                        System.out.println(controller.getAdapter().getLampe());
                        if (curBrightness >= 89) {
                            Random random = new Random();
                            change = random.nextInt(3);
                            if (change == 2){
                                change = 3;
                            }
                            System.out.println("change 3");
                            System.out.println(change);
                        }
                    }
                    while (change == 3) {
                        Random random = new Random();
                        ColorSelector colorSelector = new ColorSelector();
                        int randomNum = random.nextInt(9);
                        HueColor randomcolor = colorSelector.getColors().get(randomNum);
                        count++;
                        new ColorCommand(controller, "color", randomcolor).execute(controller);       //Absprechen, ob Color als String oder als Color ToDo
                        System.out.println(randomcolor);
                        System.out.println(controller.getAdapter().getLampe());
                        Thread.sleep(100);
                        if (count == 7) {
                            change = random.nextInt(3);
                            System.out.println(change);
                            count = 0;
                        }
                    }
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
