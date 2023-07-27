package model;

import org.json.JSONObject;

// this is the Ninja class that represents an enemy
public class Ninja {

    public static final int DX = 1;

    // add fields to represent changing properties of Ninja
    private int xcoor;
    private int ycoor;
    private int dir;
    private int leftBound;
    private int rightBound;

    // EFFECTS: constructs Ninja with initial properties
    public Ninja(int x, int y, int dir, int leftBound, int rightBound) {
        this.xcoor = x;
        this.ycoor = y;
        this.dir = dir;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    // MODIFIES: this
    // EFFECTS: updates the ninja's x coordinate. If it runs it's left or right bound, the direction switches
    public void move() {
        if (xcoor == leftBound || xcoor == rightBound) {
            dir *= -1;
        }
        xcoor = xcoor + dir * DX;
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

    // EFFECTS: retrieves Ninja's direction
    public int getDir() {
        return this.dir;
    }

    // EFFECTS: retrieves Ninja's leftBound
    public int getLeftBound() {
        return this.leftBound;
    }

    // EFFECTS: retrieves Ninja's rightBound
    public int getRightBound() {
        return this.rightBound;
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("xcoor", xcoor);
        json.put("ycoor", ycoor);
        json.put("dir", dir);
        json.put("leftBound", leftBound);
        json.put("rightBound", rightBound);
        return json;
    }
}
