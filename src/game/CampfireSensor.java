package game;

import city.cs.engine.Sensor;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import city.cs.engine.Shape;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class CampfireSensor extends Sensor implements SensorListener {
    private final Game game;
    private final Campfire campfire;
    public CampfireSensor(Campfire campfire, Shape shape, Game game){
        super(campfire,shape);
        this.campfire = campfire;
        this.addSensorListener(this);
        this.game = game;
    }

    /**
     * if player makes contact with campfire, then move to the next level
     */

    @Override
    public void beginContact(SensorEvent sensorEvent){
        if(sensorEvent.getContactBody() instanceof Protagonist){
            campfire.destroy();
            game.goToNextLevel();
        }
    }

    @Override
    public void endContact(SensorEvent sensorEvent){

    }
}