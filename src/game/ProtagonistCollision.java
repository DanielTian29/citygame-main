package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class ProtagonistCollision implements CollisionListener {


    /**
     * if player collision happens with enemy then lose health
     */
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Enemy enemy){
            ((Protagonist) collisionEvent.getReportingBody()).loseHealth();
        }
    }
}

