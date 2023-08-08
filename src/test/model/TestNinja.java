package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestNinja {
    private Ninja n1;
    private Ninja n2;
    private Batarang batarang = new Batarang(21, 21, true);
    private Batman batman = new Batman();

    @BeforeEach
    void runBefore() {
        n1 = new Ninja(1, 7, 1, 0,50);
        n2 = new Ninja(4,18, -1,0,50);
    }

    @Test
    void testConstructor() {
        assertEquals(1, n1.getXcoor());
        assertEquals(7, n1.getYcoor());
        assertEquals(1, n1.getDir());
        assertEquals(0, n1.getLeftBound());
        assertEquals(50, n1.getRightBound());

        assertEquals(4, n2.getXcoor());
        assertEquals(18, n2.getYcoor());
        assertEquals(-1, n2.getDir());
        assertEquals(0, n2.getLeftBound());
        assertEquals(50, n2.getRightBound());
    }

    @Test
    void testGetters() {
        n1.setXcoor(9);
        n1.setYcoor(4);
        assertEquals(9, n1.getXcoor());
        assertEquals(4, n1.getYcoor());
        assertEquals(1, n1.getDir());
        assertEquals(0, n1.getLeftBound());
        assertEquals(50, n1.getRightBound());

        n2.setXcoor(20);
        n2.setYcoor(19);
        assertEquals(20, n2.getXcoor());
        assertEquals(19, n2.getYcoor());
        assertEquals(-1, n2.getDir());
        assertEquals(0, n2.getLeftBound());
        assertEquals(50, n2.getRightBound());
    }

    @Test
    void testMove() {
        assertEquals(1, n1.getXcoor());
        n1.move();
        assertEquals(2, n1.getXcoor());

        assertEquals(4, n2.getXcoor());
        n2.move();
        assertEquals(3, n2.getXcoor());
    }

    @Test
    void testMoveSwitch() {
        assertEquals(1, n1.getXcoor());
        n1.setXcoor(50); // at right bound, now switch direction
        n1.move();
        assertEquals(49, n1.getXcoor());

        assertEquals(4, n2.getXcoor());
        n2.setXcoor(0); // at left bound, now switch direction
        n2.move();
        assertEquals(1, n2.getXcoor());
    }

    @Test
    void testCollidedWith() {
        n1.setXcoor(21);
        n1.setYcoor(21);
        assertTrue(n1.collidedWith(batarang));
    }

    @Test
    void testAttackBatman() {
        n1.setXcoor(21);
        n1.setYcoor(21);
        batman.setYcoor(21);
        batman.setYcoor(21);
        assertFalse(n1.attackBatman(batman));
    }
}
