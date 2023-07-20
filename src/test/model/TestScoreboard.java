package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestScoreboard {
    private Scoreboard sl;
    private Score s;
    private Score s1;
    private Score s2;

    @BeforeEach
    void runBefore() {
        sl = new Scoreboard();
        s = new Score("A", 0);
        s1 = new Score("B", 1);
        s2 = new Score("C", 2);
    }

    @Test
    void testConstructor() {
        assertEquals(0, sl.getSize());
    }

    @Test
    void testOneAdded() {
        sl.addScore(s);
        assertEquals(1, sl.getSize());
        assertEquals("A", sl.getUserList().get(0));
        assertEquals(0, sl.getScoreList().get(0));

        Score sCopy = new Score("A", 7);
        sl.addScore(sCopy);
        assertEquals(1, sl.getSize());
        assertEquals("A", sl.getUserList().get(0));
        assertEquals(7, sl.getScoreList().get(0));
    }

    @Test
    void testAllAdded() {
        sl.addScore(s);
        sl.addScore(s1);
        sl.addScore(s2);
        assertEquals(3, sl.getSize());
        assertEquals("A", sl.getUserList().get(0));
        assertEquals(0, sl.getScoreList().get(0));
        assertEquals("B", sl.getUserList().get(1));
        assertEquals(1, sl.getScoreList().get(1));
        assertEquals("C", sl.getUserList().get(2));
        assertEquals(2, sl.getScoreList().get(2));
    }
}
