package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s3.control.commands.command.AbstractCommand;

import java.util.List;

public class ToggleCommand extends AbstractCommand {

    public ToggleCommand(AbstractLampController controller, String name) {
        super(controller, name);
    }

    @Override
    public void execute(AbstractLampController controller) {
        new StateCommand(controller, "toggle", !controller.getAdapter().getState()).execute(controller);
    }

    @Override
    public List<String> getConfig() {
        return List.of("Toggle");
    }

}
