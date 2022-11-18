package de.ostfalia.s3.control;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.Lamp;
import de.ostfalia.s1.lamp.LampController;
import de.ostfalia.s3.control.commands.AbstractCommand;
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
        ICommand command = undoList.get(undoList.size() - 1);

        if (command instanceof AbstractCommand abstractCommand) {
            abstractCommand.undo();
        } else command.undo(controller);

        undoList.remove(command);
    }

    public void undo(int index){
        //TODO
    }

    public void execute(ICommand command) {
        command.execute(controller);
    }

    public void execute(AbstractCommand command) {
        command = command.executeAndClone();

        if (command != null)
            undoList.add(command);
    }

    public void execute(ICommand... commands) {
        for (ICommand command : commands)
            execute(command);
    }

}
