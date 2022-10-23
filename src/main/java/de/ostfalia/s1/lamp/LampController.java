package de.ostfalia.s1.lamp;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.awt.*;
import java.io.IOException;

@Named
@RequestScoped
public class LampController {

    private Java2NodeRedLampAdapter adapter = new Java2NodeRedLampAdapter();

    public Java2NodeRedLampAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Java2NodeRedLampAdapter adapter) {
        this.adapter = adapter;
    }

    public void switchChanged(ValueChangeEvent e) throws IOException {
        adapter.getLampe().setState(Boolean.valueOf(e.getNewValue().toString()));
    }

    public void nameChanged(ValueChangeEvent e) throws IOException {
        adapter.getLampe().setName((e.getNewValue().toString()));
    }

    public void brightnessChanged(ValueChangeEvent e) throws IOException {
        adapter.getLampe().setIntensity(Float.valueOf(e.getNewValue().toString()));
    }

    public void setColor(ValueChangeEvent e) throws IOException {
        adapter.getLampe().setTest(e.getNewValue().toString());
    }

}
