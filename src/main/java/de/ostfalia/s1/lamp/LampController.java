package de.ostfalia.s1.lamp;

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

    int number;


    private Java2NodeRedLampAdapter adapter = new Java2NodeRedLampAdapter();


    public Java2NodeRedLampAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Java2NodeRedLampAdapter adapter) {
        this.adapter = adapter;
    }

    public void switchChanged(ValueChangeEvent e) throws IOException, Exception {
        adapter.getLampe().setState(Boolean.parseBoolean(e.getNewValue().toString()));
    }

    public void nameChanged(ValueChangeEvent e) throws IOException {
        adapter.getLampe().setName((e.getNewValue().toString()));
    }

    public void brightnessChanged(ValueChangeEvent e) throws IOException {
        adapter.getLampe().setIntensity(Float.parseFloat(e.getNewValue().toString()));
    }

    public void colorChanged(ValueChangeEvent e) throws IOException {
//        adapter.getLampe().setTest(e.getNewValue().toString());
//        adapter.getLampe().setColor(e.getNewValue().toString());
    }
    public void increment() {
        number++;
//        try{
//            sendGetRequest();
//        } catch (Exception e){
//
//        }
    }

//    public void sendGetRequest() throws Exception {
//        adapter.getRequest();
//    }
//    public void sendPutRequest() throws Exception {
//        adapter.putRequest();
//    }

}
