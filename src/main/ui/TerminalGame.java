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

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Scanner;

public class TerminalGame {
    private Game game;
    private Screen screen;
    private WindowBasedTextGUI endGui;
    private int sizeX;
    private int sizeY;

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

        game = new Game((terminalSize.getColumns() - 1) / 2,terminalSize.getRows() - 2);

        beginTicks();
    }

    private void beginTicks() throws IOException, InterruptedException {
        while (!game.isEnded() || endGui.getActiveWindow() != null) {
            tick();
            Thread.sleep(1000L / Game.TICKS_PER_SECOND);
        }

        addScoreToScoreboard();
        System.exit(0);
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
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();

        if (stroke == null) {
            return;
        }

//        KeyStroke keyStroke;
//        try {
//            keyStroke = screen.readInput();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        // Retrieve the KeyEvent from the KeyStroke
//        Character keyEvent = keyStroke.getCharacter();

//        if (stroke.getCharacter() != null) {
//            return;
//        }

        KeyType type = stroke.getKeyType();
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
//            case Escape:
//                game.getBatman().throwBatarang();
//                break;
            case Backspace:
                game.getBatman().punch();
            default:
                return;
        }

//        if (stroke.getCharacter() == 'w') {
//            game.throwBatarang();
//        }

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
//        drawBatarang();
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
        drawPosition(batman.getXcoor(), batman.getYcoor(), TextColor.ANSI.GREEN, 'b', true);

    }

    private void drawNinja() {
        for (Ninja ninja : game.getNinjas()) {
            drawPosition(ninja.getXcoor(), ninja.getYcoor(), TextColor.ANSI.RED, 'n', true);
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

    private void drawPosition(int x, int y, TextColor color, char c, boolean wide) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(x * 2, y + 1, String.valueOf(c));

        if (wide) {
            text.putString(x * 2 + 1, y + 1, String.valueOf(c));
        }
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
