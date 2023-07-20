package model;

// this is the Score class that represents the player's score
public class Score {

    // add fields to represent changing properties of Batman
    private String name;
    private int score;

    // EFFECTS: constructs a score with initial properties
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // EFFECTS: retrieves the name of the player
    public String getName() {
        return name;
    }

    // EFFECTS: retrieves the score of the player
    public int getScore() {
        return score;
    }
}
