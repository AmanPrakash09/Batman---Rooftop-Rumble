package model;

import java.util.ArrayList;
import java.util.List;

// the Scoreboard class that represents a list of scores
public class Scoreboard {

    // list of different user's names
    List<String> userList;
    // list of their respective scores
    List<Integer> scoreList;

    // EFFECTS: constructs a Scoreboard with empty lists
    public Scoreboard() {
        this.userList = new ArrayList<>();
        this.scoreList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if user has already played the game, their score gets updated.
    //          Otherwise, their name and score is added.
    public void addScore(Score score) {
        if (userList.contains(score.getName())) {
            int index = userList.indexOf(score.getName());
//            scoreList.add(score.getScore());
            scoreList.set(index, score.getScore());
        } else {
            userList.add(score.getName());
            scoreList.add(score.getScore());
        }
    }

    // EFFECTS: retrieves the list of scores
    public List<Integer> getScoreList() {
        return scoreList;
    }

    // EFFECTS: retrieves the list of names
    public List<String> getUserList() {
        return userList;
    }

    // EFFECTS: retrieves the size of scoreboard
    public int getSize() {
        return scoreList.size();
    }

}
