package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlSpeed;

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

        while (!getThread().isInterrupted()) {
            List<Bicycle> fahrrad = bs.getFahrradDaten(channel, LocalDateTime.now(), LocalDateTime.now().minusMinutes(1), 1);
            KennzahlSpeed speed = null;
            double ms = speed.getAverage(fahrrad);
            float km = (float) (ms / 3600);
            float helligkeit = (km / 50) * 100;
            new BrightnessCommand(controller, "fahrrad", helligkeit).execute(controller);
        }
        });
        runThread(thread);
    }

}
