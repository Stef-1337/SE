package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.commands.command.AbstractCommand;

import java.util.List;

public class DimCommand extends AbstractCommand {
    private float change;

    public DimCommand(CommandParameterData data) {
        this(data.getController(), data.getName(), data.getIntensityStep());
    }

    public DimCommand(AbstractLampController controller, String name, float change) {
        super(controller, name);
        this.change = change;
    }


    @Override
    public void execute(AbstractLampController controller) {
        if (controller.getAdapter().getIntensity() + change <= 100 && controller.getAdapter().getIntensity() + change >= 0) {
            new BrightnessCommand(controller, "dimPlus", controller.getAdapter().getIntensity() + change).execute(controller);
        } else {
            if (controller.getAdapter().getIntensity() + change > 100) {
                new BrightnessCommand(controller, "dim100", 100F).execute(controller);
            } else {
                new BrightnessCommand(controller, "dim0", 0F).execute(controller);
            }
        }

    }

    @Override
    public List<String> getConfig() {
        String s = "";
        if (change > 0) {
            s = "+";
        }
        return List.of("Dim: " + s + change);
    }
}
