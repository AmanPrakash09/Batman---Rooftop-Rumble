package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestScore {
    private Score s;

    @BeforeEach
    void runBefore() {
        s = new Score("user", 0);
    }

    @Test
    void testConstructor() {
        assertEquals("user", s.getName());
        assertEquals(0, s.getScore());
    }

}
