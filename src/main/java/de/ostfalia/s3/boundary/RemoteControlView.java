package de.ostfalia.s3.boundary;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s1.lamp.HueColor;
import de.ostfalia.s2.fahrrad.boundary.BicycleDetailView;
import de.ostfalia.s3.control.ColorService;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.CommandProcessor;
import de.ostfalia.s3.control.commands.AbstractCommand;
import de.ostfalia.s3.control.commands.BrightnessCommand;
import de.ostfalia.s3.control.commands.ColorCommand;
import de.ostfalia.s3.control.commands.DimCommand;
import de.ostfalia.s3.control.commands.FlashCommand;
import de.ostfalia.s3.control.commands.LampCommand;
import de.ostfalia.s3.control.commands.PartyCommand;
import de.ostfalia.s3.control.commands.RaceCommand;
import de.ostfalia.s3.control.commands.RainbowCommand;
import de.ostfalia.s3.control.commands.SOSCommand;
import de.ostfalia.s3.control.commands.StateCommand;
import de.ostfalia.s3.control.commands.TimeCommand;
import de.ostfalia.s3.control.commands.UndoCommand;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Named
@SessionScoped
public class RemoteControlView implements Serializable {

    private static final int SIZE = 8;

    int undoIndex;
    private List<Integer> slots;
    private String name;

    @Inject
    private ColorService colorService;

    private CommandProcessor commandProcessor;

    private AbstractLampController controller = new AbstractLampController();
    private ColorSelector colorSelector = new ColorSelector();

    private HashMap<Integer, AbstractCommand> commands = new HashMap<>();

    private int slotSelected;

    private CommandParameterData data = new CommandParameterData(colorSelector);

    public RemoteControlView() {
        slots = new ArrayList<>(SIZE);

        commandProcessor = new CommandProcessor(controller);

        initDefaults();

        for (int i = 1; i < 9; i++) {
            slots.add(i);
        }
    }

    private void initDefaults() {
        commands.put(1, new StateCommand(controller, "On", true));
        commands.put(2, new StateCommand(controller, "Off", false));
    }

    private void resetCommands(){
        commands.clear();
        initDefaults();
    }

    public void setCommand(int slot, AbstractCommand command) {
        System.out.println("Try setting " + slot + ", " + command);
        if (command.getName().length() != 0)
            if (slot <= SIZE) {
                commands.put(slot, command);
                data = new CommandParameterData(colorSelector);
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

    public void onRunCommandClick(int slot){
        AbstractCommand command = getCommandUnchecked(slot);
        if(command != null)
            commandProcessor.execute(command);
        else System.out.println("No command found");
    }

    public void onApplySwitchButtonClick() {
        addCommand(new StateCommand(controller, data.getName(), data.isOn()));
    }

    public void onApplyBrightnessButtonClick() {
        addCommand(new BrightnessCommand(controller, data.getName(), data.getIntensity()));
    }

    public void onApplyDimmButtonClick() {
        addCommand(new DimCommand(controller, data.getName(), data.getIntensityStep()));
    }

    public void onApplyColorButtonClick() {
        addCommand(new ColorCommand(controller, data.getName(), data.getColor()));
    }

    public void onApplyFlashButtonClick(){
        addCommand(new FlashCommand(controller, data.getName(), data.getTime()));
    }

    public void onApplyTimeButtonClick(){
        addCommand(new TimeCommand(controller, data.getName(), data.getTime()));
    }

    public void onApplySOSButtonClick(){
        addCommand(new SOSCommand(controller, data.getName(), data.getTime()));
    }

    public void onApplyRainbowButtonClick(){
        addCommand(new RainbowCommand(controller, data.getName(), data.getTime()));
    }

    public void onApplyPartyButtonClick() {
        data.getColorList().forEach(e -> System.out.println(e.toString()));
        addCommand(new PartyCommand(controller, data.getName(), data.getColorList(), data.getTime()));
    }

    public void onApplyLampButtonClick(){
        addCommand(new LampCommand(controller, data.getName(), data.isOn(), (float) data.getIntensity(), data.getColor()));
    }

    public void onApplyDriveButtonClick(){
        System.out.println("drive");
//        addCommand(new DriveCommand(controller, data.getName(), channel));
    }

    public void onApplyRaceButtonClick(){
        System.out.println("race");
//        addCommand(new RaceCommand(controller, data.getName(), data.getChannel1(), data.getChannel2(), data.getColor(), data.getColor2(), data.getTimeRange());
    }

    public void onApplyUndoButtonClick() {
        addCommand(new UndoCommand(controller, data.getName(), -1, commandProcessor));
        System.out.println(commandProcessor.getUndoList().toString());
    }

    public void onRunUndoButtonClick() {
    }

    public void onRunResetButtonClick() {
        resetCommands();
    }


    public void doNothing(){

    }

    public void viewCommands() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .resizable(false)
                .build();
        System.out.println("test");
        PrimeFaces.current().dialog().openDynamic("src/main/webapp/RemoteControl/commandView.xhtml", options, null);
    }


}

