package de.ostfalia.s1.lamp;

public class HueColor {
    private String name;
    private String hex;
    private int rgbR;
    private int rgbG;
    private int rgbB;
    private float x;
    private float y;
    private Colors kategorie;

    public HueColor(String name, String hex, int rgbR, int rgbG, int rgbB, float x, float y, Colors kategorie) {
        this.name = name;
        this.hex = hex;
        this.rgbR = rgbR;
        this.rgbG = rgbG;
        this.rgbB = rgbB;
        this.x = x;
        this.y = y;
        this.kategorie = kategorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public int getRgbR() {
        return rgbR;
    }

    public void setRgbR(int rgbR) {
        this.rgbR = rgbR;
    }

    public int getRgbG() {
        return rgbG;
    }

    public void setRgbG(int rgbG) {
        this.rgbG = rgbG;
    }

    public int getRgbB() {
        return rgbB;
    }

    public void setRgbB(int rgbB) {
        this.rgbB = rgbB;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Colors getKategorie() {
        return kategorie;
    }

    public void setKategorie(Colors kategorie) {
        this.kategorie = kategorie;
    }

    public String toString() {
        return name + " " + hex + " " + rgbR + " " + rgbG + " " + rgbB + " " + x + " " + y + " " + kategorie;
    }

}
