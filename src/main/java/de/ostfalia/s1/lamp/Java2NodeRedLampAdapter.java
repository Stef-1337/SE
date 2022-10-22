package de.ostfalia.s1.lamp;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;


@Named
@SessionScoped
public class Java2NodeRedLampAdapter implements ILamp, Serializable {

    Lamp lampe = new Lamp();
    Requester requester = new Requester();

    @Override
    public void switchOn() throws IOException {
        lampe.switchOn();
    }

    @Override
    public void switchOn(float intensity) throws IOException {
        lampe.switchOn();
    }

    @Override
    public void switchOn(Color color) throws IOException {
        lampe.switchOn();
    }

    @Override
    public void switchOff() throws IOException {
        lampe.switchOn();
    }

    @Override
    public void setColor(Color color) throws IOException {
        lampe.setColor(color);
    }

    @Override
    public void setIntensity(float intensity) throws IOException {
        lampe.setIntensity(intensity);
    }

    @Override
    public Color getColor() throws IOException {
        return lampe.getColor();
    }

    @Override
    public float getIntensity() throws IOException {
        return lampe.getIntensity();
    }

    @Override
    public boolean getState() throws IOException {
        return lampe.getState();
    }

    public void putRequest() throws IOException {
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(lampe);
        requester.setLampState(4, result);
    }

    public void getRequest() throws Exception{
            JsonObject s = requester.getState(new URL(requester.base)).getJsonObject("state");
            lampe.setState(s.getBoolean("on"));
            lampe.setIntensity(s.getInt("bri"));

    }

    public static void main(String[] args) throws IOException {
        Requester bridge = new Requester();
        Lamp lamp = new Lamp(false, 154, 7778, 254, new double[]{0.5330,0.4273}, "Stehlampe rechts");
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(lamp);
        System.out.println(result);
        bridge.setLampState(4, result);
        JsonObject state = bridge.getState(new URL(base));
        JsonObject s = state.getJsonObject("state"); //"entpacken"
        int bri = s.getInt("bri"); // einzelne Parameter aus der Json auslesen (Anstatt die String/Substring Variante)
        System.out.println(bri);
        String feedback = state.toString();
        System.out.println(feedback);
        feedback = feedback.substring(9, feedback.length());
        String[] feedbackArray = feedback.split(",");
        feedback = feedbackArray[0] + ", " + feedbackArray[1] + ", " + feedbackArray[2] + ", " + feedbackArray[3] +
                ", " + feedbackArray[5].substring(0,12) + ", " + feedbackArray[6] + ", " + feedbackArray[15] + "}";
        System.out.println(feedback);

//        JsonString feedback = state.getJsonString("sat");
//        System.out.println(feedback);

        lamp =  jsonb.fromJson(feedback, Lamp.class); // Damit können wir später den Status aus derLampe auslesen. Müssen wir aber noch anpassen
        System.out.println(lamp.name);
    }
}
