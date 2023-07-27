package model;

import org.json.JSONObject;

// this is the Batman class that represents the player
public class Batman {

    public static final int DX = 1;
    public static final int DY = 1;

    // add fields to represent changing properties of Batman
    private int xcoor;
    private int ycoor;

    private boolean onGround;
    private boolean inAir;

    private boolean atRightMost;

    private boolean onRoof;

    private boolean facingRight;

    private boolean punching;

    // EFFECTS: constructs Batman with initial properties
    public Batman() {
        this.xcoor = 1;
        this.ycoor = 1;
        this.onGround = false;
        this.inAir = true;
        this.onRoof = false;
        this.atRightMost = false;
        this.facingRight = true;
        this.punching = false;
    }

    // MODIFIES: this
    // EFFECTS: if batman is punching and is facing a ninja while being one space away from them, returns true.
    //          Otherwise, false.
    public boolean hasPunched(Ninja ninja) {
        if (punching) {
            if (facingRight) {
                if (xcoor + 1 == ninja.getXcoor() && ycoor == ninja.getYcoor()) {
                    return true;
                }
            } else {
                if (xcoor - 1 == ninja.getXcoor() && ycoor == ninja.getYcoor()) {
                    return true;
                }
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: makes Batman punch
    public void punch() {
        this.punching = true;
    }

    // MODIFIES: this
    // EFFECTS: sets atRightMost to true. Method is called when Batman is at the right most X coordinate of the screen
    public void putAtRightEdge() {
        this.atRightMost = true;
    }

    // MODIFIES: this
    // EFFECTS: sets Batman on the ground
    public void putOnGround() {
        this.onGround = true;
        this.inAir = false;
        this.onRoof = false;
    }

    // MODIFIES: this
    // EFFECTS: sets Batman as falling
    public void fall() {
        this.onRoof = false;
        this.inAir = true;
        this.onGround = false;
    }

    // MODIFIES: this
    // EFFECTS: sets Batman on a roof
    public void putOnRoof() {
        this.onRoof = true;
        this.inAir = false;
        this.onGround = false;
    }

    // MODIFIES: this
    // EFFECTS: if Batman is in the air, then gravity makes him fall
    public void gravity() {
        if (inAir && !onGround && !onRoof) {
            this.ycoor = ycoor + DY;
        }
    }

    // MODIFIES: this
    // EFFECTS: if Batman is not on the right most edge of the screen, then he moves right
    public void moveRight() {
        if (!atRightMost) {
            this.xcoor = xcoor + DX;
            this.facingRight = true;
            this.punching = false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if Batman is not on the left most edge of the screen, then he moves left
    public void moveLeft() {
        if (xcoor != 0) {
            this.atRightMost = false;
            this.xcoor = xcoor - DX;
            this.facingRight = false;
            this.punching = false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if Batman is not in the air, then he moves up. Ensure he cannot move up consecutively
    public void moveUp() {
        if (!inAir) {
            this.inAir = true;
            this.onGround = false;
            this.onRoof = false;
            this.ycoor = ycoor - 7 * DY;
            this.punching = false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if Batman is on the roof, then he can move down
    public void moveDown() {
        if (!onGround && !inAir) {
            this.ycoor = ycoor + DY;
            this.punching = false;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets Batman's X coordinate
    public void setXcoor(int x) {
        this.xcoor = x;
    }

    // MODIFIES: this
    // EFFECTS: sets Batman's Y coordinate
    public void setYcoor(int y) {
        this.ycoor = y;
    }

    // EFFECTS: make Batman face right
    public boolean faceRight() {
        return this.facingRight = true;
    }

    // EFFECTS: shows if Batman is punching
    public boolean isPunching() {
        return this.punching;
    }

    // EFFECTS: shows Batman's X coordinate
    public int getXcoor() {
        return this.xcoor;
    }

    // EFFECTS: shows Batman's Y coordinate
    public int getYcoor() {
        return this.ycoor;
    }

    // EFFECTS: shows if Batman is on the ground
    public boolean isOnGround() {
        return this.onGround;
    }

    // EFFECTS: shows if Batman is on the roof
    public boolean isOnRoof() {
        return this.onRoof;
    }

    // EFFECTS: shows if Batman is in the air
    public boolean isInAir() {
        return this.inAir;
    }

    // EFFECTS: shows if Batman is on the right most edge of the sreen
    public boolean isAtRightMost() {
        return this.atRightMost;
    }

    // EFFECTS: shows if Batman is facing right
    public boolean isFacingRight() {
        return this.facingRight;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("xcoor", xcoor);
        json.put("ycoor", ycoor);
        json.put("onGround", onGround);
        json.put("inAir", inAir);
        json.put("atRightMost", atRightMost);
        json.put("onRoof", onRoof);
        json.put("facingRight", facingRight);
        json.put("punching", punching);
        return json;
    }

}
