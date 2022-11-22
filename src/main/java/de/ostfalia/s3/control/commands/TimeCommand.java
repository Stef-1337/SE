package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

import java.util.List;

public class TimeCommand extends AbstractThreadCommand {
    private int time;
    private boolean to;

    public TimeCommand(AbstractLampController controller, String name, int time, boolean to) {
        super(controller, name);
        this.time = time;
        this.to = to;
    }

    @Override
    public void execute(AbstractLampController controller) {
        try {
            Thread.sleep(time);
            new StateCommand(controller, "time", !to);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getConfig() {
        return List.of("Time: " + time + "State: " + !to);
    }
}
