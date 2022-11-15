package de.ostfalia.s3.command;

import de.ostfalia.s1.lamp.AbstractLampController;

public class StateCommand implements ICommand {

    private AbstractLampController controller;

    private boolean to;

    public StateCommand(boolean state){
        this.to = state;
    }

    @Override
    public void execute() {
        controller.switchChanged(to);
    }

    @Override
    public ICommand undo() {
        return new StateCommand(!to);
    }
}
