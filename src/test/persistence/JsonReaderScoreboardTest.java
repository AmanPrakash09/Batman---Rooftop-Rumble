package persistence;

import model.Batarang;
import model.Game;
import model.Ninja;
import model.Scoreboard;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderScoreboardTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReaderScoreboard reader = new JsonReaderScoreboard("./data/noSuchFile.json");
        try {
            Scoreboard sb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderRandomGame() {
        JsonReaderScoreboard reader = new JsonReaderScoreboard("./data/testReaderRandomSB.json");
        try {
            Scoreboard sb = reader.read();
            assertEquals(3, sb.getSize());
            assertEquals("A", sb.getUserList().get(0));
            assertEquals("B", sb.getUserList().get(1));
            assertEquals("C", sb.getUserList().get(2));
            assertEquals(75, sb.getScoreList().get(0));
            assertEquals(70, sb.getScoreList().get(1));
            assertEquals(65, sb.getScoreList().get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
