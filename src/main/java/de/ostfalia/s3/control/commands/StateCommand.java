package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

public class StateCommand extends AbstractCommand {

    private AbstractLampController controller;

    private boolean to;

    public StateCommand(String command, boolean state){
        super(command);
        this.to = state;
    }

    @Override
    public void execute(AbstractLampController controller) {
        //controller.switchChanged(to);
    }

    @Override
    public void undo(AbstractLampController controller) {
        new StateCommand(getName(), !to).execute(controller);
    }
}
