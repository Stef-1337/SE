package de.ostfalia.s3.boundary;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.Lamp;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.CommandProcessor;
import de.ostfalia.s3.control.commands.AbstractCommand;
import de.ostfalia.s3.control.commands.StateCommand;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;

@Getter
@Setter
@Named
@SessionScoped
public class RemoteControlView implements Serializable {

    private static final int SIZE = 8;

    private CommandProcessor commandProcessor;

    private HashMap<Integer, AbstractCommand> commands = new HashMap<>();

    private AbstractLampController controller;

    private int slot;
    private CommandParameterData data = new CommandParameterData();

    public void setCommand(int slot, AbstractCommand command) {
        if (slot < SIZE) commands.put(slot, command);
    }

    public Optional<AbstractCommand> getCommand(int slot){
        return Optional.ofNullable(commands.get(slot));
    }

    public void onButtonClick() {
        getCommand(slot).ifPresent(command -> commandProcessor.execute(command));
    }

    public void onApplySwitchButtonClick() {
        setCommand(slot, new StateCommand(controller, data.getName(), data.isOn()));
    }

    public void onApplyColorButtonClick(){
        //setCommand(slot, new ColorCommand("Farbe " + status.getColor().toString(), status.getColor()));
    }

}

