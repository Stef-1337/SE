package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.commands.command.AbstractCommand;

import java.util.List;

public class StateCommand extends AbstractCommand {

    private boolean to;

    public StateCommand(CommandParameterData data){
        this(data.getController(), data.getName(), data.isOn());
    }

    public StateCommand(AbstractLampController controller, String command, boolean state) {
        super(controller, command);
        this.to = state;
    }

    @Override
    public void execute(AbstractLampController controller) {
        controller.switchChanged(to);
        System.out.println("state");
        System.out.println(controller.getAdapter().getLampe());
    }

    @Override
    public List<String> getConfig() {
        return List.of("State: " + to);
    }
}
