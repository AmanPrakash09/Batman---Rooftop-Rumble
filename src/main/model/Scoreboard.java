package model;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    List<String> userList;
    List<Integer> scoreList;

    public Scoreboard() {
        this.userList = new ArrayList<>();
        this.scoreList = new ArrayList<>();
    }

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

    public List<Integer> getScoreList() {
        return scoreList;
    }

    public List<String> getUserList() {
        return userList;
    }

    public int getSize() {
        return scoreList.size();
    }

}
