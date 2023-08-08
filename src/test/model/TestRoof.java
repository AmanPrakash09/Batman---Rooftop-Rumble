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
        r1 = new Roof(50, 150, 400);
        r2 = new Roof(100, 250, 350);
        r3 = new Roof(150, 70, 300);
    }

    @Test
    void testConstructor() {
        assertEquals(50, r1.getXcoor());
        assertEquals(150, r1.getWidth());
        assertEquals(350, r1.getHeight());

        assertEquals(100, r2.getXcoor());
        assertEquals(250, r2.getWidth());
        assertEquals(300, r2.getHeight());

        assertEquals(150, r3.getXcoor());
        assertEquals(70, r3.getWidth());
        assertEquals(250, r3.getHeight());
    }

    @Test
    void testSetWidth() {
        r1.setWidth(15);
        assertEquals(50, r1.getXcoor());
        assertEquals(15, r1.getWidth());
        assertEquals(350, r1.getHeight());

        r2.setWidth(11);
        assertEquals(100, r2.getXcoor());
        assertEquals(11, r2.getWidth());
        assertEquals(300, r2.getHeight());

        r3.setWidth(2);
        assertEquals(150, r3.getXcoor());
        assertEquals(2, r3.getWidth());
        assertEquals(250, r3.getHeight());
    }

    @Test
    void testSetHeight() {
        r1.setHeight(150);
        assertEquals(50, r1.getXcoor());
        assertEquals(150, r1.getWidth());
        assertEquals(100, r1.getHeight());

        r2.setHeight(110);
        assertEquals(100, r2.getXcoor());
        assertEquals(250, r2.getWidth());
        assertEquals(60, r2.getHeight());

        r3.setHeight(60);
        assertEquals(150, r3.getXcoor());
        assertEquals(70, r3.getWidth());
        assertEquals(10, r3.getHeight());
    }
}
