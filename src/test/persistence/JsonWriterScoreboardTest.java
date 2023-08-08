package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterScoreboardTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            Scoreboard sb = new Scoreboard();
            JsonWriterScoreboard writer = new JsonWriterScoreboard("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterInitialGame() {
        try {
            Scoreboard sb = new Scoreboard();
            Score s = new Score("A", 1);
            sb.addScore(s);
            JsonWriterScoreboard writer = new JsonWriterScoreboard("./data/testWriterSB.json");
            writer.open();
            writer.write(sb);
            writer.close();

            JsonReaderScoreboard reader = new JsonReaderScoreboard("./data/testWriterSB.json");
            sb = reader.read();
            assertEquals(1, sb.getSize());
            assertEquals("A", sb.getUserList().get(0));
            assertEquals(1, sb.getScoreList().get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
