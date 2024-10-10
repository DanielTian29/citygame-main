package game;

import city.cs.engine.*;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class EnemySensor extends Sensor implements SensorListener {
    Enemy enemy;
    private Game game;
    private  World world;
    public EnemySensor(Enemy body, Shape shape, Game game, World world) {
        super(body, shape);
        this.addSensorListener(this);
        this.enemy = body;
        this.game = game;
        this.world = world;
    }

    /**
     * checks if player collided with enemy or if magic projectile made contact with enemy
     * if player makes contact with enemy, then player loses health
     * if magic projectile makes contact with enemy then enemy gets destroyed, plays death sound
     * unless it's the last enemy which is the boss
     */
    @Override
    public void beginContact(SensorEvent sensorEvent){
        if (sensorEvent.getContactBody() instanceof MagicProjectile magicProjectile){
            magicProjectile.destroy();
            enemy.takeDamage();
            if (enemy.getHealth() == 0 && !(world instanceof Level3)) {
                enemy.destroy();
                enemy.playSound();
                Protagonist.increaseKills();
                Protagonist.increaseSprint();
            } else if(enemy.getHealth() == 0){
                enemy.destroy();
                Protagonist.increaseKills();
                Protagonist.increaseSprint();
                game.goToNextLevel();
            }
        }
        if (sensorEvent.getContactBody() instanceof Protagonist protagonist){
            Protagonist.loseHealth();
        }
    }

    @Override
    public void endContact(SensorEvent sensorEvent){

    }
}
