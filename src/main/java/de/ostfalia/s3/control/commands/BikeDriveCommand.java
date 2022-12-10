package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.kennzahl.Kennzahl;
import de.ostfalia.s3.entity.DataSingleton;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

public class BikeDriveCommand extends AbstractThreadCommand {

    private int channel;

    public BikeDriveCommand(AbstractLampController controller, String name, int channel) {
        super(controller, name);

        this.channel = channel;
    }

    private int start = 0;

    @Override
    public void execute(AbstractLampController controller) {
        new StateCommand(controller, "On", true).execute(controller);

        runThread(new Thread(() -> {
            while (!getThread().isInterrupted()) {
                try {
                    DataSingleton data = DataSingleton.getInstance();
                    List<Bicycle> result = data.getData(channel);

                    if (result.isEmpty()) {
                        new BrightnessCommand(controller, "Low", 1f).execute(controller);
                        System.out.println("No data");
                    } else {
                        Bicycle current = result.get(start++);

                        int frequency = current.getRotations_per_second() / 4;
                        double kmh = frequency * 2.111 * 3.6;
                        double brightness = kmh * 2.0;
                        if(brightness > 100) brightness = 100;

                        System.out.println("Brightness: " + brightness);
                        new BrightnessCommand(controller, "BikeDrive", (float) brightness).execute(controller);

                        if (start == 61) start = 0;
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
