package model;

public class Ground {

    private int width;
    private int height;

    public Ground() {
        this.width = 1;
        this.height = 1;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public void setHeight(int h) {
        this.height = h;
//        System.out.println(height);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
