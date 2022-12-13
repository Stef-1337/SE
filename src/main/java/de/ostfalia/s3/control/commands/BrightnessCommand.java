package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.commands.command.AbstractCommand;

import java.util.List;

public class BrightnessCommand extends AbstractCommand {

    private float brightness;

    public BrightnessCommand(CommandParameterData data){
        this(data.getController(), data.getName(), data.getIntensity());
    }

    public BrightnessCommand(AbstractLampController controller, String name, float brightness) {
        super(controller, name);
        this.brightness = brightness;
    }

    @Override
    public void execute(AbstractLampController controller) {
        controller.brightnessChanged(brightness);
        System.out.println(controller.getAdapter().getLampe());
    }

    @Override
    public List<String> getConfig() {
        return List.of("Brightness: " + brightness);
    }
}
