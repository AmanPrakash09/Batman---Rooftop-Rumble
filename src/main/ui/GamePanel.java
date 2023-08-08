package ui;

import model.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.*;

/*
 * The panel in which the game is rendered.
 * CITATION: SpaceInvaders
 */
public class GamePanel extends JPanel {

    private static final String OVER = "Game Over!";
    private static final String ADD_SCORE = "Press A to add your score!";
    public static final Color BATMAN_COLOR = new Color(250, 128, 20);
    public static final Color NINJA_COLOR = new Color(10, 50, 188);
    public static final Color BATARANG_COLOR = new Color(128, 50, 20);
    public static final Color GROUND_COLOR = new Color(0, 0, 0);
    public static final Color ROOF_COLOR = new Color(150, 150, 150);
    private Game game;

    // Constructs a game panel
    // EFFECTS:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(Game g) {
        setPreferredSize(new Dimension(g.getMaxX(), g.getMaxY()));
        setBackground(Color.GRAY);
        this.game = g;
    }

    // EFFECTS: renders game
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

        if (game.isEnded()) {
            gameOver(g);
        }
    }

    // MODIFIES: game
    // EFFECTS:  draws the game onto game
    private void drawGame(Graphics g) {

        if (game.isEnded()) {
            return;
        }

        drawBatman(g);
        drawGround(g);
        drawRoofs(g);
        drawBatarangs(g);
        drawNinjas(g);
    }

    // MODIFIES: game
    // EFFECTS:  draws Batman onto game
    private void drawBatman(Graphics g) {
        Batman b = game.getBatman();
        Color savedCol = g.getColor();
        g.setColor(BATMAN_COLOR);
        g.fillRect(b.getXcoor() - Batman.SIZE_X, b.getYcoor() - Batman.SIZE_Y, Batman.SIZE_X * 2,
                Batman.SIZE_Y * 2);
        g.setColor(savedCol);
    }

    // MODIFIES: game
    // EFFECTS:  draws the Ninjas onto game
    private void drawNinjas(Graphics g) {
        for (Ninja next : game.getNinjas()) {
            drawNinja(g, next);
        }
    }

    // MODIFIES: game
    // EFFECTS:  draws the Ninja i onto game
    private void drawNinja(Graphics g, Ninja n) {
        Color savedCol = g.getColor();
        g.setColor(NINJA_COLOR);
        g.fillOval(n.getXcoor() - Ninja.SIZE_X, n.getYcoor() - Ninja.SIZE_Y, Ninja.SIZE_X * 2,
                Ninja.SIZE_Y * 2);
        g.setColor(savedCol);
    }

    // MODIFIES: game
    // EFFECTS:  draws the Batarangs onto game
    private void drawBatarangs(Graphics g) {
        for (Batarang next : game.getBatarangs()) {
            drawBatarang(g, next);
        }
    }

    // MODIFIES: game
    // EFFECTS:  draws the Batarang b onto game
    private void drawBatarang(Graphics g, Batarang b) {
        Color savedCol = g.getColor();
        g.setColor(BATARANG_COLOR);
        g.fillOval(b.getXcoor() - Batarang.SIZE_X, b.getYcoor() - Batarang.SIZE_Y, Batarang.SIZE_X, Batarang.SIZE_Y);
        g.setColor(savedCol);
    }

    // MODIFIES: game
    // EFFECTS:  draws the Ground onto game
    private void drawGround(Graphics g) {
        Ground ground = game.getGround();
        ground.setWidth(GUI.WIDTH);
        ground.setHeight(GUI.HEIGHT - 100);
        Color savedCol = g.getColor();
        g.setColor(GROUND_COLOR);
        g.fillRect(0, GUI.HEIGHT - 100, GUI.WIDTH, 20);
        g.setColor(savedCol);
    }

    // MODIFIES: game
    // EFFECTS:  draws the Roof(s) onto game
    private void drawRoofs(Graphics g) {
        Roof r = game.getRoof();
        r.setHeight(game.getGround().getHeight());
        Color savedCol = g.getColor();
        g.setColor(ROOF_COLOR);
        g.fillRect(Game.ROOF_STARTX, game.getRoof().getHeight(), Game.ROOF_WIDTH, 20);
        g.setColor(savedCol);

        Roof r1 = game.getRoof1();
        r1.setHeight(game.getRoof().getHeight());
        Color savedCol1 = g.getColor();
        g.setColor(ROOF_COLOR);
        g.fillRect(Game.ROOF1_STARTX, game.getRoof1().getHeight(), Game.ROOF1_WIDTH, 20);
        g.setColor(savedCol1);

        Roof r2 = game.getRoof2();
        r2.setHeight(game.getRoof1().getHeight());
        Color savedCol2 = g.getColor();
        g.setColor(ROOF_COLOR);
        g.fillRect(Game.ROOF2_STARTX, game.getRoof2().getHeight(), Game.ROOF2_WIDTH, 20);
        g.setColor(savedCol2);
    }

    // MODIFIES: game
    // EFFECTS:  draws "game over" and instructions to add the user's score
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, game.getMaxY() / 2);
        centreString(ADD_SCORE, g, fm, game.getMaxY() / 2 + 50);
        g.setColor(saved);
    }

    // MODIFIES: game
    // EFFECTS:  centres the string str horizontally onto g at vertical position yPos
    private void centreString(String str, Graphics g, FontMetrics fm, int y) {
        int width = fm.stringWidth(str);
        g.drawString(str, (game.getMaxX() - width) / 2, y);
    }

    // MODIFIES: game
    // EFFECTS:  sets the game to the desired game
    public void setGame(Game game) {
        this.game = game;
    }
}
