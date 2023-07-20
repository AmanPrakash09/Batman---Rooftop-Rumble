package model;

// this is the Ninja class that represents an enemy
public class Ninja {

    // add fields to represent changing properties of Ninja
    private int xcoor;
    private int ycoor;

    // EFFECTS: constructs Ninja with initial properties
    public Ninja(int x, int y) {
        this.xcoor = x;
        this.ycoor = y;
    }

    // MODIFIES: this
    // EFFECTS: sets Ninja's X coordinate
    public void setXcoor(int x) {
        this.xcoor = x;
    }

    // MODIFIES: this
    // EFFECTS: sets Ninja's Y coordinate
    public void setYcoor(int y) {
        this.ycoor = y;
    }

    // EFFECTS: retrieves Ninja's X coordinate
    public int getXcoor() {
        return this.xcoor;
    }

    // EFFECTS: retrieves Ninja's Y coordinate
    public int getYcoor() {
        return this.ycoor;
    }

}
