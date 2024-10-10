package game;

import city.cs.engine.*;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class Protagonist extends Walker implements StepListener{
    private static final Shape protagonistShape = new BoxShape(1f,2.5f);

    private static final BodyImage image =
            new BodyImage("data/IdleKnight1.png", 6f);

    private int points;
    private static float health = 0;
    private static int sprint = 0;
    private static int score = 0;
    private GameLevel level;
    private static int kills = 0;

    public Protagonist(GameLevel level) {
        super(level, protagonistShape);
        health = 5;
        points = 0;
        sprint = 5;
        SolidFixture fixture = new SolidFixture(this, protagonistShape);
        fixture.setFriction(0);
        this.addCollisionListener(new ProtagonistCollision());
        this.addImage(image);
        this.level = level;
    }

    /**
     * updates points
     */
    public void updatePoints(GameLevel level, float health, int score, int kills){
        points = points + ((int)health*50) + (score * 20) + (kills * 20) ;
        if (level instanceof Level3 && kills == 1){
            points = points + 1000;
        }
    }

    /**
     * returns points
     */
    public int getPoints(){
        return points;
    }

    /**
     * increments health by -0.5
     */
    public static void loseHealth(){
        health = health - 0.5f;
    }

    /**
     * returns health
     */
    public float getHealth(){
        return health;
    }

    /**
     * increments score by 1
     */
    public static void increaseScore(){
        score++;
    }

    /**
     * returns score
     */
    public int getScore(){return score;}

    /**
     * resets score
     */
    public void resetScore(){score = 0;}

    /**
     * increments kills by 1
     */
    public static void increaseKills(){kills++;}

    /**
     * resets kills
     */
    public void resetKills(){kills = 0;}

    /**
     * returns kills
     */
    public int getKills() {return kills;}

    /**
     * returns level
     */
    public GameLevel getLevel() {return level;}

    /**
     * increments sprint by -1
     */
    public void decreaseSprint(){sprint--;}

    /**
     * returns sprint
     */
    public int getSprint(){return sprint;}

    /**
     * increments sprint by 1
     */
    public static void increaseSprint(){sprint++;}

    @Override
    public void preStep(StepEvent stepEvent) {
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }
}
