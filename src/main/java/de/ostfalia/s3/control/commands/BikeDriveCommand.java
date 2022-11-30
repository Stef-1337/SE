package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;

import java.util.List;

public class BikeDriveCommand extends AbstractThreadCommand{
    BicycleDetailData ms;
    public BikeDriveCommand(AbstractLampController controller, String name, BicycleDetailData speed) {
        super(controller, name);
        this.ms = speed;

    }

    @Override
    public List<String> getConfig() {
        return List.of("Drive: Geschwindigkeit" + ms);
    }

    @Override
    public void execute(AbstractLampController controller) {

        for (int i = 0; i < ms.getSize(); i++) {
            float km = (float) ms.getValue(i) / 3600 ;
            float helligkeit = (km / 50) * 100;
            new BrightnessCommand(controller,"fahrrad", helligkeit).execute(controller);
        }

    }

}
