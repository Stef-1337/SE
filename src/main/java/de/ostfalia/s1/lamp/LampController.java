package de.ostfalia.s1.lamp;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class LampController extends AbstractLampController implements Serializable {

    public void switchChanged(ValueChangeEvent e) {
        switchChanged(Boolean.valueOf(e.getNewValue().toString()));
    }

    public void nameChanged(ValueChangeEvent e) {
        nameChanged(e.getNewValue().toString());
    }

    public void brightnessChanged(ValueChangeEvent e) {
        brightnessChanged(Float.parseFloat(e.getNewValue().toString()));
    }

    public void colorChanged(ValueChangeEvent e) {
        colorChanged(e.getNewValue().toString());
    }

}
