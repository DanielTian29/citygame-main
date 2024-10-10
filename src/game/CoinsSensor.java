package game;

import city.cs.engine.*;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class CoinsSensor extends Sensor implements SensorListener {
    private final Coins coins;
    private SoundClip coinSound;
    public CoinsSensor(Coins coins, Shape shape,SoundClip coinSound) {
        super(coins, shape);
        this.addSensorListener(this);
        this.coins = coins;
        this.coinSound = coinSound;
    }

    /**
     * if player touches the coin, then add to the score and remove the coin from the level
     */
    @Override
    public void beginContact(SensorEvent sensorEvent){
        if(sensorEvent.getContactBody() instanceof Protagonist){
            coins.destroy();
            coinSound.play();
            Protagonist.increaseScore();
        }
    }

    @Override
    public void endContact(SensorEvent sensorEvent){

    }
}
