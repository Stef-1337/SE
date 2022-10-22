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
import java.util.ArrayList;
import java.util.List;
import java.lang.Object;
import java.awt.color.ColorSpace;


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

    public void putRequest() throws Exception {
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(new RequestObject(lampe.getState(), lampe.getIntensity(),
                getRGBtoXY(lampe.getColor()), lampe.getName()));
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

    }

    public static List<Double> getRGBtoXY(Color c) {
        // For the hue bulb the corners of the triangle are:
        // -Red: 0.675, 0.322
        // -Green: 0.4091, 0.518
        // -Blue: 0.167, 0.04
        double[] normalizedToOne = new double[3];
        float cred, cgreen, cblue;
        cred = c.getRed();
        cgreen = c.getGreen();
        cblue = c.getBlue();
        normalizedToOne[0] = (cred / 255);
        normalizedToOne[1] = (cgreen / 255);
        normalizedToOne[2] = (cblue / 255);
        float red, green, blue;

        // Make red more vivid
        if (normalizedToOne[0] > 0.04045) {
            red = (float) Math.pow(
                    (normalizedToOne[0] + 0.055) / (1.0 + 0.055), 2.4);
        } else {
            red = (float) (normalizedToOne[0] / 12.92);
        }

        // Make green more vivid
        if (normalizedToOne[1] > 0.04045) {
            green = (float) Math.pow((normalizedToOne[1] + 0.055)
                    / (1.0 + 0.055), 2.4);
        } else {
            green = (float) (normalizedToOne[1] / 12.92);
        }

        // Make blue more vivid
        if (normalizedToOne[2] > 0.04045) {
            blue = (float) Math.pow((normalizedToOne[2] + 0.055)
                    / (1.0 + 0.055), 2.4);
        } else {
            blue = (float) (normalizedToOne[2] / 12.92);
        }

        float X = (float) (red * 0.649926 + green * 0.103455 + blue * 0.197109);
        float Y = (float) (red * 0.234327 + green * 0.743075 + blue * 0.022598);
        float Z = (float) (red * 0.0000000 + green * 0.053077 + blue * 1.035763);

        float x = X / (X + Y + Z);
        float y = Y / (X + Y + Z);

        double[] xy = new double[2];
        xy[0] = x;
        xy[1] = y;
        List<Double> xyAsList = new ArrayList<>(2);
        for (Double d:xy) {
            xyAsList.add(d);
        }
        return xyAsList;
    }

    public Color getXYtoRGB(List<Double> list){
        float x = list.get(0);
    }
}
