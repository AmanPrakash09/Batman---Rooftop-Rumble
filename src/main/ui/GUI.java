package ui;

import model.Game;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

/*
 * Represents the main window in which the game is played.
 * CITATION: SpaceInvaders
 */
public class GUI extends JFrame {

    private static final int INTERVAL = 10;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private Game game;
    private GamePanel gp;
    private ScorePanel sp;
    private static final String JSON_STORE = "./data/game.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Boolean freezeGame;

    // effects: sets up window in which game will be played
    public GUI() {
        super("Batman - Rooftop Rumble");
        freezeGame = false;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        game = new Game(WIDTH, HEIGHT);
        gp = new GamePanel(game);
        sp = new ScorePanel(game);
        add(gp);
        add(sp, BorderLayout.NORTH);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
    }

    // EFFECTS:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!freezeGame) {
                    game.tick();
                    gp.repaint();
                    sp.update();
                }
            }
        });

        t.start();
    }

    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // MODIFIES: this, game
    // EFFECTS:  depending on which key is pressed, a corresponding action is carried out
    private class KeyHandler extends KeyAdapter {
        @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_A && game.isEnded()) {
                setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    AddScorePanel scoreboardDisplay = null;
                    try {
                        scoreboardDisplay = new AddScorePanel(game);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    scoreboardDisplay.setVisible(true);
                });
            }

            switch (key) {
                case KeyEvent.VK_KP_UP:
                case KeyEvent.VK_UP:
                    game.getBatman().moveUp();
                    break;
                case KeyEvent.VK_KP_DOWN:
                case KeyEvent.VK_DOWN:
                    game.getBatman().moveDown();
                    break;
                case KeyEvent.VK_KP_RIGHT:
                case KeyEvent.VK_RIGHT:
                    game.getBatman().moveRight();
                    break;
                case KeyEvent.VK_KP_LEFT:
                case KeyEvent.VK_LEFT:
                    game.getBatman().moveLeft();
                    break;
                case KeyEvent.VK_X:
                    game.getBatman().punch();
                    break;
                case KeyEvent.VK_Z:
                    game.throwBatarang();
                    break;
                case KeyEvent.VK_S:
                    saveGame();
                    break;
                case KeyEvent.VK_L:
                    loadGame();
                    break;
                default:
                    return;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: saves game to file
    private void saveGame() {
        freezeGame = true;
        int response = showPrompt("Are you sure you want to save the game?");
        if (response == JOptionPane.YES_OPTION) {
            try {
                jsonWriter.open();
                jsonWriter.write(game);
                jsonWriter.close();
//                System.out.println("Saved to " + JSON_STORE);
            } catch (FileNotFoundException e) {
//                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
        freezeGame = false;
    }

    // MODIFIES: this, game
    // EFFECTS: loads game from file
    private void loadGame() {
        freezeGame = true;
        int response = showPrompt("Are you sure you want to load the game?");
        if (response == JOptionPane.YES_OPTION) {
            try {
                game = jsonReader.read();
                gp.setGame(game);
                sp.setGame(game);
//                System.out.println("Loaded from " + JSON_STORE);
            } catch (IOException e) {
//                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
        freezeGame = false;
    }

    // MODIFIES: this
    // EFFECTS: shows a prompt to check if user is sure about saving/loading
    private int showPrompt(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Just Checking...",
                JOptionPane.YES_NO_OPTION);
    }
}
