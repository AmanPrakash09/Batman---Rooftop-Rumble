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
// Represents a reader that reads Scoreboard from JSON data stored in file
public class JsonReaderScoreboard {
    private String source;
//    private GUI gui;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderScoreboard(String source) {
        this.source = source;
//        gui = new GUI();
    }

    // EFFECTS: reads Scoreboard from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Scoreboard read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseScoreboard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return Files.readString(Paths.get(source), StandardCharsets.UTF_8);
    }

    // EFFECTS: parses Scoreboard from JSON object and returns it
    private Scoreboard parseScoreboard(JSONObject jsonObject) throws IOException {
        Scoreboard sb = new Scoreboard();
        JSONArray userList = jsonObject.getJSONArray("userList");
        JSONArray scoreList = jsonObject.getJSONArray("scoreList");
        sb.setScoreList(scoreList);
        sb.setUserList(userList);
        sb.sort();
        return sb;
    }
}
