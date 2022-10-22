package de.ostfalia.s1.lamp;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

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




}
