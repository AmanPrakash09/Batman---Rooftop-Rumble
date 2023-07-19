package model;

public class Batman {

    public static final int DX = 1;
    public static final int DY = 1;

    private int health;

    private int xcoor;
    private int ycoor;

    private boolean onGround;
    private boolean inAir;

    private boolean atRightMost;

    private boolean onRoof;

    private boolean facingRight;

    private boolean punching;

//    private Batarang batarang;

    public Batman() {
        this.health = 100;
        this.xcoor = 1;
        this.ycoor = 1;
        this.onGround = false;
        this.inAir = true;
        this.onRoof = false;
        this.atRightMost = false;
        this.facingRight = true;
        this.punching = false;
    }

//    public void throwBatarang() {
//        batarang = new Batarang(getXcoor(), getYcoor());
//        batarang.setXcoor(getXcoor());
//        batarang.setYcoor(getYcoor());
////        while (batarang.getXcoor() >= 0 && batarang.getXcoor() <= maxX) {
////            batarang.move(batman.isFacingRight());
////        }
//        int target = 0;
//        while (target < 5) {
//            batarang.move(isFacingRight());;
////            delay(100);
//            target++;
//        }
////        batarang.setValid(false);
//    }

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
//        return this.xcoor == ninja.getXcoor() && this.ycoor == ninja.getYcoor();
    }

//    private void delay(int milliseconds) {
//        try {
//            Thread.sleep(milliseconds);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public void punch() {
        this.punching = true;
    }

    public void putAtRightEdge() {
        this.atRightMost = true;
    }

    public void putOnGround() {
        this.onGround = true;
        this.inAir = false;
        this.onRoof = false;
    }

    public void fall() {
        this.onRoof = false;
        this.inAir = true;
        this.onGround = false;
    }

    public void putOnRoof() {
        this.onRoof = true;
        this.inAir = false;
        this.onGround = false;
    }

    public void gravity() {
        if (inAir && !onGround && !onRoof) {
            this.ycoor = ycoor + DY;
        }
    }

    public void moveRight() {
        if (!atRightMost) {
            this.xcoor = xcoor + DX;
            this.facingRight = true;
            this.punching = false;
        }
    }

    public void moveLeft() {
        if (xcoor != 0) {
            this.atRightMost = false;
            this.xcoor = xcoor - DX;
            this.facingRight = false;
            this.punching = false;
        }
    }

    public void moveUp() {
        if (!inAir) {
            this.inAir = true;
            this.onGround = false;
            this.onRoof = false;
            this.ycoor = ycoor - 7 * DY;
            this.punching = false;
        }
    }

    public void moveDown() {
        if (!onGround && !inAir) {
            this.ycoor = ycoor + DY;
            this.punching = false;
        }
//        this.ycoor = ycoor + DY;
//        System.out.println(this.ycoor);
    }

    public void setXcoor(int x) {
        this.xcoor = x;
    }

    public void setYcoor(int y) {
        this.ycoor = y;
    }

    public boolean isPunching() {
        return this.punching;
    }

    public int getHealth() {
        return this.health;
    }

    public int getXcoor() {
        return this.xcoor;
    }

    public int getYcoor() {
        return this.ycoor;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public boolean isOnRoof() {
        return this.onRoof;
    }

    public boolean isFacingRight() {
        return this.facingRight;
    }

//    public Batarang getBatarang() {
//        return this.batarang;
//    }

}
