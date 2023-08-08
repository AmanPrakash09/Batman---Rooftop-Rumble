package persistence;

import model.Batarang;
import model.Game;
import model.Ninja;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            Game g = new Game(50, 50);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterInitialGame() {
        try {
            Game g = new Game(50, 50);
            Batarang batarang = new Batarang(1, 1, true);
            g.addBatarang(batarang);
            JsonWriter writer = new JsonWriter("./data/testWriterInitialGame.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterInitialGame.json");
            g = reader.read();
            assertEquals(0, g.getScore());
            checkBatman(10,10,false,false,true,false,false,true, g.getBatman());
            assertEquals(1, g.getBatarangs().size());
            checkBatarang(1,1,1,g.getBatarangs().get(0));
            assertEquals(100, g.getHealth());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
