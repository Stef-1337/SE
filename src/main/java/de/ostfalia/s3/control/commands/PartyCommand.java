package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s1.lamp.HueColor;

import java.util.List;
import java.util.Random;

public class PartyCommand extends AbstractThreadCommand {
    private List<HueColor> colors;
    private int time;
    private int amount;
    private Float brightness;

    public PartyCommand(AbstractLampController controller, String name, List<HueColor> colors, int time, int amount) {
        super(controller, name);
        this.colors = colors;
        this.time = time;
        this.amount = amount;
        this.brightness = controller.getAdapter().getIntensity();
    }

    @Override
    public void execute(AbstractLampController controller) {


        Thread thread = new Thread(() -> {
            Float curBrightness = controller.getAdapter().getIntensity();
            int size = colors.size();
            Random random = new Random();
            int randomNum = random.nextInt(size);
            new LampCommand(controller, "lamp", true,curBrightness, colors.get(randomNum)).execute(controller);
            int change = 0;
            int count = 0;


            try {
                while (!getThread().isInterrupted()) {
                    while (curBrightness > 5 && change == 0) {
                        new BrightnessCommand(controller, "brightness", curBrightness -= 5).execute(controller);
                        Thread.sleep(1);
                        if (curBrightness == brightness - 15 || curBrightness <= 6) {
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
                        randomNum = random.nextInt(size);
                        new LampCommand(controller, "lamp", true, curBrightness, colors.get(randomNum)).execute(controller);
                        change = random.nextInt(3);
                    }
                    while (curBrightness < 90 && change == 2) {
                        new BrightnessCommand(controller, "brightness", curBrightness += 10).execute(controller);
                        if (curBrightness >= 89) {
                            change = random.nextInt(3);
                            if (change == 2) {
                                change = 3;
                            }
                        }
                    }
                    while (change == 3) {
                        randomNum = random.nextInt(size);
                        HueColor randomcolor = colors.get(randomNum);
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
