package de.ostfalia.s3.boundary;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.Java2NodeRedLampAdapter;
import de.ostfalia.s3.control.ColorService;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.CommandProcessor;
import de.ostfalia.s3.control.commands.AbstractCommand;
import de.ostfalia.s3.control.commands.BrightnessCommand;
import de.ostfalia.s3.control.commands.ColorCommand;
import de.ostfalia.s3.control.commands.StateCommand;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.*;
import java.awt.*;
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

    private String name;

    private List<Integer> slots;

    @Inject
    private ColorService colorService;

    private CommandProcessor commandProcessor;

    private HashMap<Integer, AbstractCommand> commands = new HashMap<>();

    private AbstractLampController controller;

    private int slotSelected;

    private CommandParameterData data = new CommandParameterData();

    public RemoteControlView(){
        slots = new ArrayList<>(SIZE);

        initDefaults();

        for (int i = 1; i < 9; i++){
            slots.add(i);
        }
    }

    private void initDefaults(){
        commands.put(1, new StateCommand(controller, "On", true));
        commands.put(2, new StateCommand(controller, "Off", false));
    }

    public void setCommand(int slot, AbstractCommand command) {
        System.out.println("Try setting " + slot + ", " + command);
        if(command.getName().length() != 0)
            if (slot < SIZE){
                commands.put(slot, command);
                data = new CommandParameterData();
            }
    }

    public void addCommand(AbstractCommand command){
        setCommand(slotSelected, command);
    }

    public Optional<AbstractCommand> getCommand(int slot){
        return Optional.ofNullable(commands.get(slot));
    }

    public AbstractCommand getCommandUnchecked(int slot){
        return commands.get(slot);
    }

    public void onButtonClick() {
        getCommand(slotSelected).ifPresent(command -> commandProcessor.execute(command));
    }

    public void onApplySwitchButtonClick(ActionEvent event){
        addCommand(new StateCommand(controller, data.getName(), data.isOn()));
    }

    public void onApplyBrightnessButtonClick(ActionEvent event) {
        addCommand(new BrightnessCommand(controller, data.getName(), data.getIntensity()));
    }

    public void onApplyColorButtonClick(ActionEvent event){
        //TODO wird noch nicht aufgerufen
        addCommand(new ColorCommand(controller, data.getName(), data.getColors().get(0)));
    }

}

