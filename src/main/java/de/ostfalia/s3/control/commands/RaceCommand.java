package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.HueColor;
import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.kennzahl.strategy.KennzahlDistance;
import de.ostfalia.s3.entity.Datacollector;
import de.ostfalia.s2.fahrrad.control.BicycleService;

import java.time.LocalDateTime;
import java.util.List;

public class RaceCommand extends AbstractCommand {

    private final int bChannel1;
    private final int bChannel2;
    private final HueColor hueColor1;
    private final HueColor hueColor2;
    private final LocalDateTime start;
    private final LocalDateTime end;

    int id;
    int time;

    BicycleService bs = new BicycleService();

    public RaceCommand (AbstractLampController controller, String name, int bChannel1, int bChannel2, HueColor hueColor1, HueColor hueColor2, LocalDateTime start, LocalDateTime end, int id, int time) {
        super(controller, name);
        this.bChannel1 = bChannel1;
        this.bChannel2 = bChannel2;
        this.hueColor1 = hueColor1;
        this.hueColor2 = hueColor2;
        this.start = start;
        this.end = end;
        this.id = id;
        this.time = time;
    }

    @Override
    public List<String> getConfig() {
        return List.of("bicycle "+ bChannel1 + ":" + hueColor1.getName() + " bicyle" + bChannel2 + ":" + hueColor2.getName());
    }

    @Override
    public void execute(AbstractLampController controller) {

        double rotation1 = distance(bChannel1);
        double rotation2 = distance(bChannel2);

        double difference;
        double max;

        if (rotation1 < rotation2) {
            controller.colorChanged(String.valueOf(hueColor2));
            difference = rotation2 - rotation1;
            max = rotation2;
        } else if(rotation1 >= rotation2) {
            controller.colorChanged(String.valueOf(hueColor1));
            difference = rotation1 - rotation2;
            max = rotation1;
        } else {
            //Fehler
            return;
        }

        float brightness;

        if(difference > 0 && max != 0) {
            brightness = (float) (difference / max * 100);
        } else {
            brightness = 1;
        }

        controller.brightnessChanged(brightness);
    }

    public double distance(int channel){
        Datacollector dc = new Datacollector(id, time);
        Datacollector.getInstance(id, time);

        if (channel != id) {
            id = channel;
        }
        List<Bicycle> list = bs.getFahrradDaten(id, LocalDateTime.now(), LocalDateTime.now().minusMinutes(time), 1);
        KennzahlDistance tmp = new KennzahlDistance();
        return tmp.getTotal(list);
    }
}
