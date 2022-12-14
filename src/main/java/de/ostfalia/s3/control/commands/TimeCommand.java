package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.commands.command.AbstractThreadCommand;

import java.util.List;

public class TimeCommand extends AbstractThreadCommand {
    private int time;

    public TimeCommand(CommandParameterData data) {
        this(data.getController(), data.getName(), data.getTime());
    }

    public TimeCommand(AbstractLampController controller, String name, int time) {
        super(controller, name);
        this.time = time;
    }

    @Override
    public void execute(AbstractLampController controller) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(time);
                new StateCommand(controller, "time", !controller.getAdapter().getState()).execute(controller);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        runThread(thread);

    }

    @Override
    public List<String> getConfig() {
        return List.of("Time: " + time * 0.001 + "s\nState: toggle ");
    }
}
