package game;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class Enemy extends Walker implements StepListener {
    private int minimumX = 0;
    private int maximumX = 0;
    private static Shape enemyShape;
    private static World world;
    private Game game;
    private int health = 1;
    private static BodyImage image = new BodyImage("data/toadWalking.gif", 5f);
    private static SoundClip enemySound;

    static {
        try {
            enemySound = new SoundClip("data/Audio/spider.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    public Enemy(World world, Game game) {
        super(world);
        this.world = world;
        this.game = game;
        if (world instanceof Level1) {
            image = new BodyImage("data/spiderWalking.gif", 5f);
            enemyShape = new BoxShape(3f, 1f);
        } else if (world instanceof Level2) {
            image = new BodyImage("data/toadWalking.gif", 5f);
            enemyShape = new BoxShape(1f, 2f);
        } else if (world instanceof Level3) {
            image = new BodyImage("data/bossMoving.gif", 15f);
            enemyShape = new BoxShape(2f, 4f);
            health = 10;
        }
        addImage(image);
        Fixture fixture = new GhostlyFixture(this, enemyShape);
        Sensor sensor = new EnemySensor(this, enemyShape, game, world);
        this.setGravityScale(0);
        world.addStepListener(this);
    }

    /**
     * update the noise the enemy makes based on what level is currently running
     */
    public void updateSound() {
            if (world instanceof Level1) {
                try {
                    enemySound = new SoundClip("data/Audio/spider.wav");
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    throw new RuntimeException(e);
                }
            } else if (world instanceof Level2) {
                try {
                    enemySound = new SoundClip("data/Audio/goblin.wav");
                    enemySound.setVolume(0.5);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    throw new RuntimeException(e);
                }

            } else if (world instanceof Level3) {
                try {
                    enemySound = new SoundClip("data/Audio/horror.wav");
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    /**
     * set range of enemy
     */
    public void setRange(int x, int platformWidth) {
        this.minimumX = x - platformWidth;
        this.maximumX = x + platformWidth;
    }

    /**
     * increments health by -1
     */
    public void takeDamage(){health--;}

    /**
     * returns enemies health
     */
    public int getHealth(){return health;}

    /**
     * plays sound that the enemy makes
     */
    public void playSound(){enemySound.play();}

    /**
     * when the enemy reaches the edge of a ledge, then make it turn around
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        if (!(world instanceof Level3)) {
            if (getPosition().x >= this.maximumX) {
                startWalking(-10);
                removeAllImages();
                addImage(image).flipHorizontal();
            }
            if (getPosition().x <= this.minimumX) {
                startWalking(10);
                removeAllImages();
                addImage(image);
            }
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }

    public Shape getShape(){
        return enemyShape;
    }
}
