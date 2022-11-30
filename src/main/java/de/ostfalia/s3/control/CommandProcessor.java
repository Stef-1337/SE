package de.ostfalia.s3.control;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.Lamp;
import de.ostfalia.s1.lamp.LampController;
import de.ostfalia.s3.control.commands.AbstractCommand;
import de.ostfalia.s3.control.commands.AbstractThreadCommand;
import de.ostfalia.s3.control.commands.ICommand;
import de.ostfalia.s3.control.commands.StateCommand;
import de.ostfalia.s3.control.commands.UndoCommand;
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

    public void undo(int index) {
        if(index == -1)
            index = undoList.size() - 1;

        int size = undoList.size();

        if (size > 0 && size > index) {
            ICommand command = undoList.get(index);

            for (int i = size - 1; i >= index; i--) {
                undoList.remove(i);
            }
            if (command instanceof AbstractCommand abstractCommand) {
                if (abstractCommand instanceof AbstractThreadCommand threadCommand) threadCommand.stopThread();
                abstractCommand.undo();

            } else command.undo(controller);

        } else System.out.println("Nothing left to undo..");
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

            if (command != null && !(command instanceof UndoCommand)) {
                undoList.add(command);
                if(undoList.size() > 5)
                    undoList.remove(0);
            }
        } else
            command.execute(controller);
    }

    public void execute(ICommand... commands) {
        for (ICommand command : commands)
            execute(command);
    }

}
