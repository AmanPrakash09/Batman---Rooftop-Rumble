package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// this is the Game class that represents the whole game being played
public class Game {
    public static final int TICKS_PER_SECOND = 10;

    public static final int ROOF_STARTX = 5;
    public static final int ROOF_WIDTH = 15;

    public static final int ROOF1_STARTX = 10;
    public static final int ROOF1_WIDTH = 25;

    public static final int ROOF2_STARTX = 15;
    public static final int ROOF2_WIDTH = 7;

    // add fields to represent changing properties of Game
    private Batman batman = new Batman();
    private final Ground ground = new Ground();
    private final Roof roof = new Roof(ROOF_STARTX, ROOF_WIDTH, ground.getHeight());
    private final Roof roof1 = new Roof(ROOF1_STARTX, ROOF1_WIDTH, roof.getHeight());
    private final Roof roof2 = new Roof(ROOF2_STARTX, ROOF2_WIDTH, roof.getHeight());
    private final Set<Ninja> ninjas = new HashSet<>();
    private List<Batarang> batarangs;
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

        batarangs = new ArrayList<Batarang>();

        Ninja ninja = new Ninja(2,18, 1, 0, maxX);
        Ninja ninja1 = new Ninja(10,18, -1, 0, maxX);
        Ninja ninja2 = new Ninja(15,18, 1, 0, maxX);
        Ninja ninja3 = new Ninja(17,18, -1, 0, maxX);

        int ninjaRoof1Y = maxY - 2 - Roof.SPACE - 2;

//        System.out.println(ninjaRoof1Y);

        Ninja ninja4 = new Ninja(7,ninjaRoof1Y, 1, roof.getXcoor(), roof.getXcoor() + roof.getWidth());
        Ninja ninja5 = new Ninja(9,ninjaRoof1Y, 0, roof.getXcoor(), roof.getXcoor() + roof.getWidth());
        Ninja ninja6 = new Ninja(13,ninjaRoof1Y, 0, roof.getXcoor(), roof.getXcoor() + roof.getWidth());
        Ninja ninja7 = new Ninja(17,ninjaRoof1Y, -1, roof.getXcoor(), roof.getXcoor() + roof.getWidth());

//        Ninja ninja8 = new Ninja(2,18, 1, 0, maxX);
//        Ninja ninja9 = new Ninja(10,18, -1, 0, maxX);
//        Ninja ninja10 = new Ninja(15,18, 1, 0, maxX);
//        Ninja ninja11 = new Ninja(17,18, -1, 0, maxX);
        ninjas.add(ninja);
        ninjas.add(ninja1);
        ninjas.add(ninja2);
        ninjas.add(ninja3);

        ninjas.add(ninja4);
        ninjas.add(ninja5);
        ninjas.add(ninja6);
        ninjas.add(ninja7);
    }

    // MODIFIES: this
    // EFFECTS: during each tick of the game, the game constantly checks for these methods/cases
    public void tick() {
        isBatmanOnSurface();

        batman.gravity();
//        batman.throwBatarang();

        handleNinja();
        moveNinja();

        moveBatarangs();
        lostBatarangs();

        batarangAttack();

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
                if (batman.isFacingRight()) {
                    batman.setXcoor(batman.getXcoor() - 2);
                } else {
                    batman.setXcoor(batman.getXcoor() + 2);
                }
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
        score += 10;
//        snake.grow();
    }

    // modifies: this
    // effects: moves the missiles
    public void moveNinja() {
//        int count = 0;
//        int dir = 1;
        for (Ninja next : ninjas) {
////            next.move(getBatman().isFacingRight());
//            if (count % 2 == 0) {
//                dir *= -1;
//            }
//            next.move(dir);
//            count++;
//            if (next.getXcoor() > maxX || next.getXcoor() < 0) {
//                next.switchDir();
//            }
            next.move();
        }
    }

    public void lostBatarangs() {
        List<Batarang> batarangsToRemove = new ArrayList<Batarang>();

        for (Batarang next : batarangs) {
            if (next.getXcoor() < 0 || next.getXcoor() > maxX) {
                batarangsToRemove.add(next);
            }
        }

        batarangs.removeAll(batarangsToRemove);
    }

    // Checks for collisions between a ninja and a batarang
    // modifies: this
    // effects:  removes any ninja that has been hit with a batarang
    //           and removes corresponding batarang from game
    public void batarangAttack() {
        List<Ninja> ninjasToRemove = new ArrayList<Ninja>();
        List<Batarang> batarangsToRemove = new ArrayList<Batarang>();

        for (Ninja target : ninjas) {
            if (checkNinjaHit(target, batarangsToRemove)) {
                ninjasToRemove.add(target);
            }
        }

        ninjasToRemove.forEach(ninjas::remove);
        batarangs.removeAll(batarangsToRemove);
    }

    // Exercise:  fill in the documentation for this method
    public boolean checkNinjaHit(Ninja target, List<Batarang> batarangsToRemove) {
        for (Batarang next : batarangs) {
            if (target.getXcoor() == next.getXcoor() && target.getYcoor() == next.getYcoor()) {
                batarangsToRemove.add(next);
                score += 5;
                return true;
            }
        }

        return false;
    }

    // updates the missiles
    // modifies: this
    // effects: moves the missiles
    public void moveBatarangs() {
        for (Batarang next : batarangs) {
//            next.move(getBatman().isFacingRight());
            next.move();
        }
    }

    // Fires a missile
    // modifies: this
    // effects:  fires a missile if max number of missiles in play has
    //           not been exceeded, otherwise silently returns
    public void throwBatarang() {
        Batarang b = new Batarang(batman.getXcoor(), batman.getYcoor(), batman.isFacingRight());
        batarangs.add(b);
//        System.out.println("fire");
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

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
//        json.put("name", name);
        json.put("ninjas", ninjasToJson());
        json.put("batarangs", batarangsToJson());
        json.put("batman", batmanToJson());
        json.put("score", getScore());
        json.put("health", getHealth());
        return json;
    }

    // EFFECTS: returns ninjas in this game as a JSON array
    private JSONArray ninjasToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Ninja n : ninjas) {
            jsonArray.put(n.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns batarangs in this game as a JSON array
    private JSONArray batarangsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Batarang b : batarangs) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns batarangs in this game as a JSON array
    private JSONArray batmanToJson() {
        JSONArray jsonArray = new JSONArray();

        Batman b = getBatman();
        jsonArray.put(b.toJson());

        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: ends game
    public void endGame() {
        this.ended = true;
    }

    // MODIFIES: this
    // EFFECTS: sets health to a desired value
    public void setHealth(int health) {
        this.health = health;
    }

    // MODIFIES: this
    // EFFECTS: sets score to a desired value
    public void setScore(int score) {
        this.score = score;
    }

    // MODIFIES: this
    // EFFECTS: sets Batman established in Game
    public void setBatman(Batman batman) {
        this.batman = batman;
    }

    // MODIFIES: this
    // EFFECTS: clears Set of ninjas established in Game
    public void emptyNinjas() {
        this.ninjas.clear();
    }

    // MODIFIES: this
    // EFFECTS: clears list of batarangs established in Game
    public void emptyBatarangs() {
        this.batarangs.clear();
    }

    // MODIFIES: this
    // EFFECTS: adds ninja to ninjas established in Game
    public void addNinja(Ninja n) {
        this.ninjas.add(n);
    }

    // MODIFIES: this
    // EFFECTS: adds batarang to batarangs established in Game
    public void addBatarang(Batarang b) {
        this.batarangs.add(b);
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

    public List<Batarang> getBatarangs() {
        return batarangs;
    }

    // EFFECTS: shows if the Game has ended or not
    public boolean isEnded() {
        return ended;
    }
}
