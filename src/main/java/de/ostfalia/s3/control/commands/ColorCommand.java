package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

import java.awt.*;

public class ColorCommand extends AbstractCommand {

    private Color color;

    public ColorCommand(AbstractLampController controller, String name, Color color) {
        super(controller, name);
        this.color = color;
    }

    @Override
    public void execute(AbstractLampController controller) {
        controller.colorChanged(color.toString());
    }
}
