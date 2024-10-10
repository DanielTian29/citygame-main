package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class Campfire extends StaticBody {
    private static final Shape campFireshape = new CircleShape(1.5f);
    private static final BodyImage campFireImage = new BodyImage("data/campfire.gif", 5f);

    public Campfire(World world, float x, float y, Game game){
        super(world);
        this.addImage(campFireImage);
        this.setPosition(new Vec2(x,y));

        Sensor sensor = new CampfireSensor(this, campFireshape, game);
    }
}

