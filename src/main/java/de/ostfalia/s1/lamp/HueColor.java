package de.ostfalia.s1.lamp;

public class HueColor {
    private String name;
    private String hex;
    private int rgbR;
    private int rgbG;
    private int rgbB;
    private float x;
    private float y;
    private String kategorie;

    public HueColor(String name, String hex, int rgbR, int rgbG, int rgbB, float x, float y, String kategorie) {
        this.name = name;
        this.hex = hex;
        this.rgbR = rgbR;
        this.rgbG = rgbG;
        this.rgbB = rgbB;
        this.x = x;
        this.y = y;
        this.kategorie = kategorie;
    }
    public String toString(){
        return name + " " + hex + " " + rgbR + " " + rgbG + " " + rgbB + " " + x + " " + y + " " + kategorie;
    }
}
