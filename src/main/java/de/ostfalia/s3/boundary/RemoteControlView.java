package de.ostfalia.s3.boundary;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.CommandProcessor;
import de.ostfalia.s3.control.commands.command.AbstractCommand;
import de.ostfalia.s3.control.commands.StateCommand;
import de.ostfalia.s3.control.commands.UndoCommand;
import de.ostfalia.s3.control.CommandFactory;
import de.ostfalia.s3.control.commands.command.ICommand;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
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
    private String name;

    private CommandProcessor commandProcessor;

    private AbstractLampController controller = new AbstractLampController();
    private ColorSelector colorSelector = new ColorSelector();

    private HashMap<Integer, AbstractCommand> commands = new HashMap<>();

    private int slotSelected;

    private CommandParameterData data = new CommandParameterData(controller, colorSelector);

    private CommandFactory factory;

    public RemoteControlView() {
        slots = new ArrayList<>(SIZE);

        commandProcessor = new CommandProcessor(controller);
        factory = new CommandFactory(controller);

        initDefaults();

        for (int i = 1; i < 9; i++) {
            slots.add(i);
        }
    }

    private void initDefaults() {
        commands.put(1, new StateCommand(controller, "On", true));
        commands.put(2, new StateCommand(controller, "Off", false));
    }

    private void resetCommands() {
        commands.clear();
        initDefaults();
    }

    public void setCommand(int slot, AbstractCommand command) {
        System.out.println("Try setting " + slot + ", " + command);
        if (command.getName().length() != 0)
            if (slot <= SIZE) {
                commands.put(slot, command);
                data = new CommandParameterData(controller, colorSelector);
                slotSelected = 0;
            }
    }

    public void addCommand(AbstractCommand command) {
        setCommand(slotSelected, command);
    }

    public Optional<AbstractCommand> getCommand(int slot) {
        return Optional.ofNullable(commands.get(slot));
    }

    public AbstractCommand getCommandUnchecked(int slot) {
        return commands.get(slot);
    }

    public void onRunCommandClick(int slot) {
        AbstractCommand command = getCommandUnchecked(slot);
        if (command != null)
            commandProcessor.execute(command);
        else System.out.println("No command found");
    }

    public void onApplyCommandClick(String clazz) {
        ICommand command = factory.createCommand(clazz, data);

        if (command instanceof AbstractCommand abstractCommand)
            addCommand(abstractCommand);
        else System.out.println("Invalid command.");
    }

    public void onSelectBoxClick(ValueChangeEvent event) {
        String undoString = (String) event.getNewValue();
        new UndoCommand(controller, "Undo", Integer.parseInt(undoString.split(":")[0]), commandProcessor).execute(controller);
    }

    public void onRunResetButtonClick() {
        resetCommands();
    }

    public void viewCommands() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .resizable(false)
                .build();
        PrimeFaces.current().dialog().openDynamic("/RemoteControl/commandView", options, null);
    }


}

