package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.ILamp;
import de.ostfalia.s1.lamp.Java2NodeRedLampAdapter;
import de.ostfalia.s1.lamp.Lamp;
import lombok.Getter;

public abstract class AbstractCommand implements ICommand, Cloneable {

    @Getter
    private String name;
    @Getter
    private AbstractLampController controller;
    @Getter
    private Lamp status;

    public AbstractCommand(AbstractLampController controller, String name) {
        this.name = name;
        this.controller = controller;
    }

    private void setStatus() {
        this.status = controller.getAdapter().fetchCurrentLampStatus();
    }

    public AbstractCommand executeAndClone() {
        try {
            AbstractCommand command = (AbstractCommand) clone();
            command.setStatus();
            command.execute(controller);
            return command;
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void undo(){
        undo(controller);

        Java2NodeRedLampAdapter lamp = controller.getAdapter();
        lamp.setLampe(status);
        lamp.putRequest();
    }

    public void undo(AbstractLampController controller){

    }

}
