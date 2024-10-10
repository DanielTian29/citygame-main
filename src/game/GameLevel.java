package game;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public abstract class GameLevel extends World {
    private final Protagonist protagonist;
    public GameLevel(){
        protagonist = new Protagonist(this);
        protagonist.setPosition(new Vec2(0,3));
        protagonist.setLinearVelocity(new Vec2(0,0));
        protagonist.setGravityScale(12f);
    }

    /**
     * return player class
     */
    public Protagonist getProtagonist(){
        return protagonist;
    }

    /**
     * return enemy
     */
    public abstract Enemy getEnemy();

    /**
     * return background for level
     */
    public abstract String getBg();

    /**
     * start music for the level
     */
    public abstract void startMusic();

    /**
     * stop music for the level
     */
    public abstract void stopMusic();
}