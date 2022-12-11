package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s3.control.CommandProcessor;
import de.ostfalia.s3.control.commands.command.AbstractCommand;

import java.util.List;

public class UndoCommand extends AbstractCommand {

    private int index;
    private CommandProcessor processor;

    public UndoCommand(AbstractLampController controller, String name, int index, CommandProcessor processor) {
        super(controller, name);
        this.index = index;
        this.processor = processor;
    }

    @Override
    public List<String> getConfig() {
        return List.of("Index: " + (index == -1 ? "Last" : index));
    }

    @Override
    public void execute(AbstractLampController controller) {
        processor.undo(index);
    }
}
