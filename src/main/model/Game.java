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

    public static final int ROOF_STARTX = 50;
    public static final int ROOF_WIDTH = 150;

    public static final int ROOF1_STARTX = 100;
    public static final int ROOF1_WIDTH = 250;

    public static final int ROOF2_STARTX = 150;
    public static final int ROOF2_WIDTH = 70;

    // add fields to represent changing properties of Game
    private Batman batman = new Batman();
    private final Ground ground = new Ground();
    private final Roof roof = new Roof(ROOF_STARTX, ROOF_WIDTH, ground.getHeight());
    private final Roof roof1 = new Roof(ROOF1_STARTX, ROOF1_WIDTH, roof.getHeight());
    private final Roof roof2 = new Roof(ROOF2_STARTX, ROOF2_WIDTH, roof1.getHeight());
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

        int ninjaGroundY = maxY - 100 - Ninja.SIZE_Y;

        Ninja ninja = new Ninja(20,ninjaGroundY, 1, 0, maxX);
        Ninja ninja1 = new Ninja(100,ninjaGroundY, -1, 0, maxX);
        Ninja ninja2 = new Ninja(150,ninjaGroundY, 1, 0, maxX);
        Ninja ninja3 = new Ninja(170,ninjaGroundY, -1, 0, maxX);

        int ninjaRoof1Y = maxY - 100 - Roof.SPACE - Ninja.SIZE_Y;

        Ninja ninja4 = new Ninja(70,ninjaRoof1Y, 1, roof.getXcoor(), roof.getXcoor() + roof.getWidth());
        Ninja ninja5 = new Ninja(90,ninjaRoof1Y, 0, roof.getXcoor(), roof.getXcoor() + roof.getWidth());
        Ninja ninja6 = new Ninja(130,ninjaRoof1Y, 0, roof.getXcoor(), roof.getXcoor() + roof.getWidth());
        Ninja ninja7 = new Ninja(170,ninjaRoof1Y, -1, roof.getXcoor(), roof.getXcoor() + roof.getWidth());

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
//            if (ninja.getXcoor() == batman.getXcoor() && ninja.getYcoor() == batman.getYcoor()) {
            if (ninja.attackBatman(getBatman())) {
                if (batman.isFacingRight()) {
                    batman.setXcoor(batman.getXcoor() - 20);
                } else {
                    batman.setXcoor(batman.getXcoor() + 20);
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
    }

    // MODIFIES: this
    // EFFECTS: moves the ninjas
    public void moveNinja() {
        for (Ninja next : ninjas) {
            next.move();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes any batarangs that have moved out of the screen
    public void lostBatarangs() {
        List<Batarang> batarangsToRemove = new ArrayList<Batarang>();

        for (Batarang next : batarangs) {
            if (next.getXcoor() < 0 || next.getXcoor() > maxX) {
                batarangsToRemove.add(next);
            }
        }

        batarangs.removeAll(batarangsToRemove);
    }

    // CITATION: framework of this method was taken from SpaceInvaders
    // MODIFIES: this
    // EFFECTS:  removes any ninja that has been hit with a batarang
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

    // CITATION: framework of this method was taken from SpaceInvaders
    // MODIFIES: this
    // EFFECTS:  helper method for batarangAttack.
    //           checks if batarang's coordinates matches ninja's coordinates.
    //           if so, adds batarang to remove list
    public boolean checkNinjaHit(Ninja target, List<Batarang> batarangsToRemove) {
        for (Batarang next : batarangs) {
//            if (target.getXcoor() == next.getXcoor() && target.getYcoor() == next.getYcoor()) {
            if (target.collidedWith(next)) {
                batarangsToRemove.add(next);
                score += 5;
                return true;
            }
        }

        return false;
    }

    // MODIFIES: this
    // EFFECTS: moves the batarangs
    public void moveBatarangs() {
        for (Batarang next : batarangs) {
//            next.move(getBatman().isFacingRight());
            next.move();
        }
    }

    // modifies: this
    // effects:  throws a batarang from the coordinates of Batman
    public void throwBatarang() {
        Batarang b = new Batarang(batman.getXcoor(), batman.getYcoor(), batman.isFacingRight());
        batarangs.add(b);
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

        if (by + Batman.SIZE_Y == gh) {
            batman.putOnGround();
        }

        if ((by + Batman.SIZE_Y == rh && bx >= rx && bx <= rx + rw)
                || (by + Batman.SIZE_Y == r1h && bx >= r1x && bx <= r1x + r1w)
                || (by + Batman.SIZE_Y == r2h && bx >= r2x && bx <= r2x + r2w)) {
            batman.putOnRoof();
        }

        if (batman.isOnRoof()) {
            if ((bx < rx || bx > rx + rw || by + Batman.SIZE_Y != rh)
                    && (bx < r1x || bx > r1x + r1w || by + Batman.SIZE_Y != r1h)
                    && (bx < r2x || bx > r2x + r2w || by + Batman.SIZE_Y != r2h)) {
                batman.fall();
            }
        }

        if (bx == maxX) {
            batman.putAtRightEdge();
        }
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
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

    // EFFECTS: returns batman in this game as a JSON array
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

    // EFFECTS: retrieves list of Batarangs
    public List<Batarang> getBatarangs() {
        return batarangs;
    }

    // EFFECTS: shows if the Game has ended or not
    public boolean isEnded() {
        return ended;
    }
}
