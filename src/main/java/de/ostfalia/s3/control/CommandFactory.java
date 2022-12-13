package de.ostfalia.s3.control;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.commands.command.ICommand;

import java.lang.reflect.InvocationTargetException;

public class CommandFactory {

    public AbstractLampController controller;

    public CommandFactory(AbstractLampController controller) {
        this.controller = controller;
    }

    public ICommand createCommand(String clazz, CommandParameterData data) {
        ICommand command;

        try {
            String path = "de.ostfalia.s3.control.commands.";
            Class<?> commanadClass = Class.forName(path + clazz + "Command");
            Object commandObject = commanadClass.getConstructor(data.getClass()).newInstance(data);

            command = (ICommand) commandObject;
        } catch (ClassCastException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException ex) {
            System.out.println("Can't build command for " + clazz);
            return null;
        }

        return command;
    }

}
