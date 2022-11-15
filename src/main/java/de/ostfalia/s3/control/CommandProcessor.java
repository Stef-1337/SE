package de.ostfalia.s3.control;

import de.ostfalia.s1.lamp.AbstractLampController;

import java.util.List;

public class CommandProcessor {

    private AbstractLampController controller;

    private List<ICommand> undoList;

    public void execute(ICommand command){
        command.execute();
    }

    public void execute(ICommand... commands){
        for(ICommand command : commands)
            execute(command);
    }

}
