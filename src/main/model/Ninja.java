package model;

import org.json.JSONObject;

import java.awt.*;

// this is the Ninja class that represents an enemy
public class Ninja {

    public static final int DX = 1;

    // add fields to represent changing properties of Ninja
    public static final int SIZE_X = 7;
    public static final int SIZE_Y = 7;
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

    // Determines if this invader has collided with a missile
    // modifies: none
    // effects:  returns true if this invader has collided with missile m,
    //           false otherwise
    public boolean collidedWith(Batarang b) {
        Rectangle invaderBoundingRect = new Rectangle(getXcoor() - SIZE_X / 2, getYcoor() - SIZE_Y / 2, SIZE_X, SIZE_Y);
        Rectangle missileBoundingRect = new Rectangle(b.getXcoor() - Batarang.SIZE_X / 2,
                b.getYcoor() - Batarang.SIZE_Y / 2, Batarang.SIZE_X, Batarang.SIZE_Y);
        return invaderBoundingRect.intersects(missileBoundingRect);
    }

    // Determines if this invader has collided with a missile
    // modifies: none
    // effects:  returns true if this invader has collided with missile m,
    //           false otherwise
    public boolean attackBatman(Batman b) {
        Rectangle invaderBoundingRect = new Rectangle(getXcoor() - SIZE_X / 2, getYcoor() - SIZE_Y / 2, SIZE_X, SIZE_Y);
        Rectangle missileBoundingRect = new Rectangle(b.getXcoor() - Batman.SIZE_X / 2,
                b.getYcoor() - Batman.SIZE_Y / 2, Batman.SIZE_X, Batman.SIZE_Y);
        return invaderBoundingRect.intersects(missileBoundingRect);
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
