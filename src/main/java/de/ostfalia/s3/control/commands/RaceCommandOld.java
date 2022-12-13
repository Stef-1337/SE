package de.ostfalia.s3.control.commands;

import de.ostfalia.s1.lamp.AbstractLampController;
import de.ostfalia.s1.lamp.HueColor;
import de.ostfalia.s3.control.commands.command.AbstractCommand;

import java.time.LocalDateTime;
import java.util.List;

public class RaceCommandOld extends AbstractCommand {

    private int bChannel1;
    private int bChannel2;
    private HueColor hueColor1;
    private HueColor hueColor2;
    private LocalDateTime start;
    private LocalDateTime end;

    public RaceCommandOld(AbstractLampController controller, String name, int bChannel1, int bChannel2, HueColor hueColor1, HueColor hueColor2, LocalDateTime start, LocalDateTime end) {
        super(controller, name);
        this.bChannel1 = bChannel1;
        this.bChannel2 = bChannel2;
        this.hueColor1 = hueColor1;
        this.hueColor2 = hueColor2;
        this.start = start;
        this.end = end;
    }

    @Override
    public List<String> getConfig() {
        return List.of("bicycle "+ bChannel1 + ":" + hueColor1.getName() + " bicyle" + bChannel2 + ":" + hueColor2.getName());
    }

    @Override
    public void execute(AbstractLampController controller) {
        double dummyRotation1 = 50000;
        double dummyRotation2 = 100000;

        double rotation1 = dummyRotation1; //Dummies ersetzen durch Distanz bicycle1
        double rotation2 = dummyRotation2; //Dummies ersetzen durch Distanz bicycle2

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
}
