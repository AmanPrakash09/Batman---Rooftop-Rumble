package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBatman {
    private Batman batman;
    private Ninja ninja = new Ninja(10, 10);

    @BeforeEach
    void runBefore() {
        batman = new Batman();
    }

    @Test
    void testConstructor() {
        assertEquals(100, batman.getHealth());
        assertEquals(1, batman.getXcoor());
        assertEquals(1, batman.getYcoor());
        assertFalse(batman.isOnGround());
        assertTrue(batman.isInAir());
        assertFalse(batman.isOnRoof());
        assertFalse(batman.isAtRightMost());
        assertTrue(batman.isFacingRight());
        assertFalse(batman.isPunching());
    }

    @Test
    void testHasPunched() {
        // batman is not punching
        assertFalse(batman.hasPunched(ninja));

        // batman punches, is not facing right, and is nowhere near ninja
        batman.moveLeft();  // faces left
        batman.punch();     // punches
        batman.setXcoor(5);
        batman.setYcoor(5);
        assertFalse(batman.hasPunched(ninja));

        // batman punches, is not facing right, and is not 1 DX space away from Ninja
        batman.moveLeft();  // faces left
        batman.punch();     // punches
        batman.setXcoor(5);
        batman.setYcoor(10);
        assertFalse(batman.hasPunched(ninja));

        // batman punches, is not facing right, and is not on the same y as Ninja
        batman.moveLeft();  // faces left
        batman.punch();     // punches
        batman.setXcoor(11);
        batman.setYcoor(9);
        assertFalse(batman.hasPunched(ninja));

        // batman punches, is not facing right, and is one space to the right of Ninja
        batman.moveLeft();  // faces left
        batman.punch();     // punches
        batman.setXcoor(11);
        batman.setYcoor(10);
        assertTrue(batman.hasPunched(ninja));
    }

    @Test
    void TestGravity() {
        batman.fall();
        batman.gravity();
        assertEquals(2, batman.getYcoor());
    }

    @Test
    void TestMoveRight() {
        batman.moveRight();
        assertEquals(2, batman.getXcoor());
        assertTrue(batman.isFacingRight());
        assertFalse(batman.isPunching());

        batman.putAtRightEdge();
        batman.moveRight();
        assertEquals(2, batman.getXcoor());
        assertTrue(batman.isFacingRight());
        assertFalse(batman.isPunching());
    }

    @Test
    void TestMoveLeft() {
        batman.moveLeft();
        assertEquals(0, batman.getXcoor());
        assertFalse(batman.isFacingRight());
        assertFalse(batman.isPunching());

        batman.moveLeft();
        assertEquals(0, batman.getXcoor());
        assertFalse(batman.isFacingRight());
        assertFalse(batman.isPunching());
    }

    @Test
    void TestMoveUp() {
        batman.fall();
        assertEquals(0, batman.getXcoor());
        assertFalse(batman.isFacingRight());
        assertFalse(batman.isPunching());

        batman.moveLeft();
        assertEquals(0, batman.getXcoor());
        assertFalse(batman.isFacingRight());
        assertFalse(batman.isPunching());
    }
}
