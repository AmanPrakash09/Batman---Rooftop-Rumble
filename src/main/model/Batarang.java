package model;

import org.json.JSONObject;

import java.awt.*;

public class Batarang {

    public static final int DX = 1;

    private int xcoor;
    private int ycoor;
    private int dir;

    // Constructs a Batarang
    // effects: Batarang is positioned at coordinates (x, y)
    public Batarang(int x, int y, Boolean facingRight) {
        this.xcoor = x;
        this.ycoor = y;
        if (facingRight) {
            dir = 1;
        } else {
            dir = -1;
        }
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

    public int getDir() {
        return this.dir;
    }


    // MODIFIES: this
    // Effects: Batarang is moved DX units depending on which way Batman is facing
//    public void move(Boolean facingRight) {
//        if (facingRight) {
//            moveHelper(1);
//        } else {
//            moveHelper(-1);
//        }
//        System.out.println("yo I was called");
//    }

    public void move() {
        xcoor = xcoor + dir * DX;
    }

//    private void delay(int milliseconds) {
//        try {
//            Thread.sleep(milliseconds);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("xcoor", xcoor);
        json.put("ycoor", ycoor);
        json.put("dir", dir);
        return json;
    }
}
