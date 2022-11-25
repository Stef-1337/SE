package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s1.lamp.HueColor;

import java.util.List;

public class RainbowCommand extends AbstractThreadCommand {
    private int time;

    public RainbowCommand(AbstractLampController controller, String name, int time) {
        super(controller, name);
        this.time = time;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Thread thread = new Thread(() -> {
            try {
                Float brightness = controller.getAdapter().getIntensity();
                while (!getThread().isInterrupted()) {
                    ColorSelector colorSelector = new ColorSelector();
                    for (int i = 8; i > 1; i--) {
                        if (!(i == 4)) {
                            Thread.sleep(time);
                            new LampCommand(controller, "rainbow", true, brightness, colorSelector.getColors().get(i)).execute(controller);
                        }
                    }
                    for (int i = 2; i < 9; i++) {
                        if (!(i == 4)) {
                            Thread.sleep(time);
                            new LampCommand(controller, "rainbow", true, brightness, colorSelector.getColors().get(i)).execute(controller);
                        }
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        runThread(thread);
    }

    @Override
    public List<String> getConfig() {
        return List.of("Rainbow: Time between: " + time);
    }
}
