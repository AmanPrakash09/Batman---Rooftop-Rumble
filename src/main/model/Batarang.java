package model;

import org.json.JSONObject;

import java.awt.*;

// this is the Batarang class that represents a projectile thrown by Batman
public class Batarang {

    public static final int DX = 1;

    // add fields to represent changing properties of Batarang
    private int xcoor;
    private int ycoor;
    private int dir;

    // Constructs a Batarang
    // EFFECTS: Batarang is positioned at coordinates (x, y) and it's direction depends on which way Batman is facing
    public Batarang(int x, int y, Boolean facingRight) {
        this.xcoor = x;
        this.ycoor = y;
        if (facingRight) {
            dir = 1;
        } else {
            dir = -1;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets Batarang's X coordinate
    public void setXcoor(int x) {
        this.xcoor = x;
    }

    // MODIFIES: this
    // EFFECTS: sets Batarang's Y coordinate
    public void setYcoor(int y) {
        this.ycoor = y;
    }

    // EFFECTS: returns Batarang's X coordinate
    public int getXcoor() {
        return this.xcoor;
    }

    // EFFECTS: returns Batarang's Y coordinate
    public int getYcoor() {
        return this.ycoor;
    }

    // EFFECTS: returns Batarang's direction
    public int getDir() {
        return this.dir;
    }

    // MODIFIES: this
    // EFFECTS: updates Batarang's X coordinate as it travels across the screen
    public void move() {
        xcoor = xcoor + dir * DX;
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("xcoor", xcoor);
        json.put("ycoor", ycoor);
        json.put("dir", dir);
        return json;
    }
}
