package model;

public class Ninja {

    private int xcoor;
    private int ycoor;

    public Ninja(int x, int y) {
        this.xcoor = x;
        this.ycoor = y;
    }

    public void setXcoor(int x) {
        this.xcoor = x;
    }

    public void setYcoor(int y) {
        this.ycoor = y;
    }

    public int getXcoor() {
        return this.xcoor;
    }

    public int getYcoor() {
        return this.ycoor;
    }

}
