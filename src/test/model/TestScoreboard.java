package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestScoreboard {
    private Scoreboard sb;
    private Score s;
    private Score s1;
    private Score s2;

    @BeforeEach
    void runBefore() {
        sb = new Scoreboard();
        s = new Score("A", 0);
        s1 = new Score("B", 1);
        s2 = new Score("C", 2);
    }

    @Test
    void testConstructor() {
        assertEquals(0, sb.getSize());
    }

    @Test
    void testOneAdded() throws IOException {
        sb.addScore(s);
        assertEquals(1, sb.getSize());
        assertEquals("A", sb.getUserList().get(0));
        assertEquals(0, sb.getScoreList().get(0));

        Score sCopy = new Score("A", 7);
        sb.addScore(sCopy);
        assertEquals(1, sb.getSize());
        assertEquals("A", sb.getUserList().get(0));
        assertEquals(7, sb.getScoreList().get(0));
    }

    @Test
    void testAllAdded() throws IOException {
        sb.addScore(s);
        sb.addScore(s1);
        sb.addScore(s2);
        assertEquals(3, sb.getSize());
        assertEquals("C", sb.getUserList().get(0));
        assertEquals(2, sb.getScoreList().get(0));
        assertEquals("B", sb.getUserList().get(1));
        assertEquals(1, sb.getScoreList().get(1));
        assertEquals("A", sb.getUserList().get(2));
        assertEquals(0, sb.getScoreList().get(2));
    }

    @Test
    void testRemove() throws IOException {
        sb.addScore(s);
        assertEquals(1, sb.getSize());
        assertEquals("A", sb.getUserList().get(0));
        assertEquals(0, sb.getScoreList().get(0));

        assertTrue(sb.getScoreList().contains(0));
        assertTrue(sb.getUserList().contains("A"));
        sb.removeScore(s);
        assertEquals(0, sb.getSize());
        assertTrue(sb.getUserList().isEmpty());
        assertTrue(sb.getScoreList().isEmpty());

        // not added
        sb.removeScore(s1);
        assertEquals(0, sb.getSize());
        assertTrue(sb.getUserList().isEmpty());
        assertTrue(sb.getScoreList().isEmpty());
    }

    @Test
    void testSetScoreList() {
        JSONArray jaUser = new JSONArray();
        jaUser.put(10);
        jaUser.put(20);

        sb.setScoreList(jaUser);
        assertEquals(2, sb.getSize());
        assertEquals(10, sb.getScoreList().get(0));
        assertEquals(20, sb.getScoreList().get(1));
    }

    @Test
    void testSetUserList() {
        JSONArray jaUser = new JSONArray();
        jaUser.put("A");
        jaUser.put("B");

        sb.setUserList(jaUser);
        assertEquals(2, sb.getUserList().size());
        assertEquals("A", sb.getUserList().get(0));
        assertEquals("B", sb.getUserList().get(1));
    }
}
