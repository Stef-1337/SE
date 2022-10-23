package de.ostfalia.s1.lamp;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
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
    Requester r = new Requester();

    public static void main(String[] args) throws Exception {
        Java2NodeRedLampAdapter j = new Java2NodeRedLampAdapter();
        j.getRequest();
    }

    private static JsonObject jsonFromString(String jsonObjectStr) {
        JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        return object;
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
    public Color getColor() throws IOException {
        return lampe.getColor();
    }

    @Override
    public void setColor(Color color) throws IOException {
        lampe.setColor(color);
    }

    @Override
    public float getIntensity() throws IOException {
        return lampe.getIntensity();
    }

    @Override
    public void setIntensity(float intensity) throws IOException {
        lampe.setIntensity(intensity);
    }

    @Override
    public boolean getState() throws IOException {
        return lampe.getState();
    }

    public void putRequest() throws Exception {
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(new RequestObject(lampe.getState(), lampe.getIntensity(),
                getRGBtoXY(lampe.getColor()), lampe.getName()));
        r.setLampState(3, result);
    }

    public void getRequest() throws Exception {
        JsonObject state = r.getState(new URL(r.base));
        JsonObject s1 = state.getJsonObject("state");
        JsonObject s2 = jsonFromString("{" + state.toString().split("\"type\":\"Extended color light\",")[1].split(",")[0] + "}");

        System.out.println(state.toString());
        System.out.println(s1.toString());
        System.out.println(s2.toString());
        lampe.setState(s1.getBoolean("on"));
        lampe.setIntensity(s1.getInt("bri"));
        lampe.setName(s2.getString("name"));

        lampe.setColor(getXYtoRGB(stringToList("\"xy\":[0.3015,0.3057]")));
        System.out.println(lampe.getColor().toString());

        lampe.setColor(getXYtoRGB(stringToList(getXyString(s1))));
        System.out.println(stringToList(getXyString(s1)));
        System.out.println(lampe.getColor().toString());
//        System.out.println(s1.getString("xy"));

//        System.out.println(stringToList(s2.getString("xy")).toString());
//        lampe.setColor(getXYtoRGB(stringToList(s1.getString("xy"))));
    }

    public List<Float> stringToList(String xy) {
        xy = xy.substring(6, xy.length() - 1);
        Stream<String> stringStream = Arrays.stream(xy.split(","));
        List<Float> doubleList = new ArrayList<>(2);
        stringStream.forEach(e -> doubleList.add(Float.valueOf(e)));
        return doubleList;
    }

    public String getXyString (JsonObject jsonObject){
        Stream<String> stringStream = Arrays.stream(jsonObject.toString().split(","));
        List<String> strings = stringStream.filter(e -> e.contains("[") || e.contains("]")).toList();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (String s:
             strings) {
            stringBuilder.append(s);
            if (i == 0){
                stringBuilder.append(',');
                i++;
            }
        }
        return stringBuilder.toString();
    }

    public Color getXYtoRGB(List<Float> list) throws IOException {
        float x = list.get(0);
        float y = list.get(1);
        float z = (1.0f - x - y);

//        float z = (float) (1.0f - x - y);
//        float Y = lampe.getIntensity();
//        float X = (float) ((Y / y) * x);
//        float Z = (float) ((Y / y) * z);
//
        float r = 3.240479f * x - 1.53715f * y - 0.498535f * z;
        float g = -0.969256f * x + 1.875991f * y + 0.041556f * z;
        float b = 0.055648f * x - 0.204043f * y + 1.057311f * z;

        if ( r > 0.0031308 )
            r = 1.055f * ( (float)Math.pow(r, 0.4166f) ) - 0.055f;
        else
            r = 12.92f * r;

        if ( g > 0.0031308 )
            g = 1.055f * ( (float)Math.pow(g, 0.4166f) ) - 0.055f;
        else
            g = 12.92f * g;

        if ( b > 0.0031308 )
            b = 1.055f * ( (float)Math.pow(b, 0.4166f) ) - 0.055f;
        else
            b = 12.92f * b;

        System.out.println(r);
        System.out.println(g);
        System.out.println(b);
        return new Color((int) r * 255, (int) g * 255, (int) b * 255);

    }
}
