package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

import java.util.List;

public class DimMinus extends AbstractCommand{
    private float minus;
    public DimMinus(AbstractLampController controller, String name, float minus) {
        super(controller, name);
        this.minus = minus;
    }




    @Override
    public void execute(AbstractLampController controller) {
        if (controller.getAdapter().getIntensity() - minus >= 0) {
            new BrightnessCommand(controller, "dimMinus", controller.getAdapter().getIntensity() - minus);
        }else{
            new BrightnessCommand(controller, "dim0", 0F);
        }
    }
    @Override
    public List<String> getConfig() {
        return null;
    }
}
