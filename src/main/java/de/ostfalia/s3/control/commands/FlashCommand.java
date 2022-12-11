package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s3.control.commands.command.AbstractThreadCommand;

import java.util.List;

public class FlashCommand extends AbstractThreadCommand {
    private int time;

    public FlashCommand(AbstractLampController controller, String name, int time) {
        super(controller, name);
        this.time = time;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Thread thread = new Thread(() -> {
            try {
                while (!getThread().isInterrupted()) {
                    new StateCommand(controller, "state", true).execute(controller);
                    Thread.sleep(time);
                    new StateCommand(controller, "state", false).execute(controller);
                    Thread.sleep(time);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        runThread(thread);
    }

    @Override
    public List<String> getConfig() {
        return List.of("Flash: " + time + "ms");
    }
}
