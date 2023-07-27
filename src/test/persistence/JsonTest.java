package persistence;

import model.Batarang;
import model.Batman;
import model.Ninja;

import static org.junit.jupiter.api.Assertions.assertEquals;

// CITATION: framework of code was taken from JsonSerializationDemo
// represents class of helper methods used by JsonReader and JsonWriter
public class JsonTest {
    // EFFECTS: checks Ninja's properties
    protected void checkNinjas(int xcoor, int ycoor, int dir, int leftBound, int rightBound, Ninja ninja) {
        assertEquals(xcoor, ninja.getXcoor());
        assertEquals(ycoor, ninja.getYcoor());
        assertEquals(dir, ninja.getDir());
        assertEquals(leftBound, ninja.getLeftBound());
        assertEquals(rightBound, ninja.getRightBound());
    }

    // EFFECTS: checks Batman's properties
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

    // EFFECTS: checks Batarang's properties
    protected void checkBatarang(int xcoor, int ycoor, int dir, Batarang batarang) {
        assertEquals(xcoor, batarang.getXcoor());
        assertEquals(ycoor, batarang.getYcoor());
        assertEquals(dir, batarang.getDir());
    }
}
