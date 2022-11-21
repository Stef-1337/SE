package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;

import java.util.ArrayList;
import java.util.List;

public class StateCommand extends AbstractCommand {

    private boolean to;
    List<String> stringList;

    public StateCommand(AbstractLampController controller, String command, boolean state){
        super(controller, command);
        this.to = state;
        stringList = new ArrayList<>();
        stringList.add("abc");
        stringList.add("def");
    }

    @Override
    public void execute(AbstractLampController controller) {
        controller.switchChanged(to);
        System.out.println("state");
        System.out.println(controller.getAdapter().getLampe());
    }

    @Override
    public List<String> getConfig() {
        System.out.println("Moin config");
        List<String> config = new ArrayList<>();
        config.add(String.valueOf(to));
        System.out.println(config.get(1));
        return config;
    }
}
