package de.ostfalia.s3.control;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s3.control.commands.ICommand;

import java.util.List;

public class CommandProcessor {

    private AbstractLampController controller;

    // aus          grün        100             gelb
    //state(an), color(gelb), brightness(50), color(grün), color(blau)
    private List<ICommand> undoList;

    public CommandProcessor(AbstractLampController controller){
        this.controller = controller;
    }

    public void undo(){
        ICommand command = undoList.get(undoList.size() - 1);
        command.undo(controller);

        undoList.remove(command);
    }

    public void execute(ICommand command){
        command.execute(controller);
        //TODO undoList füllen
    }

    public void execute(ICommand... commands){
        for(ICommand command : commands)
            execute(command);
    }

}
