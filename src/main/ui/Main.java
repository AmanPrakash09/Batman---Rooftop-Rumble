package ui;

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
