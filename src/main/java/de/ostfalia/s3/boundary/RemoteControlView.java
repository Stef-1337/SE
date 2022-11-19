package de.ostfalia.s3.boundary;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.CommandProcessor;
import de.ostfalia.s3.control.commands.AbstractCommand;
import de.ostfalia.s3.control.commands.StateCommand;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Named
@SessionScoped
public class RemoteControlView implements Serializable {

    private static final int SIZE = 8;

    private List<Integer> slots;

    private CommandProcessor commandProcessor;

    private HashMap<Integer, AbstractCommand> commands = new HashMap<>();

    private AbstractLampController controller;

    private int slotSelected;

    private CommandParameterData data = new CommandParameterData();

    public RemoteControlView(){
        slots = new ArrayList<>(SIZE);

        for (int i = 1; i < 9; i++){
            slots.add(i);
        }
    }

    public void setCommand(int slot, AbstractCommand command) {
        if (slot < SIZE) commands.put(slot, command);
    }

    public Optional<AbstractCommand> getCommand(int slot){
        return Optional.ofNullable(commands.get(slot));
    }

    public void onButtonClick() {
        getCommand(slotSelected).ifPresent(command -> commandProcessor.execute(command));
    }

    public void onApplySwitchButtonClick() {
        setCommand(slotSelected, new StateCommand(controller, data.getName(), data.isOn()));
    }

    public void onApplyColorButtonClick(){
        //setCommand(slot, new ColorCommand("Farbe " + status.getColor().toString(), status.getColor()));
    }

    public List<String> getConfig(int slot){
       return null;
    }

}

