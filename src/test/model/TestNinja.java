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
}
