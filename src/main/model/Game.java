package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Game {
    public static final int TICKS_PER_SECOND = 100;
    private final Batman batman = new Batman();
    private final Ground ground = new Ground();
    private final Roof roof = new Roof(5, 15, ground.getHeight());
    private final Roof roof1 = new Roof(10, 25, roof.getHeight());
    private final Roof roof2 = new Roof(15, 7, roof.getHeight());
    private final Set<Ninja> ninjas = new HashSet<>();
//    private Batarang batarang;
    private int maxX;
    private int maxY;
    private int health;
    private int score = 0;
    private boolean ended;

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

    /**
     * Progresses the game state, moving Batman
     */
    public void tick() {
        isBatmanOnSurface();

        batman.gravity();

//        System.out.println(batman.isPunching());
//        System.out.println(batman.getXcoor());

//        throwBatarang();

//        batman.moveRight();
//        batman.moveLeft();
//        batman.moveUp();
//        batman.moveDown();
        handleNinja();

        if (ninjas.isEmpty() || health == 0) {
            ended = true;
//            spawnNewNinja();
        }
    }

    /**
     * Spawns a new ninja into a valid
     * position in the game
     */
//    public void spawnNewNinja() {
//        Ninja ninja = new Ninja(7,7);
//        ninjas.add(ninja);
//    }

    /**
     * Checks for ninja that Batman has defeated,
     * grows the snake and increases score if food is found
     */
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

    /**
     * Checks if Batman has collided with a surface
     */
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

//        System.out.println(this.batman.getYcoor());
//        System.out.println(this.ground.getHeight());
//        System.out.print(this.batman.getXcoor());
//        System.out.print(" ");
//        System.out.print(this.roof.getXcoor());
//        System.out.println(" ");
//        System.out.print(this.roof.getXcoor() + this.roof.getWidth());
//        System.out.println(" ");

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

//    // Throws Batarang
//    // modifies: this
//    // effects:  throws Batarang from Batman's coordinates
//    public void throwBatarang() {
//        batarang = new Batarang(batman.getXcoor(), batman.getYcoor());
//        batarang.setXcoor(batman.getXcoor());
//        batarang.setYcoor(batman.getYcoor());
////        while (batarang.getXcoor() >= 0 && batarang.getXcoor() <= maxX) {
////            batarang.move(batman.isFacingRight());
////        }
//        int target = 0;
//        while (target < 5) {
//            batarang.move(batman.isFacingRight());;
////            delay(100);
//            target++;
//        }
////        batarang.setValid(false);
//    }

//    private void delay(int milliseconds) {
//        try {
//            Thread.sleep(milliseconds);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public void addScoreToScoreboard() throws IOException {
        Scanner name = new Scanner(System.in);
        String playerName;

        // Enter name and press Enter
        System.out.println("Enter Name");
        playerName = name.nextLine();

        String nameAndScore = playerName.concat(": ").concat(Integer.toString(getScore()));

//        System.out.println(playerName + ": " + getScore());
        System.out.println(nameAndScore);

        File file = new File("./data/scoreboard.txt");
        FileWriter fr = new FileWriter(file, true);
        fr.write(nameAndScore + "\n");
        fr.close();

//        File file = new File(".");
//        for (String fileNames : file.list()) {
//            System.out.println(fileNames);
//        }
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void emptyNinjas() {
        this.ninjas.clear();
    }

    public Batman getBatman() {
        return batman;
    }

    public Ground getGround() {
        return ground;
    }

    public Roof getRoof() {
        return roof;
    }

    public Roof getRoof1() {
        return roof1;
    }

    public Roof getRoof2() {
        return roof2;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

//    public Batarang getBatarang() {
//        return batarang;
//    }

    public Set<Ninja> getNinjas() {
        return ninjas;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public boolean isEnded() {
        return ended;
    }
}
