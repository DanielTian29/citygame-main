package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class MagicProjectileCollision implements CollisionListener {


    /**
     * if the projectile touches anything else then the protagonist, it'll get destroyed
     */
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (!(collisionEvent.getOtherBody() instanceof Protagonist protagonist)) {
            collisionEvent.getReportingBody().destroy();
        }
    }
}
