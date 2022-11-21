package de.ostfalia.s3.control;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.Lamp;
import de.ostfalia.s1.lamp.LampController;
import de.ostfalia.s3.control.commands.AbstractCommand;
import de.ostfalia.s3.control.commands.AbstractThreadCommand;
import de.ostfalia.s3.control.commands.ICommand;
import de.ostfalia.s3.control.commands.StateCommand;
import lombok.Getter;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandProcessor {

    private AbstractLampController controller;

    // aus          grün        100             gelb
    //state(an), color(gelb), brightness(50), color(grün), color(blau)
    @Getter
    private List<ICommand> undoList;

    public CommandProcessor(AbstractLampController controller) {
        this.controller = controller;
        this.undoList = new ArrayList<>();
    }

    public void undo() {
        if (undoList.size() > 0) {
            ICommand command = undoList.get(undoList.size() - 1);

            if (command instanceof AbstractCommand abstractCommand) {
                if (abstractCommand instanceof AbstractThreadCommand threadCommand) threadCommand.stopThread();
                abstractCommand.undo();

            } else command.undo(controller);

            undoList.remove(command);
            if (undoList.size() > 0) {
                ICommand current = undoList.get(undoList.size() - 1);
                if (current instanceof AbstractThreadCommand threadCommand) threadCommand.execute(controller);
            }
        }
    }

    public void undo(int index) {
        //TODO
    }

    public void execute(ICommand command) {
        int index = undoList.size() - 1;
        if (index >= 0) {
            ICommand previous = undoList.get(index);
            if (previous instanceof AbstractThreadCommand) {
                ((AbstractThreadCommand) previous).stopThread();
            }
        }

        if (command instanceof AbstractCommand) {
            command = ((AbstractCommand) command).executeAndClone();

            if (command != null)
                undoList.add(command);
        } else
            command.execute(controller);
    }

    public void execute(ICommand... commands) {
        for (ICommand command : commands)
            execute(command);
    }

}
