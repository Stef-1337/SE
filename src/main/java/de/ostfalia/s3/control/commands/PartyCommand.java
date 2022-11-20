package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

import java.awt.*;
import java.util.Random;

public class PartyCommand extends AbstractCommand{
    Boolean to;
    Float brightness;
    String color;

    public PartyCommand(AbstractLampController controller, String name,Boolean to, Float brightness, String color) {
        super(controller, name);
        this.to = to;
        this.brightness = brightness;
        this.color = color;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Float brightnessChange = brightness;
        new LampCommand(controller, "lamp", to, brightness, color);
        int change = 0;
        int count = 0;
        try {
            while (true){
                while (brightnessChange > 0 && change == 0) {
                    //new DimCommand(controller, "dim", to, brightness, color);
                    new BrightnessCommand(controller, "brightness", brightnessChange -= 5);
                    Thread.sleep(100);
                    System.out.println(brightnessChange);
                    if (brightnessChange == brightness -15 || brightnessChange <= 6){
                        System.out.println("change 1");
                        change = 1;
                    }
                }
                if (change == 1){
                    brightnessChange = brightness / 3;
                    new StateCommand(controller, "state", false);
                    Thread.sleep(100);
                    new ColorCommand(controller, "color", color);       //color muss hier zu einer anderen geändert werden ToDo
                    new BrightnessCommand(controller, "bribhtness", brightnessChange);
                    change = 2;
                    System.out.println("change 2");
                }
                while (brightnessChange < 100 && change == 2){
                    new BrightnessCommand(controller, "brightness", brightnessChange += 10);
                    System.out.println(brightnessChange);
                    if (brightnessChange >= 89){
                        change = 3;
                        System.out.println("change 3");
                    }
                }
                while (change == 3){
                    Random random = new Random();
                    Float r = random.nextFloat();
                    Float g = random.nextFloat();
                    Float b = random.nextFloat();
                    Color randomcolor = new Color(r, g, b);
                    count++;
                    new ColorCommand(controller, "color", randomcolor.toString());       //Absprechen, ob Color als String oder als Color ToDo
                    System.out.println(randomcolor);

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
