package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

public class StateCommand extends AbstractCommand {

    private boolean to;

    public StateCommand(AbstractLampController controller, String command, boolean state){
        super(controller, command);
        this.to = state;
    }

    @Override
    public void execute(AbstractLampController controller) {
        controller.switchChanged(to);
        System.out.println("state");
        System.out.println(controller.getAdapter().getLampe());
    }

}
