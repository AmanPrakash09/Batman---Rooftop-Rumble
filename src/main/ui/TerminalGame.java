package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.AbstractWindow;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TerminalGame {
    private Game game;
    private Screen screen;
    private WindowBasedTextGUI endGui;
    private int sizeX;
    private int sizeY;

    private static final String JSON_STORE = "./data/game.json";
//    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    /**
     * Begins the game and method does not leave execution
     * until game is complete.
     */
    public void start() throws IOException, InterruptedException {
        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        TerminalSize terminalSize = screen.getTerminalSize();
        this.sizeX = (terminalSize.getColumns() - 1) / 2;
        this.sizeY = terminalSize.getRows() - 2;

        System.out.println(sizeX);
        System.out.println(sizeY);

//        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        game = new Game((terminalSize.getColumns() - 1) / 2,terminalSize.getRows() - 2);

        beginTicks();
    }

    private void beginTicks() throws IOException, InterruptedException {
        while (!game.isEnded() || endGui.getActiveWindow() != null) {

//            String command = null;
//            input = new Scanner(System.in);

//            displayMenu();
//            command = input.next();
//            command = command.toLowerCase();
//
//            if (command.equals("q")) {
//                game.endGame();
//            } else if (command.equals("c")) {
//                continue;
//            } else {
//                processCommand(command);
//            }

            tick();
            Thread.sleep(1000L / Game.TICKS_PER_SECOND);
        }

        addScoreToScoreboard();
        System.exit(0);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ts -> save game to file");
        System.out.println("\tl -> load game from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("s")) {
            saveGame();
        } else if (command.equals("l")) {
            loadGame();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: saves the game to file
    private void saveGame() {
        System.out.println("save");
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    private void loadGame() {
        System.out.println("load");
        try {
            game = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public void addScoreToScoreboard() throws IOException {
        Scanner name = new Scanner(System.in);
        String playerName;

        // Enter name and press Enter
        System.out.println("Enter Name");
        playerName = name.nextLine();

        Score s = new Score(playerName, game.getScore());

        Scoreboard sb = new Scoreboard();
        sb.addScore(s);

        List<String> userList = sb.getUserList();
        List<Integer> scoreList = sb.getScoreList();

        File file = new File("./data/scoreboard.txt");
        FileWriter fr = new FileWriter(file, true);

        for (int i = 0; i < userList.size(); i++) {
            String nameAndScore = userList.get(i).concat(": ").concat(Integer.toString(scoreList.get(i)));
            fr.write(nameAndScore + "\n");
        }

        fr.close();

//        System.out.println(sb.getList().get(0));

//        String nameAndScore = playerName.concat(": ").concat(Integer.toString(getScore()));
//
////        System.out.println(playerName + ": " + getScore());
//        System.out.println(nameAndScore);
//
//        File file = new File("./data/scoreboard.txt");
//        FileWriter fr = new FileWriter(file, true);
//        fr.write(nameAndScore + "\n");
//        fr.close();
//
////        File file = new File(".");
////        for (String fileNames : file.list()) {
////            System.out.println(fileNames);
//        }
    }

    private void tick() throws IOException {
        handleUserInput();

        game.tick();

        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();
        render();
        screen.refresh();

        screen.setCursorPosition(new TerminalPosition(screen.getTerminalSize().getColumns() - 1, 0));
    }

    /**
     * Control's of Batman
     */
    private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();

        if (stroke == null) {
            return;
        }

        KeyType type = stroke.getKeyType();
        switchCases(type);
        switchCases1(type);
    }

    public void switchCases(KeyType type) {
        switch (type) {
            case ArrowUp:
                game.getBatman().moveUp();
                break;
            case ArrowDown:
                game.getBatman().moveDown();
                break;
            case ArrowRight:
                game.getBatman().moveRight();
                break;
            case ArrowLeft:
                game.getBatman().moveLeft();
                break;
        }
    }

    public void switchCases1(KeyType type) {
        switch (type) {
            case Backspace:
                game.getBatman().punch();
                break;
            case Tab:
                game.throwBatarang();
                break;
            case Character:
                saveGame();
                break;
            case Escape:
                loadGame();
                break;
            default:
                return;
        }
    }

    private void render() {
        if (game.isEnded()) {
            if (endGui == null) {
                drawEndScreen();
            }

            return;
        }

        drawBatman();
        drawGround();
        drawRoofs();
        drawHealth();
        drawScore();
        drawBatarang();
        drawNinja();
    }

    private void drawEndScreen() {
        endGui = new MultiWindowTextGUI(screen);

        new MessageDialogBuilder()
                .setTitle("Game over!")
                .setText("You finished with a score of " + game.getScore() + "!")
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(endGui);
    }

//    private void drawEndScreen() {
//        final TextBox nameTextBox = new TextBox();
//        new AbstractWindow("Game over!") {
//            public void addTo(WindowBasedTextGUI endGui) {
//            }
//
//            {
//                setComponent(nameTextBox);
//            }
//        }.addTo(endGui);
//
//        String playerName = nameTextBox.getText();
//
//        new MessageDialogBuilder()
//                .setTitle("Game over!")
//                .setText("Player: " + playerName + "\nScore: " + game.getScore())
//                .addButton(MessageDialogButton.Close)
//                .build()
//                .showDialog(endGui);
//    }

    private void drawBatman() {
        Batman batman = game.getBatman();
        drawPosition(batman.getXcoor(), batman.getYcoor(), TextColor.ANSI.GREEN, 'b');

    }

    private void drawBatarang() {
//        Batarang batarang = game.getBatarangs();
//
//        if (batarang != null) {
//            drawPosition(batarang.getXcoor(), batarang.getYcoor(), TextColor.ANSI.WHITE, '-');
//        }

        for (Batarang batarang : game.getBatarangs()) {
            drawPosition(batarang.getXcoor(), batarang.getYcoor(), TextColor.ANSI.WHITE, '-');
        }
    }

    private void drawNinja() {
        for (Ninja ninja : game.getNinjas()) {
            drawPosition(ninja.getXcoor(), ninja.getYcoor(), TextColor.ANSI.RED, 'n');
        }
    }

    private void drawGround() {
        Ground ground = game.getGround();

        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.BLUE);
        text.drawLine(0, this.sizeY - 2, this.sizeX * 2, this.sizeY - 2, 'g');

        ground.setWidth(this.sizeX * 2);
        ground.setHeight(this.sizeY - 2);

    }

//    public void drawRoofs() {
//        drawRoof(game.)
//    }

    private void drawRoofs() {
        Roof roof = game.getRoof();
        Roof roof1 = game.getRoof1();
        Roof roof2 = game.getRoof2();

        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.YELLOW);
        text.drawLine(roof.getXcoor() + 5, roof.getHeight(),
                roof.getXcoor() + 2 * roof.getWidth() + 5, roof.getHeight(), 'r');
        text.drawLine(roof1.getXcoor() + 10, roof1.getHeight(),
                roof1.getXcoor() + 2 * roof1.getWidth() + 10, roof1.getHeight(), 'r');
        text.drawLine(roof2.getXcoor() + 15, roof2.getHeight(),
                roof2.getXcoor() + 2 * roof2.getWidth() + 15, roof2.getHeight(), 'r');

        roof.setHeight(this.sizeY - 2);
        roof1.setHeight(roof.getHeight());
        roof2.setHeight(roof1.getHeight());
    }

    private void drawPosition(int x, int y, TextColor color, char c) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(x * 2, y + 1, String.valueOf(c));

//        if (wide) {
//            text.putString(x * 2 + 1, y + 1, String.valueOf(c));
//        }
    }

    private void drawHealth() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.GREEN);
        text.putString(1, 0, "Health: ");

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(8, 0, String.valueOf(game.getHealth()));
    }

    private void drawScore() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.GREEN);
        text.putString(70, 0, "Score: ");

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(77, 0, String.valueOf(game.getScore()));
    }
}
