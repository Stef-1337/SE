package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.Lamp;
import lombok.Getter;

public abstract class AbstractCommand implements ICommand {

    @Getter
    private String name;
    @Getter
    private AbstractLampController controller;
    @Getter
    private Lamp status;

    public AbstractCommand(Lamp status, AbstractLampController controller, String name){
        this.name = name;
        this.controller = controller;
        this.status = status;
    }



}
