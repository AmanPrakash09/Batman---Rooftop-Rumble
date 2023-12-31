package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBatman {
    private Batman batman;
    private Ninja ninja = new Ninja(20, 20, 0, 0, 500);

    @BeforeEach
    void runBefore() {
        batman = new Batman();
    }

    @Test
    void testConstructor() {
        assertEquals(10, batman.getXcoor());
        assertEquals(10, batman.getYcoor());
        assertFalse(batman.isOnGround());
        assertTrue(batman.isInAir());
        assertFalse(batman.isOnRoof());
        assertFalse(batman.isAtRightMost());
        assertTrue(batman.isFacingRight());
        assertFalse(batman.isPunching());
    }

    @Test
    void testHasPunched() {
        // batman is not punching
        assertFalse(batman.hasPunched(ninja));

        // batman punches, is not facing right, and is nowhere near ninja
        batman.moveLeft();  // faces left
        batman.punch();     // punches
        batman.setXcoor(50);
        batman.setYcoor(50);
        assertFalse(batman.hasPunched(ninja));

        // batman punches, is not facing right, and is not 10 DX space away from Ninja
        batman.moveLeft();  // faces left
        batman.punch();     // punches
        batman.setXcoor(50);
        batman.setYcoor(20);
        assertFalse(batman.hasPunched(ninja));

        // batman punches, is not facing right, and is not on the same y as Ninja
        batman.moveLeft();  // faces left
        batman.punch();     // punches
        batman.setXcoor(10);
        batman.setYcoor(10);
        assertFalse(batman.hasPunched(ninja));

        // batman punches, is not facing right, and is one space to the right of Ninja
        batman.moveLeft();  // faces left
        batman.punch();     // punches
        batman.setXcoor(30);
        batman.setYcoor(20);
        assertTrue(batman.hasPunched(ninja));

        // batman punches, is facing right, and is nowhere near ninja
        batman.moveRight();  // faces right
        batman.punch();     // punches
        batman.setXcoor(50);
        batman.setYcoor(50);
        assertFalse(batman.hasPunched(ninja));

        // batman punches, is facing right, and is not 1 DX space away from Ninja
        batman.moveRight();  // faces right
        batman.punch();     // punches
        batman.setXcoor(50);
        batman.setYcoor(20);
        assertFalse(batman.hasPunched(ninja));

        // batman punches, is facing right, and is not on the same y as Ninja
        batman.moveRight();  // faces right
        batman.punch();     // punches
        batman.setXcoor(10);
        batman.setYcoor(10);
        assertFalse(batman.hasPunched(ninja));

        // batman punches, is facing right, and is one space to the left of Ninja
        batman.moveRight();  // faces right
        batman.punch();     // punches
        batman.setXcoor(10);
        batman.setYcoor(20);
        assertTrue(batman.hasPunched(ninja));

        // batman punches, is facing right, and is in range to punch
        batman.moveRight();  // faces right
        batman.punch();     // punches
        batman.setXcoor(10);
        batman.setYcoor(20);
        assertTrue(batman.hasPunched(ninja));

        // batman punches, is facing left, and is in range to punch
        batman.moveLeft();  // faces left
        batman.punch();     // punches
        batman.setXcoor(30);
        batman.setYcoor(20);
        assertTrue(batman.hasPunched(ninja));

        // batman punches, is facing left, and is in range to punch but opposite side
        batman.moveLeft();  // faces left
        batman.punch();     // punches
        batman.setXcoor(10);
        batman.setYcoor(20);
        assertFalse(batman.hasPunched(ninja));

        batman.moveLeft();  // faces left
        batman.punch();     // punches
        batman.setXcoor(30);
        batman.setYcoor(10);
        assertFalse(batman.hasPunched(ninja));
    }

    @Test
    void TestFacingRight() {
        batman.faceRight();
        assertTrue(batman.isFacingRight());
    }

    @Test
    void TestGravity() {
        batman.fall();
        batman.gravity();
        assertEquals(11, batman.getYcoor());
    }

    @Test
    void TestGravityRoofNegative() {
        batman.putOnRoofNegative();
        batman.gravity();
        assertEquals(10, batman.getYcoor());
    }

    @Test
    void TestGravityGroundNegative() {
        batman.putOnGroundNegative();
        batman.gravity();
        assertEquals(10, batman.getYcoor());
    }

    @Test
    void TestGravityGround() {
        batman.putOnGround();
        batman.gravity();
        assertEquals(10, batman.getYcoor());
    }

    @Test
    void TestGravityRoof() {
        batman.putOnRoof();
        batman.gravity();
        assertEquals(10, batman.getYcoor());
    }

    @Test
    void TestMoveRight() {
        batman.moveRight();
        assertEquals(20, batman.getXcoor());
        assertTrue(batman.isFacingRight());
        assertFalse(batman.isPunching());

        batman.putAtRightEdge();
        batman.moveRight();
        assertEquals(20, batman.getXcoor());
        assertTrue(batman.isFacingRight());
        assertFalse(batman.isPunching());
    }

    @Test
    void TestMoveLeft() {
        batman.moveLeft();
        assertEquals(0, batman.getXcoor());
        assertFalse(batman.isFacingRight());
        assertFalse(batman.isPunching());

        batman.moveLeft();
        assertEquals(0, batman.getXcoor());
        assertFalse(batman.isFacingRight());
        assertFalse(batman.isPunching());
    }

    @Test
    void TestMoveUp() {
        batman.setXcoor(10);
        batman.setYcoor(10);

        // batman is falling, cannot jump
        batman.fall();
        batman.moveUp();
        assertTrue(batman.isInAir());
        assertFalse(batman.isOnGround());
        assertFalse(batman.isOnRoof());
        assertEquals(10, batman.getYcoor());
        assertFalse(batman.isPunching());

        // batman on ground, can jump
        batman.putOnGround();
        batman.moveUp();
        assertTrue(batman.isInAir());
        assertFalse(batman.isOnGround());
        assertFalse(batman.isOnRoof());
        assertEquals(-60, batman.getYcoor());
        assertFalse(batman.isPunching());

        // batman on roof, can jump
        batman.setYcoor(10);
        batman.putOnRoof();
        batman.moveUp();
        assertTrue(batman.isInAir());
        assertFalse(batman.isOnGround());
        assertFalse(batman.isOnRoof());
        assertEquals(-60, batman.getYcoor());
        assertFalse(batman.isPunching());

        // batman on jumped, cannot jump again until on ground/roof
        batman.setYcoor(10);
        batman.putOnRoof();
        batman.moveUp();
        assertTrue(batman.isInAir());
        assertFalse(batman.isOnGround());
        assertFalse(batman.isOnRoof());
        assertEquals(-60, batman.getYcoor());
        assertFalse(batman.isPunching());
        batman.moveUp();
        assertTrue(batman.isInAir());
        assertFalse(batman.isOnGround());
        assertFalse(batman.isOnRoof());
        assertEquals(-60, batman.getYcoor());
        assertFalse(batman.isPunching());
    }

    @Test
    void TestMoveDown() {
        batman.setXcoor(10);
        batman.setYcoor(10);

        // batman is on the ground, cannot move down
        batman.putOnGround();
        batman.moveDown();
        assertEquals(10, batman.getYcoor());
        assertFalse(batman.isPunching());

        // batman is in the air, cannot move down
        batman.fall();
        batman.moveDown();
        assertEquals(10, batman.getYcoor());
        assertFalse(batman.isPunching());

        // batman is on the roof, can move down
        batman.putOnRoof();
        batman.moveDown();
        assertEquals(20, batman.getYcoor());
        assertFalse(batman.isPunching());
    }

//    @Test
//    void testToJson() {
//        JSONObject jsonTest = batman.toJson();
////        this.xcoor = 1;
////        this.ycoor = 1;
////        this.onGround = false;
////        this.inAir = true;
////        this.onRoof = false;
////        this.atRightMost = false;
////        this.facingRight = true;
////        this.punching = false;
//        JSONObject json = new JSONObject();
//        json.put("xcoor", 1);
//        json.put("ycoor", 1);
//        json.put("onGround", false);
//        json.put("inAir", true);
//        json.put("atRightMost", false);
//        json.put("onRoof", false);
//        json.put("facingRight", true);
//        json.put("punching", false);
//
//        assertEquals(jsonTest, json);
//    }
}
