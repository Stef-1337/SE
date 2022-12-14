package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s3.control.commands.command.AbstractThreadCommand;

import java.util.List;

public class FlashDimSequenceCommand extends AbstractThreadCommand {


    public FlashDimSequenceCommand(AbstractLampController controller, String name) {
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
                while (!getThread().isInterrupted()) {
                    for (int i = 0; i < 2; i++) {
                        new VerlaufCommand(controller, "dim", time).execute(controller);
                        Thread.sleep(5);
                    }
                    for (int i = 0; i < 3; i++) {
                        new FlashCommand(controller, "flash", time / 2).execute(controller);
                        Thread.sleep(1);
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        runThread(thread);
    }
}
