package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

// this is the Game class that represents the whole game being played
public class Game {
    public static final int TICKS_PER_SECOND = 100;
    // add fields to represent changing properties of Game
    private final Batman batman = new Batman();
    private final Ground ground = new Ground();
    private final Roof roof = new Roof(5, 15, ground.getHeight());
    private final Roof roof1 = new Roof(10, 25, roof.getHeight());
    private final Roof roof2 = new Roof(15, 7, roof.getHeight());
    private final Set<Ninja> ninjas = new HashSet<>();
    private int maxX;
    private int maxY;
    private int health;
    private int score = 0;
    private boolean ended;

    // EFFECTS: constructs Game with initial properties
    public Game(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.health = 100;
        this.ended = false;

        Ninja ninja = new Ninja(2,18);
        Ninja ninja1 = new Ninja(10,18);
        Ninja ninja2 = new Ninja(15,18);
        Ninja ninja3 = new Ninja(17,18);
        ninjas.add(ninja);
        ninjas.add(ninja1);
        ninjas.add(ninja2);
        ninjas.add(ninja3);
    }

    // MODIFIES: this
    // EFFECTS: during each tick of the game, the game constantly checks for these methods/cases
    public void tick() {
        isBatmanOnSurface();

        batman.gravity();

        handleNinja();

        if (ninjas.isEmpty() || health == 0) {
            ended = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: checks if Batman has come across any ninjas. If so, then damage is received.
    //          checks if Batman has punched a ninja at the appropriate conditions. If so, ninja is removed
    //          and score is incremented.
    public void handleNinja() {
        for (Ninja ninja : ninjas) {
            if (ninja.getXcoor() == batman.getXcoor() && ninja.getYcoor() == batman.getYcoor()) {
                health -= 1;
            }
        }

        Ninja defeatedNinja = ninjas.stream()
                .filter(batman::hasPunched)
                .findFirst()
                .orElse(null);

        if (defeatedNinja == null) {
            return;
        }

        ninjas.remove(defeatedNinja);
        score++;
//        snake.grow();
    }

    // MODIFIES: this, Batman
    // EFFECTS: checks if Batman is on any surface (roof/ground) or at the right edge of the screen.
    //          If Batman's coordinates are above the ground, he is put on the ground.
    //          If Batman's coordinates are above a roof, he is put on a roof.
    //          If Batman's coordinates are neither on a roof nor on the ground, he is falling.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void isBatmanOnSurface() {
        int bx = batman.getXcoor();
        int by = batman.getYcoor();

        int gh = ground.getHeight();

        int rx = roof.getXcoor();
        int rw = roof.getWidth();
        int rh = roof.getHeight();
        int r1x = roof1.getXcoor();
        int r1w = roof1.getWidth();
        int r1h = roof1.getHeight();
        int r2x = roof2.getXcoor();
        int r2w = roof2.getWidth();
        int r2h = roof2.getHeight();

        if (by + 2 == gh) {
            batman.putOnGround();
        }

        if ((by + 2 == rh && bx >= rx && bx <= rx + rw)
                || (by + 2 == r1h && bx >= r1x && bx <= r1x + r1w) || (by + 2 == r2h && bx >= r2x && bx <= r2x + r2w)) {
            batman.putOnRoof();
        }

        if (batman.isOnRoof()) {
            if ((bx < rx || bx > rx + rw || by + 2 != rh)
                    && (bx < r1x || bx > r1x + r1w || by + 2 != r1h) && (bx < r2x || bx > r2x + r2w || by + 2 != r2h)) {
                batman.fall();
            }
        }

        if (bx == maxX) {
            batman.putAtRightEdge();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets health to a desired value
    public void setHealth(int health) {
        this.health = health;
    }

    // MODIFIES: this
    // EFFECTS: clears Set of ninjas established in Game
    public void emptyNinjas() {
        this.ninjas.clear();
    }

    // EFFECTS: retrieves Batman established in Game
    public Batman getBatman() {
        return batman;
    }

    // EFFECTS: retrieves Ground established in Game
    public Ground getGround() {
        return ground;
    }

    // EFFECTS: retrieves Roof established in Game
    public Roof getRoof() {
        return roof;
    }

    // EFFECTS: retrieves Roof1 established in Game
    public Roof getRoof1() {
        return roof1;
    }

    // EFFECTS: retrieves Roof2 established in Game
    public Roof getRoof2() {
        return roof2;
    }

    // EFFECTS: retrieves the maxX value given to Game
    public int getMaxX() {
        return maxX;
    }

    // EFFECTS: retrieves the maxY value given to Game
    public int getMaxY() {
        return maxY;
    }

    // EFFECTS: retrieves the Set of ninjas established in Game
    public Set<Ninja> getNinjas() {
        return ninjas;
    }

    // EFFECTS: retrieves health
    public int getHealth() {
        return health;
    }

    // EFFECTS: retrieves score
    public int getScore() {
        return score;
    }

    // EFFECTS: shows if the Game has ended or not
    public boolean isEnded() {
        return ended;
    }
}
