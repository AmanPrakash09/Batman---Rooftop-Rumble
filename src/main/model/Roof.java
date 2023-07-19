package model;

public class Roof {
    public static final int SPACE = 5;

    private int xcoor;
    private int width;
    private int height;

    public Roof(int startingX, int w, int h) {
        this.xcoor = startingX;
        this.width = w;
        this.height = h - SPACE;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public void setHeight(int h) {
        this.height = h - SPACE;
//        System.out.println(height);
    }

    public int getXcoor() {
        return this.xcoor;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}