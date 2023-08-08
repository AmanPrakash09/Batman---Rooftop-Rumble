package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.*;
import org.json.*;
import ui.GUI;

// CITATION: framework of code was taken from JsonSerializationDemo
// Represents a reader that reads game from JSON data stored in file
public class JsonReader {
    private String source;
//    private GUI gui;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
//        gui = new GUI();
    }

    // EFFECTS: reads game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return Files.readString(Paths.get(source), StandardCharsets.UTF_8);
    }

    // EFFECTS: parses game from JSON object and returns it
    private Game parseGame(JSONObject jsonObject) throws IOException {
        Game g = new Game(GUI.WIDTH,GUI.HEIGHT);
        addNinjas(g, jsonObject);
        addBatarangs(g, jsonObject);
        addBatmanPrime(g, jsonObject);
        int health = jsonObject.getInt("health");
        int score = jsonObject.getInt("score");
        g.setHealth(health);
        g.setScore(score);
        return g;
    }

    // MODIFIES: g
    // EFFECTS: parses ninjas from JSON object and adds them to game
    private void addNinjas(Game g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("ninjas");
        g.emptyNinjas();
        for (Object json : jsonArray) {
            JSONObject nextNinja = (JSONObject) json;
            addNinja(g, nextNinja);
        }
    }

    // MODIFIES: g
    // EFFECTS: parses ninja from JSON object and adds it to game
    private void addNinja(Game g, JSONObject jsonObject) {
        int xcoor = jsonObject.getInt("xcoor");
        int ycoor = jsonObject.getInt("ycoor");
        int dir = jsonObject.getInt("dir");
        int leftBound = jsonObject.getInt("leftBound");
        int rightBound = jsonObject.getInt("rightBound");
        Ninja ninja = new Ninja(xcoor, ycoor, dir, leftBound, rightBound);

        g.addNinja(ninja);
    }

    // MODIFIES: g
    // EFFECTS: parses batarangs from JSON object and adds them to game
    private void addBatarangs(Game g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("batarangs");
        g.emptyBatarangs();
        for (Object json : jsonArray) {
            JSONObject nextBatarang = (JSONObject) json;
            addBatarang(g, nextBatarang);
        }
    }

    // MODIFIES: g
    // EFFECTS: parses batarang from JSON object and adds it to game
    private void addBatarang(Game g, JSONObject jsonObject) {
        int xcoor = jsonObject.getInt("xcoor");
        int ycoor = jsonObject.getInt("ycoor");
        int dir = jsonObject.getInt("dir");
        boolean facingRight = false;
        if (dir == 1) {
            facingRight = true;
        }
        Batarang batarang = new Batarang(xcoor, ycoor, facingRight);

        g.addBatarang(batarang);
    }

    // MODIFIES: g
    // EFFECTS: parses batman from JSON object and adds them to game
    private void addBatmanPrime(Game g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("batman");
        for (Object json : jsonArray) {
            JSONObject nextBatman = (JSONObject) json;
            addBatman(g, nextBatman);
        }
    }

    // MODIFIES: g
    // EFFECTS: parses batman from JSON object and adds it to game
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void addBatman(Game g, JSONObject jsonObject) {
        int xcoor = jsonObject.getInt("xcoor");
        int ycoor = jsonObject.getInt("ycoor");
        boolean onGround = jsonObject.getBoolean("onGround");
        boolean inAir = jsonObject.getBoolean("inAir");
        boolean atRightMost = jsonObject.getBoolean("atRightMost");
        boolean onRoof = jsonObject.getBoolean("onRoof");
        boolean facingRight = jsonObject.getBoolean("facingRight");
        boolean punching = jsonObject.getBoolean("punching");

        Batman batman = new Batman();
        batman.setXcoor(xcoor);
        batman.setYcoor(ycoor);

        if (onGround) {
            batman.putOnGround();
        }

        if (inAir) {
            batman.fall();
        }

        if (atRightMost) {
            batman.putAtRightEdge();
        }

        if (onRoof) {
            batman.putOnRoof();
        }

        if (facingRight) {
            batman.faceRight();
        }

        if (punching) {
            batman.punch();
        }

        g.setBatman(batman);
    }
}
