package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestGround {
    private Ground g;

    @BeforeEach
    void runBefore() {
        g = new Ground();
    }

    @Test
    void testConstructor() {
        assertEquals(1, g.getWidth());
        assertEquals(1, g.getHeight());
    }

    @Test
    void testSetWidth() {
        int i = 2;
        g.setWidth(i);
        assertEquals(2, g.getWidth());
        assertEquals(1, g.getHeight());

        i = 10;
        g.setWidth(i);
        assertEquals(10, g.getWidth());
        assertEquals(1, g.getHeight());
    }

    @Test
    void testSetHeight() {
        int i = 2;
        g.setHeight(i);
        assertEquals(1, g.getWidth());
        assertEquals(2, g.getHeight());

        i = 10;
        g.setHeight(i);
        assertEquals(1, g.getWidth());
        assertEquals(10, g.getHeight());
    }
}
