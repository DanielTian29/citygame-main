package game;

import javax.swing.*;
import java.awt.event.*;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class PlayerController implements KeyListener {

    //sets up values
    private Protagonist protagonist;
    private static final BodyImage image =
            new BodyImage("data/runningKnight.gif", 6f);
    private static final BodyImage idle =
            new BodyImage("data/IdleKnight1.png", 6f);
    private static boolean facingLeft = false;

    public PlayerController(Protagonist protagonist) {
        this.protagonist = protagonist;
    }

    public void updateCharacter(Protagonist c){
        protagonist = c;
    }

    /**
     * initialise the controls for the player, depending on the direction the player is facing, the player
     * will turn to that direction
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT && facingLeft && protagonist.getSprint() !=0){
            protagonist.decreaseSprint();
            protagonist.startWalking(-80);
            protagonist.removeAllImages();
            protagonist.addImage(image).flipHorizontal();// Flip the image to face right
        } else if (e.getKeyCode() == KeyEvent.VK_SHIFT && !facingLeft && protagonist.getSprint() !=0){
            protagonist.decreaseSprint();
            protagonist.startWalking(80);
            protagonist.removeAllImages();
            protagonist.addImage(image);// Flip the image to face right
        }
        // if key a is pressed move left and look left
        if (e.getKeyCode() == KeyEvent.VK_A) {
            protagonist.startWalking(-30);
            protagonist.removeAllImages();
            protagonist.addImage(image).flipHorizontal();// Flip the image to face right
            facingLeft = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            // if key d is pressed move right and look right
            protagonist.startWalking(30);
            protagonist.removeAllImages();
            protagonist.addImage(image); // Flip the image to face right
            facingLeft = false;
        }

        // if key w is pressed Jump
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            protagonist.jump(70);
        }
        if (e.getKeyCode() == KeyEvent.VK_K) {
            // shoot projectile when K is pressed
            if (facingLeft) {
                MagicProjectile magicProjectile2 = new MagicProjectile(protagonist.getWorld(), protagonist,new BoxShape(1f, 0.13f));
                magicProjectile2.setLinearVelocity(new Vec2(-50,0));
                magicProjectile2.setPosition(new Vec2(protagonist.getPosition().x-1f,protagonist.getPosition().y-0.5f));;
                magicProjectile2.removeAllImages();
                magicProjectile2.addImage(new BodyImage("data/magicProjectile.png")).flipHorizontal();
            }
            else {
                MagicProjectile magicProjectile = new MagicProjectile(protagonist.getWorld(), protagonist,new BoxShape(1f, 0.13f));
            }
        }
    }

    /**
     * so the player stops moving once key is released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            protagonist.startWalking(0);
            protagonist.removeAllImages();
            protagonist.addImage(idle).flipHorizontal();// Flip the image to face right
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            protagonist.startWalking(0);
            protagonist.removeAllImages();
            protagonist.addImage(idle);// Flip the image to face right
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT && facingLeft){
            protagonist.startWalking(0);
            protagonist.removeAllImages();
            protagonist.addImage(idle).flipHorizontal();;// Flip the image to face right
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT && !facingLeft){
            protagonist.startWalking(0);
            protagonist.removeAllImages();
            protagonist.addImage(idle);;// Flip the image to face right
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
