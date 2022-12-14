package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s1.lamp.HueColor;
import de.ostfalia.s3.control.commands.command.AbstractThreadCommand;

import java.util.List;
import java.util.Random;

public class colorDimSequenceCommand extends AbstractThreadCommand {


    public colorDimSequenceCommand(AbstractLampController controller, String name) {
        super(controller, name);
    }

    @Override
    public List<String> getConfig() {
        return null;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Thread thread = new Thread(() -> {
            int time = 10;
            try {
                ColorSelector colorSelector = new ColorSelector();
                Float brightnessChange = controller.getAdapter().getIntensity();
                List<HueColor> colorList = colorSelector.getColorList();
                Random rand = new Random();

                while (!getThread().isInterrupted()) {
                    while (brightnessChange > 0) {
                        new BrightnessCommand(controller, "brightness", brightnessChange -= time).execute(controller);
                        Thread.sleep(time * 2);

                        if (brightnessChange <= 0) {
                            new ColorCommand(controller, "color", colorList.get(rand.nextInt(colorList.size()))).execute(controller);
                        }
                    }
                    while (brightnessChange < 100) {
                        new BrightnessCommand(controller, "brightness", brightnessChange += time).execute(controller);
                        Thread.sleep(time * 2);

                        if (brightnessChange >= 100) {
                            new ColorCommand(controller, "color", colorList.get(rand.nextInt(colorList.size()))).execute(controller);
                        }
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        runThread(thread);
    }
}
