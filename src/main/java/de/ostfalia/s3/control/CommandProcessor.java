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

    public void undo() {
        undo(Math.min(0, undoList.size() - 1));
    }

    public void undo(int index) {
        if (undoList.size() > 0) {
            int i = undoList.size() - index;
            System.out.println("Index: " + i + " vs " + undoList.size() + " vs " + index);
            if (index <= undoList.size()) {
                ICommand command = undoList.get(i);
                System.out.println("Going back to " + command + " (" + i + ")");

                if (command instanceof AbstractCommand abstractCommand) {
                    if (abstractCommand instanceof AbstractThreadCommand threadCommand) threadCommand.stopThread();
                    abstractCommand.undo();

                } else command.undo(controller);

                for (int x = index - 1; x < undoList.size(); x++) undoList.remove(x);
                if (undoList.size() > 0) {
                    ICommand current = undoList.get(undoList.size() - 1);
                    if (current instanceof AbstractThreadCommand threadCommand) threadCommand.execute(controller);
                }
            }else System.out.println("Not enough commands");
        }
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

            if (command != null && !(command instanceof UndoCommand))
                undoList.add(command);
        } else
            command.execute(controller);
    }

    public void execute(ICommand... commands) {
        for (ICommand command : commands)
            execute(command);
    }

}
