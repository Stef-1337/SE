package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.HueColor;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.commands.command.AbstractCommand;

import java.util.List;

public class ColorCommand extends AbstractCommand {


    private HueColor hueColor;

    public ColorCommand(CommandParameterData data) {
        this(data.getController(), data.getName(), data.getColor());
    }

    public ColorCommand(AbstractLampController controller, String name, HueColor hueColor) {
        super(controller, name);
        this.hueColor = hueColor;
    }

    @Override
    public void execute(AbstractLampController controller) {
        controller.colorChanged(String.valueOf(hueColor));
        System.out.println(controller.getAdapter().getLampe());
    }

    @Override
    public List<String> getConfig() {
        return List.of("HueColor: " + hueColor.getName());
    }
}
