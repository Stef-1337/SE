package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

public interface ICommand {

    void execute(AbstractLampController controller);
    ICommand undo(AbstractLampController controller);

}
