package ui;

import model.Event;
import model.EventLog;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

// This is where the game is launched.
public class Main {
    public static void main(String[] args) throws Exception {
        // create and start the game
//        TerminalGame gameHandler = new TerminalGame();
//
//        gameHandler.start();
        StartingScreen start = new StartingScreen();
        start.setVisible(true); // Show the StartingScreen
    }
}
