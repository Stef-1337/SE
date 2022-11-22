package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

import java.util.List;

public class DimPlus extends AbstractCommand {
    private float plus;
    public DimPlus(AbstractLampController controller, String name, float plus) {
        super(controller, name);
        this.plus = plus;
    }



    @Override
    public void execute(AbstractLampController controller) {
        if (controller.getAdapter().getIntensity() + plus <= 100){
            new BrightnessCommand(controller, "dimPlus", controller.getAdapter().getIntensity() + plus);
        }else{
            new BrightnessCommand(controller, "dim100", 100F);
        }

    }
    @Override
    public List<String> getConfig() {
        return List.of("Dim: +" + plus);
    }
}
