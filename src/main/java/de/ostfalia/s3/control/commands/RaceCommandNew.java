package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.HueColor;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s3.entity.DataSingleton;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RaceCommandNew extends AbstractThreadCommand {

    private int channel1, channel2;
    private HueColor color1, color2;

    public RaceCommandNew(AbstractLampController controller, String name, int channel1, int channel2, HueColor color1, HueColor color2) {
        super(controller, name);

        this.channel1 = channel1;
        this.channel2 = channel2;

        this.color1 = color1;
        this.color2 = color2;
    }

    @Override
    public void execute(AbstractLampController controller) {
        DataSingleton data = DataSingleton.getInstance();

        new LampCommand(controller, "On", true, 100f, color1).execute(controller);
        runThread(new Thread(() -> {
            while (!getThread().isInterrupted()) {
                try {
                    double total1 = 0, total2 = 0;

                    for (Bicycle bike : data.getData(channel1))
                        total1 += getDistance(bike.getRotations_per_second());

                    for (Bicycle bike : data.getData(channel2))
                        total2 += getDistance(bike.getRotations_per_second());

                    System.out.println("1: " + total1 + ", 2:" + total2);

                    new ColorCommand(controller, "RaceColor", (total1 > total2 ? color1 : color2)).execute(controller);

                    Thread.sleep(TimeUnit.SECONDS.toMillis(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    private double getDistance(int rotations) {
        return (rotations / 4.0) * 2.111;
    }

    @Override
    public List<String> getConfig() {
        return List.of("Channel1: " + channel1 + ", " + color1.getName(), "Channel2: " + channel2 + ", " + color2.getName());
    }
}
