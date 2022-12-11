package de.ostfalia.s3.control.commands.command;

import de.ostfalia.s1.lamp.AbstractLampController;

import java.util.List;

public interface ICommand {

    void execute(AbstractLampController controller);
    void undo(AbstractLampController controller);
    String getName();
    List<String> getConfig();


}
