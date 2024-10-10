package game;

import city.cs.engine.UserView;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class GameView extends UserView {
    //initiate values
    private Image bg;
    Protagonist protagonist;
    private GameLevel w;
    private int x;
    private int y;

    public GameView(GameLevel w, int width, int height, Protagonist protagonist) {
        super(w, width, height);
        bg = new ImageIcon("data/HellBackground.jpg").getImage();
        this.protagonist = protagonist;
        this.w = w;
        this.x = 0;
        this.y = 0;
    }

    /**
     * change background based on level, and move image to correct position
     */
    public void setBackground(GameLevel level, int x, int y){
        bg = new ImageIcon(level.getBg()).getImage();
        this.x = x;
        this.y = y;
    }

    /**
     * update to save new level
     */
    public void updateLevel(GameLevel level) {this.w = level;}


    /**
     * update to save new player
     */
    public void updateProtagonist(Protagonist p) {
        this.protagonist = p;
    }

    /**
     * add background image
     */
    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(this.bg, x, y, this);
    }

    /**
     * checks for if player is dead, gives real time updates for player position so
     * the direction the final boss is facing and the speed can be calculated for it
     * also shows the amount of coins, kills, health, stamina the player has
     */
    @Override
    protected void paintForeground(Graphics2D g) {
        if (protagonist.getHealth() <= 0) {
            protagonist.destroy();
            System.out.println("Try again! points: " + protagonist.getPoints());
            System.exit(0);
        }

        if (protagonist.getPosition().y < -50 && !(protagonist.getLevel() instanceof Level3)) {
            protagonist.setPosition(new Vec2(0, 0));
            Protagonist.loseHealth();
            Protagonist.loseHealth();
        } else if (protagonist.getPosition().y < -50) {
            protagonist.setPosition(new Vec2(0, -24));
            Protagonist.loseHealth();
            Protagonist.loseHealth();
        }

        if (protagonist.getLevel() instanceof Level3) {
            if(w.getEnemy().getPosition().x > protagonist.getPosition().x + 20 || w.getEnemy().getPosition().x < protagonist.getPosition().x - 20 || w.getEnemy().getPosition().y > protagonist.getPosition().y + 20 || w.getEnemy().getPosition().y < protagonist.getPosition().y - 20) {
                float dx = protagonist.getPosition().x - w.getEnemy().getPosition().x;
                float dy = protagonist.getPosition().y - w.getEnemy().getPosition().y;
                double magnitude = Math.sqrt(dx * dx + dy * dy);
                double dirX = dx / magnitude;
                double dirY = dy / magnitude;
                double forceMagnitude = 20;
                double forceX = dirX * forceMagnitude;
                double forceY = dirY * forceMagnitude;

                // Calculate angle
                double bossAngle = Math.atan2(dy, dx);
                Level3.updateEnemy((float) bossAngle, (float) forceX, (float) forceY);
            }
        }

        Font coinsFont = new Font("Arial", Font.BOLD, 25);
        g.setFont(coinsFont);
        g.setColor(Color.white);

        Image coinImage = new ImageIcon("data/coinImage.png").getImage();
        Image healthImage = new ImageIcon("data/Heart.png").getImage();
        Image skullImage = new ImageIcon("data/Skull.png").getImage();
        Image sprintImage = new ImageIcon("data/sprint.png").getImage();

        //health display
        int startingHealthPosX = 12;
        for(int i = 0; i < protagonist.getHealth(); i++){
            g.drawImage(healthImage, startingHealthPosX, 10, null, null);
            startingHealthPosX = startingHealthPosX + 20;
        }

        //sprint display
        int startingSprtingPosx = 12;
        for(int i = 0; i < protagonist.getSprint(); i++){
            g.drawImage(sprintImage, startingSprtingPosx, 35, null, null);
            startingSprtingPosx = startingSprtingPosx + 20;
        }

        g.drawImage(coinImage,10, 80,null,this);
        g.drawString(Integer.toString(protagonist.getScore()),40,100);

        g.drawImage(skullImage,10, 120,null,this);
        g.drawString(Integer.toString(protagonist.getKills()),40,140);
    }
}
