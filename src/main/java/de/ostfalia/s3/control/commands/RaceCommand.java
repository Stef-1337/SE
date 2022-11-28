package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.HueColor;
import de.ostfalia.s2.fahrrad.entity.Bicycle;

import java.util.List;

public class RaceCommand extends AbstractCommand {

    private int bChannel1;
    private int bChannel2;
    private HueColor hueColor1;
    private HueColor hueColor2;

    public RaceCommand (AbstractLampController controller, String name, int bChannel1, int bChannel2, HueColor hueColor1, HueColor hueColor2) {
        super(controller, name);
        this.bChannel1 = bChannel1;
        this.bChannel2 = bChannel2;
        this.hueColor1 = hueColor1;
        this.hueColor2 = hueColor2;
    }

    @Override
    public List<String> getConfig() {
        return List.of("bicycle "+ bChannel1 + ":" + hueColor1.getName() + " bicyle" + bChannel2 + ":" + hueColor2.getName());
    }

    @Override
    public void execute(AbstractLampController controller) {

    }
}
