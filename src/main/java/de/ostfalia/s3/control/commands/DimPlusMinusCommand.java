package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

import java.util.List;

public class DimPlusMinusCommand extends AbstractCommand {
    private float change;
    public DimPlusMinusCommand(AbstractLampController controller, String name, float change) {
        super(controller, name);
        this.change = change;
    }



    @Override
    public void execute(AbstractLampController controller) {
        if (controller.getAdapter().getIntensity() + change <= 100 && controller.getAdapter().getIntensity() + change >= 0){
            new BrightnessCommand(controller, "dimPlus", controller.getAdapter().getIntensity() + change).execute(controller);
        }else {
            if (controller.getAdapter().getIntensity() + change > 100) {
                new BrightnessCommand(controller, "dim100", 100F).execute(controller);
            }else {
                new BrightnessCommand(controller, "dim0", 0F).execute(controller);
            }
        }

    }
    @Override
    public List<String> getConfig() {
        return List.of("Dim: +" + change);
    }
}
