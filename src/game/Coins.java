package game;

import city.cs.engine.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class Coins extends DynamicBody implements StepListener {

    //initiate values
    private static final Shape coinShape = new BoxShape(2f,2f);
    private static final BodyImage coinImage = new BodyImage("data/coin.png", 2f);
    private static SoundClip coinsound;

    static {
        try {
            coinsound = new SoundClip("data/Audio/coinSound.wav");
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Add the image, set values and add listeners to character
    public Coins(World world) {
        super(world);
        this.setGravityScale(0);
        this.addImage(coinImage);
        world.addStepListener(this);
        Sensor sensor = new CoinsSensor(this,coinShape,coinsound);
    }

    @Override
    public void preStep(StepEvent stepEvent) {

    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }
}
