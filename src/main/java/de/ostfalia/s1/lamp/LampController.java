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
public class LampController implements Serializable {
    private Java2NodeRedLampAdapter adapter = new Java2NodeRedLampAdapter();


    public Java2NodeRedLampAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Java2NodeRedLampAdapter adapter) throws Exception {
        adapter.putRequest();
        this.adapter = adapter;
    }

    public void switchChanged(ValueChangeEvent e) throws IOException, Exception {
        adapter.getLampe().setState(Boolean.parseBoolean(e.getNewValue().toString()));
        adapter.putRequest();
    }

    public void nameChanged(ValueChangeEvent e) throws IOException {
        adapter.getLampe().setName((e.getNewValue().toString()));
    }

    public void brightnessChanged(ValueChangeEvent e) throws Exception {
        adapter.getLampe().setIntensity(Float.parseFloat(e.getNewValue().toString()));
        adapter.putRequest();
    }

    public void colorChanged(ValueChangeEvent e) throws Exception {
        adapter.getLampe().setColor(e.getNewValue().toString());
        adapter.putRequest();
    }

}
