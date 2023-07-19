package model;

import java.awt.*;

public class Batarang {

    public static final int DX = 1;

    private int xcoor;
    private int ycoor;

    // Constructs a Batarang
    // effects: Batarang is positioned at coordinates (x, y)
    public Batarang(int x, int y) {
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

    // Updates the Batarang on clock tick
    // modifies: this
    // effects: Batarang is moved DX units depending on which way Batman is facing
    public void move(Boolean facingRight) {
        if (facingRight) {
            this.xcoor = xcoor + DX;
            delay(100);
        } else {
            this.xcoor = xcoor - DX;
            delay(100);
        }
    }

    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
