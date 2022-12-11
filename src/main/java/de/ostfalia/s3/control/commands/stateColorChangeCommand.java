package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s1.lamp.HueColor;
import de.ostfalia.s3.control.commands.command.AbstractThreadCommand;

import java.util.List;
import java.util.Random;

public class stateColorChangeCommand extends AbstractThreadCommand {

    private int time;

    public stateColorChangeCommand(AbstractLampController controller, String name, int time) {
        super(controller, name);
        this.time = time;
    }

    @Override
    public List<String> getConfig() {
        return null;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Thread thread = new Thread(() -> {
            try {
                ColorSelector colorSelector = new ColorSelector();
                List<HueColor> colorList = colorSelector.getColorList();

                Random rand = new Random();
                int randInt = rand.nextInt(rand.nextInt(colorList.size()));


                while (!getThread().isInterrupted()) {

                    new StateCommand(controller, "state", true).execute(controller);

                    for(int i = 0; i < colorList.size(); i ++) {
                        new ColorCommand(controller, "color", colorList.get(i)).execute(controller);
                        Thread.sleep(time);

                        if (i == randInt) {
                            new StateCommand(controller, "state", false).execute(controller);
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
