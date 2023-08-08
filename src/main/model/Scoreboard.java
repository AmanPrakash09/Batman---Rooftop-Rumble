package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// the Scoreboard class that represents a list of scores
public class Scoreboard {

    // list of different user's names
    List<String> userList;
    // list of their respective scores
    List<Integer> scoreList;
    private int duplicateSkipper = 1;

    // EFFECTS: constructs a Scoreboard with empty lists
    public Scoreboard() {
        this.userList = new ArrayList<>();
        this.scoreList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if user has already played the game, their score gets updated.
    //          Otherwise, their name and score is added.
    public void addScore(Score score) throws IOException {
        if (userList.contains(score.getName())) {
            int index = userList.indexOf(score.getName());
            scoreList.set(index, score.getScore());
        } else {
            userList.add(score.getName());
            scoreList.add(score.getScore());
        }
        sort();
    }

    // MODIFIES: this
    // EFFECTS: removes score and name of the Score from scoreboard
    public void removeScore(Score score) throws IOException {
        if (userList.contains(score.getName())) {
            userList.remove(score.getName());
            int index = scoreList.indexOf(score.getScore());
            scoreList.remove(index);
        }
        sort();
    }

    // MODIFIES: this
    // EFFECTS: sorts the list of scores in decending order along with their respective users
    public void sort() {
        List<Integer> originalScores = new ArrayList<>(scoreList);
        List<String> sortedUsers = new ArrayList<>();

        Collections.sort(scoreList, Collections.reverseOrder());

        for (int score : scoreList) {
            int index = originalScores.indexOf(score);
            sortedUsers.add(userList.get(index));
            originalScores.remove(index);
            userList.remove(index);
        }

        userList = sortedUsers;
    }

    // EFFECTS: retrieves the list of scores
    public List<Integer> getScoreList() {
        return scoreList;
    }

    // EFFECTS: retrieves the list of names
    public List<String> getUserList() {
        return userList;
    }

    // EFFECTS: retrieves the list of scores
    public void setScoreList(JSONArray sl) {
        List<Integer> ret = new ArrayList<>();

        for (int i = 0; i < sl.length(); i++) {
            Integer item = sl.getInt(i);
            ret.add(item);
        }

        scoreList = ret;
    }

    // EFFECTS: retrieves the list of names
    public void setUserList(JSONArray ul) {
        List<String> ret = new ArrayList<>();

        for (int i = 0; i < ul.length(); i++) {
            String item = ul.getString(i);
            ret.add(item);
        }
        userList = ret;
    }

    // EFFECTS: retrieves the size of scoreboard
    public int getSize() {
        return scoreList.size();
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("userList", userList);
        json.put("scoreList", scoreList);
        return json;
    }

}
