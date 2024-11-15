package de.ostfalia.s3.control;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.Lamp;
import de.ostfalia.s3.control.commands.command.AbstractCommand;
import de.ostfalia.s3.control.commands.command.AbstractThreadCommand;
import de.ostfalia.s3.control.commands.command.ICommand;
import de.ostfalia.s3.control.commands.UndoCommand;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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
        if (index == -1)
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

            if (undoList.size() > 0) {
                ICommand current = undoList.get(undoList.size() - 1);
                if (current instanceof AbstractThreadCommand currentThread)
                    currentThread.execute(controller);
            }
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
                if (undoList.size() > 5)
                    undoList.remove(0);
            }
        } else
            command.execute(controller);
    }

    public void execute(ICommand... commands) {
        for (ICommand command : commands)
            execute(command);
    }

    public int getIndex(ICommand iCommand) {
        for (int i = 0; i < undoList.size(); i++) {
            if (undoList.get(i).equals(iCommand)) {
                return i;
            }
        }
        return -1;
    }

    public List<String> getUndoListString() {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < undoList.size(); i++) {
            ICommand command = undoList.get(i);

            String description = i + ": " + command.getName();

            if (command instanceof AbstractCommand abstractCommand) {
                Lamp status = abstractCommand.getStatus();
                String colorName = status.getColorName();
                description += " (Vorher: " + (status.getState() ? "an" : "aus") + ", " + (colorName.equals("null") ? "XY: " + status.getX() + ", " + status.getY() : "Farbe: " + colorName) + ", " + status.getIntensity() + ")";
            }

            stringList.add(description);
        }
        return stringList;
    }
}
