package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.HueColor;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.commands.command.AbstractCommand;

import java.util.List;

public class LampCommand extends AbstractCommand {
    private Boolean to;
    private Float brightness;
    private HueColor hueColor;

    public LampCommand(CommandParameterData data) {
        this(data.getController(), data.getName(), data.isOn(), (float) data.getIntensity(), data.getColor());
    }

    public LampCommand(AbstractLampController controller, String name, Boolean to, Float brightness, HueColor hueColor) {
        super(controller, name);
        this.to = to;
        this.brightness = brightness;
        this.hueColor = hueColor;
    }

    @Override
    public void execute(AbstractLampController controller) {
        new StateCommand(controller, "state", to).execute(controller);
        new BrightnessCommand(controller, "brightness", brightness).execute(controller);
        new ColorCommand(controller, "color", hueColor).execute(controller);

    }

    @Override
    public List<String> getConfig() {
        return List.of("State:" + to + "\n Brightness: " + brightness + "\n HueColor: " + hueColor.getName());
    }
}
