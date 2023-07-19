package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestGame {

    private Game game;

    @BeforeEach
    void runBefore() {
        game = new Game(50, 50);
    }

    @Test
    void testConstructor() {
        assertEquals(50 ,game.getMaxX());
        assertEquals(50 ,game.getMaxY());
        assertEquals(100 ,game.getHealth());
        assertFalse(game.isEnded());

        assertEquals(4, game.getNinjas().size());
    }

    @Test
    void testTickInitial() {
        // initially, batman is in the air
        game.isBatmanOnSurface();
        assertTrue(game.getBatman().isInAir());
        assertFalse(game.getBatman().isOnRoof());
        assertFalse(game.getBatman().isOnGround());
    }

    @Test
    void testTickHealth() {
        game.setHealth(0);
        assertEquals(0, game.getHealth());
        game.tick();
        assertTrue(game.isEnded());
    }

    @Test
    void testTickNinjas() {
        game.handleNinja();
        game.emptyNinjas();
        game.tick();
        assertTrue(game.getNinjas().isEmpty());
        assertTrue(game.isEnded());
    }

    @Test
    void testHandleNinja() {
        // damage batman
        game.getBatman().setXcoor(2);
        game.getBatman().setYcoor(18);

        game.handleNinja();

        int nXpos = game.getNinjas().stream().findFirst().get().getXcoor();
        int nYpos = game.getNinjas().stream().findFirst().get().getYcoor();

        assertEquals(2, nXpos);
        assertEquals(18, nYpos);
        assertEquals(nXpos, game.getBatman().getXcoor());
        assertEquals(nYpos, game.getBatman().getYcoor());
        assertEquals(99, game.getHealth());
    }
}
