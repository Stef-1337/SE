package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.Colors;
import de.ostfalia.s1.lamp.HueColor;

import java.awt.*;
import java.util.Random;

public class PartyCommand extends AbstractCommand{
    Boolean to;
    Float brightness;
    String color;
    HueColor hueColor;

    public PartyCommand(AbstractLampController controller, String name,Boolean to, Float brightness, HueColor hueColor) {
        super(controller, name);
        this.to = to;
        this.brightness = brightness;
        this.hueColor = hueColor;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Float brightnessChange = brightness;
        new LampCommand(controller, "lamp", to, brightness, hueColor).execute(controller);
        int change = 0;
        int count = 0;
        try {
            while (true){
                while (brightnessChange > 0 && change == 0) {
                    new BrightnessCommand(controller, "brightness", brightnessChange -= 5).execute(controller);
                    Thread.sleep(100);
                    System.out.println(brightnessChange);
                    System.out.println(controller.getAdapter().getLampe());
                    if (brightnessChange == brightness -15 || brightnessChange <= 6){
                        System.out.println("change 1");
                        change = 1;
                    }
                }
                if (change == 1){
                    brightnessChange = brightness / 3;
                    new StateCommand(controller, "state", false).execute(controller);
                    System.out.println(controller.getAdapter().getLampe());
                    Thread.sleep(100);
                    new ColorCommand(controller, "color", hueColor).execute(controller);   //color muss hier zu einer anderen geändert werden ToDo
                    System.out.println(controller.getAdapter().getLampe());
                    new BrightnessCommand(controller, "bribhtness", brightnessChange).execute(controller);
                    System.out.println(controller.getAdapter().getLampe());
                    change = 2;
                    System.out.println("change 2");
                    System.out.println(controller.getAdapter().getLampe());
                }
                while (brightnessChange < 100 && change == 2){
                    new BrightnessCommand(controller, "brightness", brightnessChange += 10).execute(controller);
                    System.out.println(brightnessChange);
                    System.out.println(controller.getAdapter().getLampe());
                    if (brightnessChange >= 89){
                        change = 3;
                        System.out.println("change 3");
                    }
                }
                while (change == 3){
                    Random random = new Random();
                    int r = random.nextInt(255);
                    int g = random.nextInt(255);
                    int b = random.nextInt(255);
                    HueColor randomcolor = new HueColor("" ,"" ,r , b, g, 0F, 0F, Colors.WHITE);
                    count++;
                    new ColorCommand(controller, "color", randomcolor).execute(controller);       //Absprechen, ob Color als String oder als Color ToDo
                    System.out.println(randomcolor);
                    System.out.println(controller.getAdapter().getLampe());
                    Thread.sleep(100);
                    if (count == 7){
                        change = 0;
                        count = 0;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
