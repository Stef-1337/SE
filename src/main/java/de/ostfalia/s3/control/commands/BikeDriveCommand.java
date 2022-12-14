package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s3.control.CommandParameterData;
import de.ostfalia.s3.control.commands.command.AbstractThreadCommand;
import de.ostfalia.s3.entity.DataSingleton;

import java.util.List;

public class BikeDriveCommand extends AbstractThreadCommand {

    private int channel;

    public BikeDriveCommand(CommandParameterData data) {
        this(data.getController(), data.getName(), data.getChannel1());
    }

    public BikeDriveCommand(AbstractLampController controller, String name, int channel) {
        super(controller, name);

        this.channel = channel;
    }

    @Override
    public void execute(AbstractLampController controller) {
        new StateCommand(controller, "On", true).execute(controller);
        DataSingleton data = DataSingleton.getInstance();

        runThread(new Thread(() -> {
            while (!getThread().isInterrupted()) {
                try {
                    List<Bicycle> result = data.getData(channel);

                    if (result.isEmpty()) {
                        new BrightnessCommand(controller, "Low", 1f).execute(controller);
                        System.out.println("No data");
                    } else {
                        //TODO nicht mehr start benutzen wenn die db wieder geht
                        System.out.println(data.getIndex() + ", " + result.size());

                        Bicycle current = result.get(Math.min(data.getIndex(), result.size() - 1));

                        int frequency = current.getRotations_per_second() / 4;
                        double kmh = frequency * 2.111 * 3.6;
                        double brightness = kmh * 2.0;
                        if (brightness > 100) brightness = 100;

                        System.out.println("Brightness: " + brightness);
                        new BrightnessCommand(controller, "BikeDrive", (float) brightness).execute(controller);
                    }

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    @Override
    public List<String> getConfig() {
        return List.of("Channel: " + channel);
    }
}
