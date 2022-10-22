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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.pow;


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
        requester.setLampState(3, result);
    }

    public static void main(String[] args) throws Exception {
        Java2NodeRedLampAdapter j = new Java2NodeRedLampAdapter();
        j.getRequest();
    }

    public void getRequest() throws Exception {
        JsonObject s = requester.getState(new URL(requester.base)).getJsonObject("state");
        System.out.println(s.toString());
//        lampe.setState(s.getBoolean("on"));
//        lampe.setIntensity(s.getInt("bri"));
//        lampe.setColor(getXYtoRGB(stringToList(s.getString("xy"))));
//        lampe.setName(s.getString("name"));
    }

    public List<Double> stringToList(String xy){
        xy = xy.substring(1, xy.length() - 2);
        Stream<String> stringStream = Arrays.stream(xy.split(","));
        List<Double> doubleList = new ArrayList<>(2);
        stringStream.forEach(e  -> doubleList.add(Double.valueOf(e)));
        return doubleList;
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
            red = (float) pow(
                    (normalizedToOne[0] + 0.055) / (1.0 + 0.055), 2.4);
        } else {
            red = (float) (normalizedToOne[0] / 12.92);
        }

        // Make green more vivid
        if (normalizedToOne[1] > 0.04045) {
            green = (float) pow((normalizedToOne[1] + 0.055)
                    / (1.0 + 0.055), 2.4);
        } else {
            green = (float) (normalizedToOne[1] / 12.92);
        }

        // Make blue more vivid
        if (normalizedToOne[2] > 0.04045) {
            blue = (float) pow((normalizedToOne[2] + 0.055)
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
        for (Double d : xy) {
            xyAsList.add(d);
        }
        return xyAsList;
    }

    public Color getXYtoRGB(List<Double> list) throws IOException {
        double x = list.get(0);
        double y = list.get(1);
        float z = (float) (1.0f - x - y);
        float Y = lampe.getIntensity();
        float X = (float) ((Y / y) * x);
        float Z = (float) ((Y / y) * z);
        float r = X * 1.4628067f - Y * 0.1840623f - Z * 0.2743606f;
        float g = -X * 0.5217933f + Y * 1.4472381f + Z * 0.0677227f;
        float b = X * 0.0349342f - Y * 0.0968930f + Z * 1.2884099f;
        r = r <= 0.0031308f ? 12.92f * r : (float) ((1.0f + 0.055f) * pow(r, (1.0f / 2.4f)) - 0.055f);

        g = g <= 0.0031308f ? 12.92f * g : (float) ((1.0f + 0.055f) * pow(g, (1.0f / 2.4f)) - 0.055f);

        b = b <= 0.0031308f ? 12.92f * b : (float) ((1.0f + 0.055f) * pow(b, (1.0f / 2.4f)) - 0.055f);

        return new Color(r, g, b);

    }
}
