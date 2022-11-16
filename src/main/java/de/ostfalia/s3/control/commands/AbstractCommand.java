package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import lombok.Getter;

public abstract class AbstractCommand implements ICommand {

    @Getter
    private String name;
    @Getter
    private AbstractLampController controller;

    public AbstractCommand(AbstractLampController controller, String name){
        this.name = name;
        this.controller = controller;
    }



}
