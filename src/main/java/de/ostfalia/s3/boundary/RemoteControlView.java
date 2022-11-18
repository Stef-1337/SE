package de.ostfalia.s3.boundary;

import de.ostfalia.s1.lamp.Lamp;
import de.ostfalia.s3.control.CommandProcessor;
import de.ostfalia.s3.control.commands.AbstractCommand;
import de.ostfalia.s3.control.commands.StateCommand;

import java.awt.*;
import java.util.HashMap;
import java.util.Optional;

public class RemoteControlView {

    private static final int SIZE = 8;

    private CommandProcessor commandProcessor;

    private HashMap<Integer, AbstractCommand> commands;

    private Lamp status;

    //TODO intialize variables

    public void setCommand(int slot, AbstractCommand command) {
        if (slot < SIZE) commands.put(slot, command);
    }

    public void applySwitchButton(int slot) {
        setCommand(slot, new StateCommand("On Command", status.getState()));
    }

    public void applyColorButton(int slot){
        //setCommand(slot, new ColorCommand("Farbe " + status.getColor().toString(), status.getColor()));
    }

    public void onButtonClick(int slot) {
        Optional.ofNullable(commands.get(slot)).ifPresent(command -> commandProcessor.execute(command));
    }

}
