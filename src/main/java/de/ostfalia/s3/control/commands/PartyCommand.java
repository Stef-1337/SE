package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s1.lamp.HueColor;

import java.util.List;
import java.util.Random;

public class PartyCommand extends AbstractThreadCommand {
    private Boolean to;
    private Float brightness;
    private HueColor hueColor;

    private int time;

    private int amount;

    public PartyCommand(AbstractLampController controller, String name, Boolean to, Float brightness, HueColor hueColor, int time, int amount) {
        super(controller, name);
        this.to = to;
        this.brightness = brightness;
        this.hueColor = hueColor;
        this.time = time;
        this.amount = amount;
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
                        if (curBrightness == brightness - 15 || curBrightness <= 6) {
                            Random random = new Random();
                            change = random.nextInt(3);
                            if (curBrightness <= 5) {
                                change = 1;
                            }
                        }
                    }
                    if (change == 1) {
                        curBrightness = brightness / 3;
                        new StateCommand(controller, "state", false).execute(controller);
                        Thread.sleep(100);
                        new LampCommand(controller, "lamp", true, curBrightness, hueColor).execute(controller);
                        Random random = new Random();
                        change = random.nextInt(3);
                    }
                    while (curBrightness < 90 && change == 2) {
                        new BrightnessCommand(controller, "brightness", curBrightness += 10).execute(controller);
                        if (curBrightness >= 89) {
                            Random random = new Random();
                            change = random.nextInt(3);
                            if (change == 2) {
                                change = 3;
                            }
                        }
                    }
                    while (change == 3) {
                        Random random = new Random();
                        ColorSelector colorSelector = new ColorSelector();
                        int randomNum = random.nextInt(8);
                        HueColor randomcolor = colorSelector.getColors().get(randomNum);
                        count++;
                        new ColorCommand(controller, "color", randomcolor).execute(controller);
                        Thread.sleep(100);
                        if (count == amount) {
                            change = random.nextInt(3);
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
        return List.of("Party: " + time + "Amount of changes: " + amount);
    }
}
