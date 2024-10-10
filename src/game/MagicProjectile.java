package game;

import city.cs.engine.BodyImage;
import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class MagicProjectile extends DynamicBody {

    //set up the shape, position, image and listener of the magic projectile
    public MagicProjectile(World w, Protagonist protagonist, Shape shape) {
        super(w, shape);
        this.setPosition(new Vec2(protagonist.getPosition().x+1f,protagonist.getPosition().y-0.5f));
        this.setLinearVelocity(new Vec2(50,0));
        this.addImage(new BodyImage("data/magicProjectile.png"));
        this.setGravityScale(0f);
        this.addCollisionListener(new MagicProjectileCollision());
    }
}
