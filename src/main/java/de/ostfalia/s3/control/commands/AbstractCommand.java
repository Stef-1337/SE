package de.ostfalia.s3.control.commands;

import lombok.Getter;

public abstract class AbstractCommand implements ICommand {

    @Getter
    private String name;

    public AbstractCommand(String name){
        this.name = name;
    }

}
