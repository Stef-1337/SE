package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.commands.command.AbstractThreadCommand;

import java.util.List;

public class SOSCommand extends AbstractThreadCommand {


    private int time;

    public SOSCommand(CommandParameterData data) {
        this(data.getController(), data.getName(), data.getTime());
    }

    public SOSCommand(AbstractLampController controller, String name, int time) {
        super(controller, name);
        this.time = time;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Thread thread = new Thread(() -> {
            try {
                new StateCommand(controller, "off", false).execute(controller);
                Thread.sleep(time);
                while (!getThread().isInterrupted()) {


                    for (int i = 0; i < 3; i++) {
                        new StateCommand(controller, "on", true).execute(controller);
                        Thread.sleep(time * 2);
                        new StateCommand(controller, "off", false).execute(controller);
                        Thread.sleep(time);
                    }
                    for (int i = 0; i < 3; i++) {
                        new StateCommand(controller, "on", true).execute(controller);
                        Thread.sleep(time * 4);
                        new StateCommand(controller, "off", false).execute(controller);
                        Thread.sleep(time);
                    }
                    for (int i = 0; i < 3; i++) {
                        new StateCommand(controller, "on", true).execute(controller);
                        Thread.sleep(time * 2);
                        new StateCommand(controller, "off", false).execute(controller);
                        Thread.sleep(time);
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
        return List.of("Intervall: " + time + "ms");
    }
}
