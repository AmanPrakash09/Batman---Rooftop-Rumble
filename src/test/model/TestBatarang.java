package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBatarang {
    private Batarang b1;
    private Batarang b2;

    @BeforeEach
    void testConstructor() {
        b1 = new Batarang(1, 1, true);
        b2 = new Batarang(10, 1, false);
    }

    @Test
    void testSetters() {
        b1.setXcoor(5);
        b1.setYcoor(7);
        assertEquals(5, b1.getXcoor());
        assertEquals(7, b1.getYcoor());
        assertEquals(1, b1.getDir());

        b2.setXcoor(21);
        b2.setYcoor(22);
        assertEquals(21, b2.getXcoor());
        assertEquals(22, b2.getYcoor());
        assertEquals(-1, b2.getDir());
    }

    @Test
    void testMove() {
        b1.move();
        assertEquals(6, b1.getXcoor());
        assertEquals(1, b1.getYcoor());
        assertEquals(1, b1.getDir());

        b2.move();
        assertEquals(5, b2.getXcoor());
        assertEquals(1, b2.getYcoor());
        assertEquals(-1, b2.getDir());

    }
}
