package game;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class Camera implements StepListener{
    GameView view;
    Protagonist protagonist;
    public Camera(GameView view, Protagonist protagonist) {
        this.view = view;
        this.protagonist = protagonist;
    }

    /**
     * update camera so view is correctly initialised
     */

    public void updateCharacter(Protagonist c){
        protagonist = c;
    }
    @Override
    public void preStep(StepEvent stepEvent) {
        if (protagonist.getLevel() instanceof Level1 || protagonist.getLevel() instanceof Level2) {
            view.setCentre(new Vec2(protagonist.getPosition()));
            view.setZoom(10f);
        }else if(protagonist.getLevel() instanceof Level3) {
            view.setZoom(10f);
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }
}
