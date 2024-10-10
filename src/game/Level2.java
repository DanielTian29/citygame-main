package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Random;

/**
 * @author Daniel Tian, daniel.tian@city.ac.uk
 */

public class Level2 extends GameLevel {
    private static Game game;
    private static final BodyImage PlatformImage0 =
            new BodyImage("data/DungeonPlatforms/Platform20.png", 2f); // used to be platform 7 4.77f
    private static final BodyImage PlatformImage1 =
            new BodyImage("data/DungeonPlatforms/Platform15.png", 2f); // used to be platform 8 4.83f
    private static final BodyImage PlatformImage2 =
            new BodyImage("data/DungeonPlatforms/Platform10.png", 2f); // used to be platform 1 2.94f
    private static final BodyImage PlatformImage3 =
            new BodyImage("data/DungeonPlatforms/Platform5.png", 2f); // used to be platform 2 3.99f
    private static final BodyImage PlatformImage4 =
            new BodyImage("data/DungeonPlatforms/Platform2.png", 2f); // used to be platform 5 4.77f

    static float[][] cords = new float[60][3];
    static Random rand = new Random();
    private static Enemy enemy;
    static float[] platformWidths = {20f, 15f, 10f, 5f, 2f};
    static float[] platformHeights = {1f, 1f, 1f, 1f, 1f};
    static int random_num = 0;
    static float chanceOfSpawn = 0;
    private static SoundClip levelMusic;

    static {
        try {
            levelMusic = new SoundClip("data/Audio/level2Music.wav");
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * random map generator, a random position is calculated on the map where the next platforms
     * coordinates is a couple spaces up or down of the previous platform, continues in the x direction,
     * the type of platform is random, if a coin or enemy spawns is also random
     */
    public Level2(Game game) {
        super();
        Level2.game = game;
        getProtagonist().setPosition(new Vec2(0, 3));
        /* create level specific platforms, enemies,
           pickups, collision listeners, etc.*/
        cords[0][0] = 0;
        cords[0][1] = 0;
        cords[0][2] = 0;
        int randomPlat;
        float[] cord;

        //loop to go through the entire array
        for (int i = 1; i < cords.length; i++) {
            if (i < 19) {
                // set up more values
                randomPlat = rand.nextInt(5);
                cord = generateCords(i, randomPlat);
                cords[i][0] = randomPlat;
                cords[i][1] = cord[0];
                cords[i][2] = cord[1];
                chanceOfSpawn = rand.nextFloat();

                //0.5 chance that a enemy spawns in
                if (chanceOfSpawn <= 0.5f && i < 18) {
                    generateEnemies(this, (int) cord[0], (int) cord[1] + 2, randomPlat, i);
                }
                chanceOfSpawn = rand.nextFloat();
                //0.4 chance that a coin spawns in
                if (chanceOfSpawn <= 0.4f && i < 18) {
                    generateCoins(this, (int) cord[0], (int) cord[1] + 2);
                }
            }
        }
        //these if statements add the correct shape sizes and images for each platform, gets the correct values from loop from before
        for (float[] floats : cords) {
            if (floats[0] == 0) {
                Shape shape0 = new BoxShape(20f, 1f); //8.2f, 1.2f
                StaticBody platform = new StaticBody(this, shape0);
                platform.setPosition(new Vec2(floats[1], floats[2]));
                platform.addImage(PlatformImage0);
                SolidFixture fixture = new SolidFixture(platform, shape0);
                fixture.setFriction(0);
            } else if (floats[0] == 1) {
                Shape shape1 = new BoxShape(15f, 1f); //6f, 1.2f
                StaticBody platform = new StaticBody(this, shape1);
                platform.setPosition(new Vec2(floats[1], floats[2]));
                platform.addImage(PlatformImage1);
                SolidFixture fixture = new SolidFixture(platform, shape1);
                fixture.setFriction(0);
            } else if (floats[0] == 2) {
                Shape shape2 = new BoxShape(10f, 1f); //1.2f, 1.2f
                StaticBody platform = new StaticBody(this, shape2);
                platform.setPosition(new Vec2(floats[1], floats[2]));
                platform.addImage(PlatformImage2);
                SolidFixture fixture = new SolidFixture(platform, shape2);
                fixture.setFriction(0);
            } else if (floats[0] == 3) {
                Shape shape3 = new BoxShape(5f, 1f); //1.2f, 1.2f
                StaticBody platform = new StaticBody(this, shape3);
                platform.setPosition(new Vec2(floats[1], floats[2]));
                platform.addImage(PlatformImage3);
                SolidFixture fixture = new SolidFixture(platform, shape3);
                fixture.setFriction(0);
            } else if (floats[0] == 4) {
                Shape shape4 = new BoxShape(2f, 1f); //3.5f, 1.2f
                StaticBody platform = new StaticBody(this, shape4);
                platform.setPosition(new Vec2(floats[1], floats[2]));
                platform.addImage(PlatformImage4);
                SolidFixture fixture = new SolidFixture(platform, shape4);
                fixture.setFriction(0);
            }
        }
        //put the campfire on the final platform
        Campfire campfire = new Campfire(this, cords[18][1], cords[18][2] + 3.3f, game);
    }

    /**
     * spawns in enemies, sets the values
     */
    public static void generateEnemies(World world, int x, int y, int platform, int i) {
        if (platform != 4) {
            enemy = new Enemy(world, game);
            enemy.setPosition(new Vec2(x, y + 0.5f));
            enemy.setRange(x, (int) platformWidths[platform]);
            enemy.startWalking(10);
        }
    }

    /**
     * spawns in coins, sets the values
     */
    public static void generateCoins(World world, int x, int y) {
        Coins coins = new Coins(world);
        coins.setPosition(new Vec2(x, y + 1.4f));
    }

    /**
     * generates the coordinates of the platforms
     */
    public static float[] generateCords(int i, int platform) {
        //set values
        float[] cord = new float[2];
        float originY;
        float boundY;
        float gap;
        if(platform == 0){random_num = rand.nextInt(3,6);}
        if(platform == 1){random_num = rand.nextInt(2,4);}
        if(platform == 2){random_num = rand.nextInt(2,4);}
        if(platform == 3){random_num = rand.nextInt(0,3);}
        if(platform == 4){random_num = rand.nextInt(0,3);}

        gap = platformWidths[(int) cords[i-1][0]] + platformWidths[platform] + cords[i-1][1] + rand.nextInt(5,15);
        originY = cords[i - 1][2] - platformHeights[platform] - random_num;
        boundY = cords[i - 1][2] + platformHeights[platform] + random_num;
        float randomY = rand.nextFloat(originY, boundY);
        cord[1] = randomY;
        cord[0] = gap;
        return cord;
    }

    /**
     * starts music
     */
    @Override
    public void startMusic() {
        levelMusic.loop();
    }

    /**
     * stops music
     */
    @Override
    public void stopMusic() {
        levelMusic.stop();
    }

    /**
     * returns background
     */
    @Override
    public String getBg() {return "data/DungeonBackground.jpg";}

    /**
     * returns enemy
     */
    @Override
    public Enemy getEnemy() {return enemy;}

}
