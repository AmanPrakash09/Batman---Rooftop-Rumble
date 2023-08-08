package persistence;

import model.Batarang;
import model.Game;
import model.Ninja;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Game g = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderRandomGame() {
        JsonReader reader = new JsonReader("./data/testReaderRandomGame.json");
        try {
            Game g = reader.read();
            assertEquals(45, g.getScore());
            Ninja[] nArray = g.getNinjas().toArray(new Ninja[g.getNinjas().size()]);
            checkNinjas(20, 18, -1, 0, 39, nArray[0]);
            assertEquals(85, g.getHealth());
            checkBatman(8, 13, false, false, true, false, true, false, g.getBatman());
            List<Batarang> bList = g.getBatarangs();
            checkBatarang(25, 13, 1, bList.get(0));
            checkBatarang(21, 13, 1, bList.get(1));
            checkBatarang(18, 13, 1, bList.get(2));
            checkBatarang(12, 13, 1, bList.get(3));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderSpecificGame() {
        JsonReader reader = new JsonReader("./data/testReaderSpecificGame.json");
        try {
            Game g = reader.read();
            assertEquals(55, g.getScore());
            Ninja[] nArray = g.getNinjas().toArray(new Ninja[g.getNinjas().size()]);
            checkNinjas(28, 18, 1, 0, 39, nArray[0]);
            assertEquals(90, g.getHealth());
            checkBatman(39, 18, true, true, true, true, false, false, g.getBatman());
            List<Batarang> bList = g.getBatarangs();
            assertEquals(0, bList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderSpecificGame2() {
        JsonReader reader = new JsonReader("./data/testReaderSpecificGame2.json");
        try {
            Game g = reader.read();
            assertEquals(35, g.getScore());
            Ninja[] nArray = g.getNinjas().toArray(new Ninja[g.getNinjas().size()]);
            checkNinjas(479, 393, 1, 0, 500, nArray[0]);
            assertEquals(100, g.getHealth());
            checkBatman(210, 243, false, false, true, false, true, false, g.getBatman());
            List<Batarang> bList = g.getBatarangs();
            assertEquals(7, bList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderSpecificGame3() {
        JsonReader reader = new JsonReader("./data/testReaderSpecificGame3.json");
        try {
            Game g = reader.read();
            assertEquals(35, g.getScore());
            Ninja[] nArray = g.getNinjas().toArray(new Ninja[g.getNinjas().size()]);
            checkNinjas(248, 393, -1, 0, 500, nArray[0]);
            assertEquals(100, g.getHealth());
//            System.out.println(g.getBatman().isInAir());
            checkBatman(160, 187, false, false, true, false,
                    false, true, g.getBatman());
//            assertEquals(160, g.getBatman().getXcoor());
//            assertEquals(187, g.getBatman().getYcoor());
//            assertFalse(g.getBatman().isPunching());
//            assertFalse(g.getBatman().isOnGround());
//            assertTrue(g.getBatman().isFacingRight());
//            assertFalse(g.getBatman().isAtRightMost());
//            assertFalse(g.getBatman().isOnRoof());
//            assertTrue(g.getBatman().isInAir());
            List<Batarang> bList = g.getBatarangs();
            assertEquals(0, bList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
