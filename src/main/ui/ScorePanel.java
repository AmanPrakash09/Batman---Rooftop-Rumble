package ui;

import model.Game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Represents the panel in which the health and score is displayed.
 * CITATION: SpaceInvaders
 */

public class ScorePanel extends JPanel {
    private static final String HEALTH = "HEALTH: ";
    private static final String SCORE = "SCORE: ";
    private static final int LBL_WIDTH = 200;
    private static final int LBL_HEIGHT = 30;
    private Game game;
    private JLabel healthLbl;
    private JLabel scoreLbl;

    // EFFECTS: sets the background colour and draws the initial labels;
    //          updates this with the game whose score and health is to be displayed
    public ScorePanel(Game g) {
        game = g;
        setBackground(new Color(180, 180, 180));
        healthLbl = new JLabel(HEALTH + game.getHealth());
        healthLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        scoreLbl = new JLabel(SCORE + game.getScore());
        scoreLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        add(healthLbl);
        add(Box.createHorizontalStrut(10));
        add(scoreLbl);
    }

    // MODIFIES: game
    // EFFECTS:  sets the game to the desired game
    public void setGame(Game game) {
        this.game = game;
    }

    // MODIFIES: this
    // EFFECTS:  updates the score and health of the user
    public void update() {
        healthLbl.setText(HEALTH + game.getHealth());
        scoreLbl.setText(SCORE + game.getScore());
        repaint();
    }
}
