package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class Level3 extends GameLevel{
    private final Game game;

    private static Enemy enemy;
    private static final BodyImage PlatformImage0 =
            new BodyImage("data/BossPlatform/Platform20.png", 2f); // used to be platform 7 4.77f
    private static final BodyImage PlatformImage1 =
            new BodyImage("data/BossPlatform/Platform8.png", 2f); // used to be platform 8 4.83f
    private static SoundClip levelMusic;
    static {
        try {
            levelMusic = new SoundClip("data/Audio/level3Music.wav");
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Level3(Game game){
        super();
        this.game = game;
        getProtagonist().setPosition(new Vec2(0, -26));

        Shape largePlatform = new BoxShape(20f, 1f);
        Shape smallPlatform = new BoxShape(8f,1f);

        StaticBody platform1 = new StaticBody(this, largePlatform);
        platform1.setPosition(new Vec2(0, -28));

        StaticBody platform2 = new StaticBody(this, smallPlatform);
        platform2.setPosition(new Vec2(30, -10));

        StaticBody platform3 = new StaticBody(this, smallPlatform);
        platform3.setPosition(new Vec2(-30, -10));

        platform1.addImage(PlatformImage0);
        platform2.addImage(PlatformImage1);
        platform3.addImage(PlatformImage1);

        enemy = new Enemy(this, game);
        enemy.setPosition(new Vec2(0, 0));
    }

    /**
     * updates boss, so it will face and move in the correct direction
     */
    public static void updateEnemy(float bossAngle, float forceX, float forceY){
        enemy.setAngle(bossAngle);
        enemy.setLinearVelocity(new Vec2(forceX,forceY));
    }

    /**
     * starts music
     */
    @Override
    public void startMusic() {
        levelMusic.loop();
    }

    /**
     * stops music
     */
    @Override
    public void stopMusic() {
        levelMusic.stop();
    }

    /**
     * returns enemy
     */
    @Override
    public Enemy getEnemy(){return enemy;}

    /**
     * returns background
     */
    @Override
    public String getBg() {return "data/bossBackground.png";}
}
