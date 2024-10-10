package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class Game {
    private GameLevel level;
    private GameView view;
    private PlayerController playercontroller;
    private  Camera camera;

    private static SoundClip celebrate;

    static {
        try {
            celebrate = new SoundClip("data/Audio/celebrate.wav");
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialise a new Game.
     */
    public Game() {
        level = new Level1(this);
        playercontroller = new PlayerController(level.getProtagonist());
        view = new GameView(level, 800,600 , level.getProtagonist());
        view.setBackground(level,-150,-200);
        camera = new Camera(view, level.getProtagonist());
        level.addStepListener(camera);
        final JFrame frame = new JFrame("Vyke");
        frame.add(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(playercontroller);
        level.startMusic();
        level.start();
    }

    /**
     * Switch levels to next level, initialise the new level by resetting, calculating, updating,
     * or playing whatever is needed
     */
    public void goToNextLevel() {
        if (level instanceof Level1) {
            level.getProtagonist().updatePoints(level,level.getProtagonist().getHealth(),level.getProtagonist().getScore(),level.getProtagonist().getKills());
            level.getProtagonist().resetScore();
            level.getProtagonist().resetKills();
            level.stopMusic();
            level.stop();
            level = new Level2(this);
            view.setWorld(level);
            playercontroller.updateCharacter(level.getProtagonist());
            camera.updateCharacter(level.getProtagonist());
            level.addStepListener(camera);
            view.setBackground(level,-370,-190);
            view.updateLevel(level);
            view.updateProtagonist(level.getProtagonist());
            level.getEnemy().updateSound();
            level.startMusic();
            level.start();
        } else if (level instanceof Level2) {
            level.getProtagonist().updatePoints(level,level.getProtagonist().getHealth(),level.getProtagonist().getScore(),level.getProtagonist().getKills());
            level.getProtagonist().resetScore();
            level.getProtagonist().resetKills();
            level.stopMusic();
            level.stop();
            level = new Level3(this);
            view.setWorld(level);
            playercontroller.updateCharacter(level.getProtagonist());
            camera.updateCharacter(level.getProtagonist());
            level.addStepListener(camera);
            view.setBackground(level,-550,-170);
            view.updateLevel(level);
            view.updateProtagonist(level.getProtagonist());
            level.getEnemy().updateSound();
            level.getEnemy().playSound();
            level.startMusic();
            level.start();
        } else if (level instanceof Level3) {
            level.getProtagonist().updatePoints(level,level.getProtagonist().getHealth(),level.getProtagonist().getScore(),level.getProtagonist().getKills());
            level.stopMusic();
            level.stop();
            celebrate.play();
            System.out.println("Well Done! " + level.getProtagonist().getPoints());
        }
    }

    /**
     * Run the game.
     */
    public static void main(String[] args) {new Game();}
}
