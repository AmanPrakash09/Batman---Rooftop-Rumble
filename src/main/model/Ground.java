package model;

// this is the Ground class that represents the ground
public class Ground {

    // add fields to represent changing properties of Ground
    private int width;
    private int height;

    // EFFECTS: constructs Ground with initial properties
    public Ground() {
        this.width = 1;
        this.height = 1;
    }

    // MODIFIES: this
    // EFFECTS: sets the width to a desired value
    public void setWidth(int w) {
        this.width = w;
    }

    // MODIFIES: this
    // EFFECTS: sets the height to a desired value
    public void setHeight(int h) {
        this.height = h;
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
