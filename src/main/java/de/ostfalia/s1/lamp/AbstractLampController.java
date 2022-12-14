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

public class AbstractLampController {
    private Java2NodeRedLampAdapter adapter = new Java2NodeRedLampAdapter();

    public Java2NodeRedLampAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Java2NodeRedLampAdapter adapter) {
        adapter.putRequest();
        this.adapter = adapter;
    }

    public void switchChanged(Boolean state) {
        adapter.getLampe().setState(state);
        adapter.putRequest();
    }

    public void nameChanged(String name) {
        adapter.getLampe().setName(name);
    }

    public void brightnessChanged(float brightness) {
        adapter.getLampe().setIntensity(brightness);
        adapter.putRequest();
    }

    public void colorChanged(String color) {
        adapter.getLampe().setColor(color);
        adapter.putRequest();
    }
}
