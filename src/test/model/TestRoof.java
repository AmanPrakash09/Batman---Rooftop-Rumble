package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRoof {
    private Roof r1;
    private Roof r2;
    private Roof r3;

    @BeforeEach
    void runBefore() {
        r1 = new Roof(1, 10, 6);
        r2 = new Roof(10, 3, 8);
        r3 = new Roof(20, 5, Roof.SPACE);
    }

    @Test
    void testConstructor() {
        assertEquals(1, r1.getXcoor());
        assertEquals(10, r1.getWidth());
        assertEquals(1, r1.getHeight());

        assertEquals(10, r2.getXcoor());
        assertEquals(3, r2.getWidth());
        assertEquals(3, r2.getHeight());

        assertEquals(20, r3.getXcoor());
        assertEquals(5, r3.getWidth());
        assertEquals(0, r3.getHeight());
    }

    @Test
    void testSetWidth() {
        r1.setWidth(15);
        assertEquals(1, r1.getXcoor());
        assertEquals(15, r1.getWidth());
        assertEquals(1, r1.getHeight());

        r2.setWidth(11);
        assertEquals(10, r2.getXcoor());
        assertEquals(11, r2.getWidth());
        assertEquals(3, r2.getHeight());

        r3.setWidth(2);
        assertEquals(20, r3.getXcoor());
        assertEquals(2, r3.getWidth());
        assertEquals(0, r3.getHeight());
    }

    @Test
    void testSetHeight() {
        r1.setHeight(15);
        assertEquals(1, r1.getXcoor());
        assertEquals(10, r1.getWidth());
        assertEquals(10, r1.getHeight());

        r2.setHeight(11);
        assertEquals(10, r2.getXcoor());
        assertEquals(3, r2.getWidth());
        assertEquals(6, r2.getHeight());

        r3.setHeight(6);
        assertEquals(20, r3.getXcoor());
        assertEquals(5, r3.getWidth());
        assertEquals(1, r3.getHeight());
    }
}
