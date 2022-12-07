package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlSpeed;
import de.ostfalia.s3.entity.Datacollector;

import javax.ejb.Schedule;
import java.time.LocalDateTime;
import java.util.List;

public class DriveCommand extends AbstractThreadCommand{
    int channel;
    BicycleService bs;
    public DriveCommand(AbstractLampController controller, String name, int channel) {
        super(controller, name);
        this.channel = channel;
    }

    @Override
    public List<String> getConfig() {
        return List.of("Drive: Geschwindigkeit von " + channel );
    }

    @Override
    public void execute(AbstractLampController controller) {

        Thread thread = new Thread(() -> {
            try {
                Datacollector bicycle = Datacollector.getInstance(channel, 1);
                while (!getThread().isInterrupted()) {
                    double ms = bicycle.speed();
                    Thread.sleep(60000);
                    float km = (float) (ms / 3600);
                    float helligkeit = (km / 50) * 100;
                    new BrightnessCommand(controller, "fahrrad", helligkeit).execute(controller);
                }
            }
        catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        runThread(thread);
    }

}
