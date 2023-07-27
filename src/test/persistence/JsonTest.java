package persistence;

import model.Batarang;
import model.Batman;
import model.Ninja;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkNinjas(int xcoor, int ycoor, int dir, int leftBound, int rightBound, Ninja ninja) {
        assertEquals(xcoor, ninja.getXcoor());
        assertEquals(ycoor, ninja.getYcoor());
        assertEquals(dir, ninja.getDir());
        assertEquals(leftBound, ninja.getLeftBound());
        assertEquals(rightBound, ninja.getRightBound());
    }

    protected void checkBatman(int xcoor, int ycoor, boolean punching, boolean onGround, boolean facingRight, boolean atRightMost, boolean onRoof, boolean inAir, Batman batman) {
        assertEquals(xcoor, batman.getXcoor());
        assertEquals(ycoor, batman.getYcoor());
        assertEquals(punching, batman.isPunching());
        assertEquals(onGround, batman.isOnGround());
        assertEquals(facingRight, batman.isFacingRight());
        assertEquals(atRightMost, batman.isAtRightMost());
        assertEquals(onRoof, batman.isOnRoof());
        assertEquals(inAir, batman.isInAir());
    }

    protected void checkBatarang(int xcoor, int ycoor, int dir, Batarang batarang) {
        assertEquals(xcoor, batarang.getXcoor());
        assertEquals(ycoor, batarang.getYcoor());
        assertEquals(dir, batarang.getDir());
    }
}
