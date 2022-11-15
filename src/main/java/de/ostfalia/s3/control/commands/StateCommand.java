package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

public class StateCommand implements ICommand {

    private AbstractLampController controller;

    private boolean to;

    public StateCommand(boolean state){
        this.to = state;
    }

    @Override
    public void execute(AbstractLampController controller) {
        //controller.switchChanged(to);
    }

    @Override
    public ICommand undo(AbstractLampController controller) {
        return new StateCommand(!to);
    }
}
