package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        game.tick();
//        game.isBatmanOnSurface();
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

        Ninja n = game.getNinjas().stream().findFirst().get();

        n.setXcoor(2);
        n.setYcoor(18);

        int nXpos = n.getXcoor();
        int nYpos = n.getYcoor();

        assertEquals(2, nXpos);
        assertEquals(18, nYpos);
        assertEquals(nXpos, game.getBatman().getXcoor());
        assertEquals(nYpos, game.getBatman().getYcoor());
        assertEquals(99, game.getHealth());
    }

    @Test
    void testHandleNinjaHit() {
        Ninja n = game.getNinjas().stream().findFirst().get();
        n.setXcoor(2);
        n.setYcoor(18);
        // make batman punch enemy
        game.getBatman().moveRight(); // facing right
        game.getBatman().setXcoor(1);
        game.getBatman().setYcoor(18);
        // batman is now facing right and is left of ninja, now punch
        game.getBatman().punch();
        assertTrue(game.getBatman().isFacingRight());
        assertEquals(n.getXcoor(), game.getBatman().getXcoor() + 1);
        assertTrue(game.getBatman().hasPunched(n));
        // defeatedNinja = ninjas[0], remove this ninja
        game.handleNinja();
        assertEquals(3, game.getNinjas().size());
        assertEquals(1, game.getScore());
    }

    @Test
    void testIsBatmanOnSurfaceGround() {
        // put batman on the ground
        game.getBatman().setYcoor(18);
        game.getGround().setHeight(20);
        game.isBatmanOnSurface();
        assertTrue(game.getBatman().isOnGround());
        assertFalse(game.getBatman().isInAir());
        assertFalse(game.getBatman().isOnRoof());
    }

    @Test
    void testIsBatmanOnSurfaceRoof2() {
        // put batman on roof
        game.getRoof2().setHeight(10);
        // roof h = 15
        game.getBatman().setYcoor(3);
        // left edge of roof
        game.getBatman().setXcoor(15);
        game.isBatmanOnSurface();
        assertEquals(15, game.getRoof2().getXcoor());
        assertEquals(7, game.getRoof2().getWidth());
        assertEquals(5, game.getRoof2().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertFalse(game.getBatman().isInAir());
        assertTrue(game.getBatman().isOnRoof());

        // put batman on roof
        game.getRoof2().setHeight(10);
        // roof h = 15
        game.getBatman().setYcoor(3);
        // right edge of roof
        game.getBatman().setXcoor(22);
        game.isBatmanOnSurface();
        assertEquals(15, game.getRoof2().getXcoor());
        assertEquals(7, game.getRoof2().getWidth());
        assertEquals(5, game.getRoof2().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertFalse(game.getBatman().isInAir());
        assertTrue(game.getBatman().isOnRoof());

        // put batman on roof
        game.getRoof2().setHeight(10);
        // roof h = 15
        game.getBatman().setYcoor(3);
        // middle of roof
        game.getBatman().setXcoor(20);
        game.isBatmanOnSurface();
        assertEquals(15, game.getRoof2().getXcoor());
        assertEquals(7, game.getRoof2().getWidth());
        assertEquals(5, game.getRoof2().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertFalse(game.getBatman().isInAir());
        assertTrue(game.getBatman().isOnRoof());

        // fall off roof, same y
        game.getRoof2().setHeight(10);
        // roof h = 15
        game.getBatman().setYcoor(3);
        // right edge of roof
        game.getBatman().setXcoor(14);
        game.isBatmanOnSurface();
        assertEquals(15, game.getRoof2().getXcoor());
        assertEquals(7, game.getRoof2().getWidth());
        assertEquals(5, game.getRoof2().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertTrue(game.getBatman().isInAir());
        assertFalse(game.getBatman().isOnRoof());
    }

    @Test
    void testIsBatmanOnSurfaceRoof1() {
        // put batman on roof
        game.getRoof1().setHeight(15);
        // roof h = 15
        game.getBatman().setYcoor(8);
        // left edge of roof
        game.getBatman().setXcoor(10);
        game.isBatmanOnSurface();
        assertEquals(10, game.getRoof1().getXcoor());
        assertEquals(25, game.getRoof1().getWidth());
        assertEquals(10, game.getRoof1().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertFalse(game.getBatman().isInAir());
        assertTrue(game.getBatman().isOnRoof());

        // put batman on roof
        game.getRoof1().setHeight(15);
        // roof h = 15
        game.getBatman().setYcoor(8);
        // right edge of roof
        game.getBatman().setXcoor(35);
        game.isBatmanOnSurface();
        assertEquals(10, game.getRoof1().getXcoor());
        assertEquals(25, game.getRoof1().getWidth());
        assertEquals(10, game.getRoof1().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertFalse(game.getBatman().isInAir());
        assertTrue(game.getBatman().isOnRoof());

        // put batman on roof
        game.getRoof1().setHeight(15);
        // roof h = 15
        game.getBatman().setYcoor(8);
        // middle of roof
        game.getBatman().setXcoor(20);
        game.isBatmanOnSurface();
        assertEquals(10, game.getRoof1().getXcoor());
        assertEquals(25, game.getRoof1().getWidth());
        assertEquals(10, game.getRoof1().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertFalse(game.getBatman().isInAir());
        assertTrue(game.getBatman().isOnRoof());

        // fall off roof, same y
        game.getRoof1().setHeight(15);
        // roof h = 15
        game.getBatman().setYcoor(8);
        // right edge of roof
        game.getBatman().setXcoor(9);
        game.isBatmanOnSurface();
        assertEquals(10, game.getRoof1().getXcoor());
        assertEquals(25, game.getRoof1().getWidth());
        assertEquals(10, game.getRoof1().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertTrue(game.getBatman().isInAir());
        assertFalse(game.getBatman().isOnRoof());
    }

    @Test
    void testIsBatmanOnSurfaceRoof() {
        // put batman on roof
        game.getRoof().setHeight(20);
        // roof h = 15
        game.getBatman().setYcoor(13);
        // left edge of roof
        game.getBatman().setXcoor(5);
        game.isBatmanOnSurface();
        assertEquals(5, game.getRoof().getXcoor());
        assertEquals(15, game.getRoof().getWidth());
        assertEquals(15, game.getRoof().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertFalse(game.getBatman().isInAir());
        assertTrue(game.getBatman().isOnRoof());

        // put batman on roof
        game.getRoof().setHeight(20);
        // roof h = 15
        game.getBatman().setYcoor(13);
        // right edge of roof
        game.getBatman().setXcoor(20);
        game.isBatmanOnSurface();
        assertEquals(5, game.getRoof().getXcoor());
        assertEquals(15, game.getRoof().getWidth());
        assertEquals(15, game.getRoof().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertFalse(game.getBatman().isInAir());
        assertTrue(game.getBatman().isOnRoof());

        // put batman on roof
        game.getRoof().setHeight(20);
        // roof h = 15
        game.getBatman().setYcoor(13);
        // middle of roof
        game.getBatman().setXcoor(10);
        game.isBatmanOnSurface();
        assertEquals(5, game.getRoof().getXcoor());
        assertEquals(15, game.getRoof().getWidth());
        assertEquals(15, game.getRoof().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertFalse(game.getBatman().isInAir());
        assertTrue(game.getBatman().isOnRoof());

        // fall off roof, same y
        game.getRoof().setHeight(20);
        // roof h = 15
        game.getBatman().setYcoor(13);
        // right edge of roof
        game.getBatman().setXcoor(4);
        game.isBatmanOnSurface();
        assertEquals(5, game.getRoof().getXcoor());
        assertEquals(15, game.getRoof().getWidth());
        assertEquals(15, game.getRoof().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertTrue(game.getBatman().isInAir());
        assertFalse(game.getBatman().isOnRoof());
    }

    @Test
    void testIsBatmanOnSurfaceRight() {
        game.getBatman().setXcoor(50);
        game.isBatmanOnSurface();
        assertTrue(game.getBatman().isAtRightMost());
    }


}
