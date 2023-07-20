package model;

// this is the Roof class that represents a surface batman can go down
public class Roof {
    public static final int SPACE = 5;

    // add fields to represent changing properties of Roof
    private int xcoor;
    private int width;
    private int height;

    // EFFECTS: constructs Roof with initial properties
    public Roof(int startingX, int w, int h) {
        this.xcoor = startingX;
        this.width = w;
        this.height = h - SPACE;
    }

    // MODIFIES: this
    // EFFECTS: sets the width to a desired value
    public void setWidth(int w) {
        this.width = w;
    }

    // MODIFIES: this
    // EFFECTS: sets the height to a desired value. SPACE accounts for gap between surfaces.
    public void setHeight(int h) {
        this.height = h - SPACE;
    }

    // EFFECTS: retrieves the X coordinate where the roof begins
    public int getXcoor() {
        return this.xcoor;
    }

    // EFFECTS: retrieves the width
    public int getWidth() {
        return this.width;
    }

    // EFFECTS: retrieves the height
    public int getHeight() {
        return this.height;
    }
}