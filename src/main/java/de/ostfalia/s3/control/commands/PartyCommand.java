package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.HueColor;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.commands.command.AbstractThreadCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PartyCommand extends AbstractThreadCommand {
    private List<HueColor> colors;
    private int time;
    private Float brightness;

    public PartyCommand(CommandParameterData data){
        this(data.getController(), data.getName(), data.getColorList(), data.getTime());
    }

    public PartyCommand(AbstractLampController controller, String name, List<HueColor> colors, int time) {
        super(controller, name);
        this.colors = colors;
        this.time = time;
        this.brightness = controller.getAdapter().getIntensity();
    }

    @Override
    public void execute(AbstractLampController controller) {


        Thread thread = new Thread(() -> {
            Float curBrightness = controller.getAdapter().getIntensity();
            int size = colors.size();
            Random random = new Random();
            int randomNum = random.nextInt(size);
            System.out.println(size);
            new LampCommand(controller, "lamp", true,curBrightness, colors.get(randomNum)).execute(controller);
            int change = 0;
            int count = 0;
            int amount = 0;

            try {
                while (!getThread().isInterrupted()) {
                    if (curBrightness <= 5){
                        change = 2;
                    }
                    while (curBrightness > 5 && change == 0) {
                        System.out.println(000000000000000);
                        new BrightnessCommand(controller, "brightness", curBrightness -= 5).execute(controller);
                        Thread.sleep(1);
                        if (curBrightness == brightness - 15 || curBrightness <= 6) {
                            change = random.nextInt(3);
                            amount =  random.nextInt((40 - 10) + 1) + 10;
                            if (curBrightness <= 5) {
                                change = 1;
                            }
                        }
                    }
                    if (change == 1) {
                        System.out.println(1111111111);
                        curBrightness = brightness / 3;
                        new StateCommand(controller, "state", false).execute(controller);
                        Thread.sleep(100);
                        randomNum = random.nextInt(size);
                        new LampCommand(controller, "lamp", true, curBrightness, colors.get(randomNum)).execute(controller);
                        change = random.nextInt(3);
                    }
                    while (curBrightness < 90 && change == 2) {
                        System.out.println(222222222);
                        new BrightnessCommand(controller, "brightness", curBrightness += 10).execute(controller);
                        if (curBrightness >= 89) {
                            change = random.nextInt(3);
                            amount =  random.nextInt((40 - 10) + 1) + 10;
                            if (change == 2) {
                                change = 3;
                            }
                        }
                    }
                    while (change == 3) {
                        System.out.println(3333333);
                        randomNum = random.nextInt(size);
                        HueColor randomcolor = colors.get(randomNum);
                        count++;
                        new ColorCommand(controller, "color", randomcolor).execute(controller);
                        Thread.sleep(100);
                        if (count == amount) {
                            System.out.println("fehler 1");
                            change = random.nextInt(3);
                            System.out.println("fehler 2");
                            count = 0;
                            amount =  random.nextInt((40 - 10) + 1) + 10;
                            System.out.println("fehler 3" + change);
                            if (change == 2 && controller.getAdapter().getIntensity() >= 89){
                                System.out.println("fehler 4");
                                change = 0;
                            }
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
        List <String> colorNames = new ArrayList<>(colors.size());
        colors.forEach(c -> colorNames.add(c.getName()));
        return List.of("Party: " + time + "ms" + "\n Farben: " + colorNames.toString());
    }

}
