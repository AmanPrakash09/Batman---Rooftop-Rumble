package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNinja {
    private Ninja n1;
    private Ninja n2;

    @BeforeEach
    void runBefore() {
        n1 = new Ninja(1, 7);
        n2 = new Ninja(4,18);
    }

    @Test
    void testConstructor() {
        assertEquals(1, n1.getXcoor());
        assertEquals(7, n1.getYcoor());

        assertEquals(4, n2.getXcoor());
        assertEquals(18, n2.getYcoor());
    }

    @Test
    void testGetters() {
        n1.setXcoor(9);
        n1.setYcoor(4);
        assertEquals(9, n1.getXcoor());
        assertEquals(4, n1.getYcoor());

        n2.setXcoor(20);
        n2.setYcoor(19);
        assertEquals(20, n2.getXcoor());
        assertEquals(19, n2.getYcoor());
    }
}
