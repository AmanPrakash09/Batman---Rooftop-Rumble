package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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

        assertEquals(8, game.getNinjas().size());
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
    void testHandleNinjaDamage() {
        // damage batman
        game.getBatman().setXcoor(2);
        game.getBatman().setYcoor(18);

        game.handleNinja();

        Ninja[] nArray = game.getNinjas().toArray(new Ninja[game.getNinjas().size()]);

//        Ninja n = game.getNinjas().stream().findFirst().get();
        Ninja n = nArray[0];

        n.setXcoor(2);
        n.setYcoor(18);

        int nXpos = n.getXcoor();
        int nYpos = n.getYcoor();

        assertEquals(2, nXpos);
        assertEquals(18, nYpos);
        assertEquals(0, game.getBatman().getXcoor());
        assertEquals(nYpos, game.getBatman().getYcoor());
        assertEquals(99, game.getHealth());

        // diff y, no damage
        game.getBatman().setXcoor(2);
        game.getBatman().setYcoor(10);
        assertEquals(2, nXpos);
        assertEquals(18, nYpos);
        assertEquals(99, game.getHealth());

        // diff x, no damage
        game.getBatman().setXcoor(3);
        game.getBatman().setYcoor(18);
        assertEquals(2, nXpos);
        assertEquals(18, nYpos);
        assertEquals(99, game.getHealth());

        // diff x and y, no damage
        game.getBatman().setXcoor(3);
        game.getBatman().setYcoor(17);
        assertEquals(2, nXpos);
        assertEquals(18, nYpos);
        assertEquals(99, game.getHealth());
    }

    @Test
    void testHandleNinjaDamage1() {
        // damage batman
        game.getBatman().setXcoor(10);
        game.getBatman().setYcoor(18);

        game.handleNinja();

        Ninja[] nArray = game.getNinjas().toArray(new Ninja[game.getNinjas().size()]);

//        Ninja n = game.getNinjas().stream().findFirst().get();
        Ninja n = nArray[1];

        n.setXcoor(10);
        n.setYcoor(18);

        int nXpos = n.getXcoor();
        int nYpos = n.getYcoor();

        assertEquals(10, nXpos);
        assertEquals(18, nYpos);
        assertEquals(8, game.getBatman().getXcoor());
        assertEquals(nYpos, game.getBatman().getYcoor());
        assertEquals(99, game.getHealth());

        // diff y, no damage
        game.getBatman().setXcoor(10);
        game.getBatman().setYcoor(10);
        assertEquals(10, nXpos);
        assertEquals(18, nYpos);
        assertEquals(99, game.getHealth());

        // diff x, no damage
        game.getBatman().setXcoor(11);
        game.getBatman().setYcoor(18);
        assertEquals(10, nXpos);
        assertEquals(18, nYpos);
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
        assertEquals(7, game.getNinjas().size());
        assertEquals(10, game.getScore());
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
//        assertTrue(game.getBatman().isOnRoof());
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
//        assertTrue(game.getBatman().isOnRoof());
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
//        assertTrue(game.getBatman().isOnRoof());
        game.isBatmanOnSurface();
        assertEquals(15, game.getRoof2().getXcoor());
        assertEquals(7, game.getRoof2().getWidth());
        assertEquals(5, game.getRoof2().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertFalse(game.getBatman().isInAir());
        assertTrue(game.getBatman().isOnRoof());

        // fall off roof, same y, left edge
        game.getRoof2().setHeight(10);
        // roof h = 15
        game.getBatman().setYcoor(3);
        // right edge of roof
        game.getBatman().setXcoor(14);
//        assertFalse(game.getBatman().isOnRoof());
        game.isBatmanOnSurface();
        assertEquals(15, game.getRoof2().getXcoor());
        assertEquals(7, game.getRoof2().getWidth());
        assertEquals(5, game.getRoof2().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertTrue(game.getBatman().isInAir());
        assertFalse(game.getBatman().isOnRoof());

        // fall off roof, same y, right edge
        game.getRoof2().setHeight(10);
        // roof h = 15
        game.getBatman().setYcoor(3);
        // right edge of roof
        game.getBatman().setXcoor(23);
//        assertFalse(game.getBatman().isOnRoof());
        game.isBatmanOnSurface();
        assertEquals(15, game.getRoof2().getXcoor());
        assertEquals(7, game.getRoof2().getWidth());
        assertEquals(5, game.getRoof2().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertTrue(game.getBatman().isInAir());
        assertFalse(game.getBatman().isOnRoof());

        // fall off roof, different y
        game.getRoof2().setHeight(10);
        // roof h = 15
        game.getBatman().setYcoor(2);
        // right edge of roof
        game.getBatman().setXcoor(20);
//        assertFalse(game.getBatman().isOnRoof());
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

        // fall off roof, same y, left edge
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

        // fall off roof, same y, right edge
        game.getRoof1().setHeight(15);
        // roof h = 15
        game.getBatman().setYcoor(8);
        // right edge of roof
        game.getBatman().setXcoor(36);
        game.isBatmanOnSurface();
        assertEquals(10, game.getRoof1().getXcoor());
        assertEquals(25, game.getRoof1().getWidth());
        assertEquals(10, game.getRoof1().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertTrue(game.getBatman().isInAir());
        assertFalse(game.getBatman().isOnRoof());

        // fall off roof, different y
        game.getRoof1().setHeight(15);
        // roof h = 15
        game.getBatman().setYcoor(7);
        // right edge of roof
        game.getBatman().setXcoor(15);
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

        // fall off roof, same y, left edge
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

        // fall off roof, same y, right edge
        game.getRoof().setHeight(20);
        // roof h = 15
        game.getBatman().setYcoor(13);
        // right edge of roof
        game.getBatman().setXcoor(21);
        game.isBatmanOnSurface();
        assertEquals(5, game.getRoof().getXcoor());
        assertEquals(15, game.getRoof().getWidth());
        assertEquals(15, game.getRoof().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertTrue(game.getBatman().isInAir());
        assertFalse(game.getBatman().isOnRoof());

        // fall off roof, different y
        game.getRoof().setHeight(20);
        // roof h = 15
        game.getBatman().setYcoor(12);
        // right edge of roof
        game.getBatman().setXcoor(10);
        game.isBatmanOnSurface();
        assertEquals(5, game.getRoof().getXcoor());
        assertEquals(15, game.getRoof().getWidth());
        assertEquals(15, game.getRoof().getHeight());
        assertFalse(game.getBatman().isOnGround());
        assertTrue(game.getBatman().isInAir());
        assertFalse(game.getBatman().isOnRoof());
    }

    @Test
    void testIsBatmanOnSurfaceNeverOnRoof() {
        // case where batman is never on any roofs
        game.getRoof().setHeight(20);
        game.getRoof1().setHeight(15);
        game.getRoof2().setHeight(10);

        // check roof
        assertEquals(5, game.getRoof().getXcoor());
        assertEquals(15, game.getRoof().getWidth());
        assertEquals(15, game.getRoof().getHeight());

        // check roof1
        assertEquals(10, game.getRoof1().getXcoor());
        assertEquals(25, game.getRoof1().getWidth());
        assertEquals(10, game.getRoof1().getHeight());

        // check roof2
        assertEquals(15, game.getRoof2().getXcoor());
        assertEquals(7, game.getRoof2().getWidth());
        assertEquals(5, game.getRoof2().getHeight());

        // set Batman not on any roof, should be falling
        game.getBatman().setYcoor(20);
        game.getBatman().setXcoor(1);
        game.isBatmanOnSurface();
        assertFalse(game.getBatman().isOnGround());
        assertTrue(game.getBatman().isInAir());
        assertFalse(game.getBatman().isOnRoof());
    }

    @Test
    void testBatmanIsFalling() {
        // initially Batman is at (1, 1)
        game.isBatmanOnSurface();
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

    @Test
    void testHandleNinjaKnockbackLeft() {
//        Ninja n = game.getNinjas().stream().findFirst().get();
//        n.setXcoor(6);
//        n.setYcoor(18);

        game.emptyNinjas();
        Ninja n = new Ninja(6,18,0,0,50);
        game.addNinja(n);

        Batman batman = game.getBatman();
        batman.faceRight();
        batman.setXcoor(6);
        batman.setYcoor(18);

        game.handleNinja();

        assertEquals(4, batman.getXcoor());
        assertEquals(99, game.getHealth());
    }

    @Test
    void testHandleNinjaKnockbackRight() {
//        Ninja n = game.getNinjas().stream().findFirst().get();
//        n.setXcoor(4);
//        n.setYcoor(18);

        game.emptyNinjas();
        Ninja n = new Ninja(4,18,0,0,50);
        game.addNinja(n);

        Batman batman = game.getBatman();
        batman.moveLeft();
        batman.setXcoor(4);
        batman.setYcoor(18);

        game.handleNinja();

        assertEquals(6, batman.getXcoor());
        assertEquals(99, game.getHealth());
    }

    @Test
    void testHandleNinjaNothingHappens() {
//        Ninja n = game.getNinjas().stream().findFirst().get();
//        n.setXcoor(4);
//        n.setYcoor(18);

        game.emptyNinjas();
        Ninja n = new Ninja(4,18,0,0,50);
        game.addNinja(n);

        Batman batman = game.getBatman();
        batman.moveLeft();
        batman.setXcoor(5);
        batman.setYcoor(18);

        game.handleNinja();

        assertEquals(5, batman.getXcoor());
        assertEquals(100, game.getHealth());

        batman.setXcoor(4);
        batman.setYcoor(19);

        assertEquals(4, batman.getXcoor());
        assertEquals(100, game.getHealth());

        batman.setXcoor(5);
        batman.setYcoor(19);

        assertEquals(5, batman.getXcoor());
        assertEquals(100, game.getHealth());
    }

    @Test
    void testLostBatarangRightBound() {
        Batarang b1 = new Batarang(20,20,true);
        Batarang b2 = new Batarang(30,20,true);
        game.addBatarang(b1);
        game.addBatarang(b2);

        assertEquals(2, game.getBatarangs().size());

        game.getBatarangs().get(0).setXcoor(51);
        game.lostBatarangs();

        assertEquals(1, game.getBatarangs().size());
    }

    @Test
    void testLostBatarangLeftBound() {
        Batarang b1 = new Batarang(20,20,false);
        Batarang b2 = new Batarang(10,20,false);
        game.addBatarang(b1);
        game.addBatarang(b2);

        assertEquals(2, game.getBatarangs().size());

        game.getBatarangs().get(0).setXcoor(-1);
        game.lostBatarangs();

        assertEquals(1, game.getBatarangs().size());
    }

    @Test
    void testCheckNinjaHitSuccess() {
        game.emptyNinjas();
        game.emptyBatarangs();

        game.emptyNinjas();
        Ninja n = new Ninja(5,5,0,0,50);
        game.addNinja(n);

        Batarang b1 = new Batarang(5,5,false);
        Batarang b2 = new Batarang(7,5,false);
        game.addBatarang(b1);
        game.addBatarang(b2);

        assertEquals(2, game.getBatarangs().size());
        assertTrue(game.checkNinjaHit(n, game.getBatarangs()));
        // number of Batarangs will increase for now
        assertEquals(3, game.getBatarangs().size());
        assertEquals(5, game.getScore());
    }

    @Test
    void testCheckNinjaHitFail() {
        game.emptyNinjas();
        game.emptyBatarangs();

        game.emptyNinjas();
        Ninja n = new Ninja(5,5,0,0,50);
        game.addNinja(n);

        Batarang b1 = new Batarang(6,5,false);
        Batarang b2 = new Batarang(7,5,false);
        game.addBatarang(b1);
        game.addBatarang(b2);

        assertEquals(2, game.getBatarangs().size());
        assertEquals(6, game.getBatarangs().get(0).getXcoor());
        assertEquals(7, game.getBatarangs().get(1).getXcoor());
        assertFalse(game.checkNinjaHit(n, game.getBatarangs()));
        // number of Batarangs will remain the same
        assertEquals(2, game.getBatarangs().size());
        assertEquals(0, game.getScore());
    }

    @Test
    void testBatarangAttack() {
        game.emptyNinjas();
        game.emptyBatarangs();

        game.emptyNinjas();
        Ninja n = new Ninja(5,5,0,0,50);
        game.addNinja(n);

        Batarang b1 = new Batarang(5,5,false);
        Batarang b2 = new Batarang(7,5,false);
        game.addBatarang(b1);
        game.addBatarang(b2);

        assertEquals(1, game.getNinjas().size());
        assertEquals(2, game.getBatarangs().size());
        game.batarangAttack();
        // number of Ninjas and Batarangs will decrease
        assertEquals(0, game.getNinjas().size());
        assertEquals(1, game.getBatarangs().size());
        assertEquals(5, game.getScore());
    }

    @Test
    void testMoveBatarangs() {
        game.emptyBatarangs();

        Batarang b1 = new Batarang(5,5,false);
        Batarang b2 = new Batarang(7,5,true);
        game.addBatarang(b1);
        game.addBatarang(b2);

        assertEquals(5, game.getBatarangs().get(0).getXcoor());
        assertEquals(7, game.getBatarangs().get(1).getXcoor());

        game.moveBatarangs();

        assertEquals(4, game.getBatarangs().get(0).getXcoor());
        assertEquals(8, game.getBatarangs().get(1).getXcoor());
    }

    @Test
    void testThrowBatarang() {
        Batman batman = game.getBatman();
        assertEquals(0, game.getBatarangs().size());

        game.throwBatarang();
        assertEquals(1, game.getBatarangs().size());
        assertEquals(1, game.getBatarangs().get(0).getXcoor());
        assertEquals(1, game.getBatarangs().get(0).getYcoor());
        assertEquals(1, game.getBatarangs().get(0).getDir());
    }

    @Test
    void testEndGame() {
        game.endGame();
        assertTrue(game.isEnded());
    }

    @Test
    void testSetScore() {
        game.setScore(21);
        assertEquals(21, game.getScore());
    }

    @Test
    void testSetBatman() {
        game.getBatman().setXcoor(21);
        game.getBatman().setYcoor(21);

        Batman batman = new Batman();
        game.setBatman(batman);
        assertEquals(1, game.getBatman().getXcoor());
        assertEquals(1, game.getBatman().getYcoor());
    }
}
