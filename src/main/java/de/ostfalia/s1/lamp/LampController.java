package de.ostfalia.s1.lamp;

import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;

@Named
@ViewScoped
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






}
