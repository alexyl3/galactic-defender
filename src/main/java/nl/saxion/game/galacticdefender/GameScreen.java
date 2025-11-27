package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.util.ArrayList;

public class GameScreen extends ScalableGameScreen {
    int[] AsteriodX = new int[6];
    int[] AsteriodY = new int[6];
    public static final int BG_SPEED = 700;
    public static final int SPACESHIP_SPEED = 200;
    public static final int SPACESHIP_SIZE = 80;
    public static final int BULLET_SPEED = 500;
    public static final int HEART_SIZE = 50;
    public static final int ALIEN_SIZE = 100;
    public static final int ENEMY_BULLET_SIZE = 50;
    public static final int PLAYER_BULLET_SIZE = 30;
    public static int SCORE = 0;
    public static String ENVIRONMENT = "basic";
    float timeElapsed = 0;
    float spaceOffset;
    float player_bullet_timer = 0;
    float enemy_bullet_timer = 0;
    float alien_timer = 0;
    SpaceShip player;
    ArrayList<Bullet> player_bullets = new ArrayList<>();
    ArrayList<Bullet> enemy_bullets = new ArrayList<>();

    ArrayList<Alien> aliens = new ArrayList<>();

    public GameScreen() {
        super(500, 800);
    }

    @Override
    public void show() {
        spaceOffset = 0;
        GameApp.addTexture("space-bg", "textures/Other_graphics/space.png");
        GameApp.addTexture("spaceship", "textures/Other_graphics/spaceship.png");
        GameApp.addTexture("player_shot", "textures/Other_graphics/shot.png");
        GameApp.addTexture("enemy_shot", "textures/fire_textures/BulletFire.png");
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 16);
        GameApp.addTexture("heart", "textures/Other_graphics/heart.png");
        GameApp.addTexture("alien", "textures/Alien3_no_bg.png");
        GameApp.addTexture("Asteroid", "textures/Other_graphics/Asteroid.png");
        player = new SpaceShip();
        player.x = getWorldWidth() / 2;
        player.y = 0;
        player.lives = 49;
        int topMin = (int) (GameApp.getWorldHeight() - 200);
        int topMax = (int) GameApp.getWorldHeight();

        for (int index = 0; index < 6; index++) {
            AsteriodX[index] = GameApp.randomInt(0, (int) GameApp.getWorldWidth());
            AsteriodY[index] = GameApp.randomInt(topMin, topMax);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        timeElapsed += delta;
        player_bullet_timer += delta;
        enemy_bullet_timer += delta;
        alien_timer += delta;
        SCORE += (int) (delta * 60);

        handlePlayerInput(delta);

        if (player_bullet_timer >= 0.15) {
            Bullet newBullet = new Bullet();
            GameApp.addInterpolator("player_bullet" + player_bullets.size(), 0, getWorldHeight(), 5f, "pow2");
            newBullet.x = (float) (player.x + GameApp.getTextureWidth("spaceship") / 2.6);
            newBullet.y = getWorldHeight();
            newBullet.interpolator = "player_bullet" + player_bullets.size();
            player_bullets.add(newBullet);
            player_bullet_timer = 0;
        }
        if (alien_timer > 2) {
            alien_timer = 0;
            Alien alien = new Alien();
            alien.size = GameApp.randomInt(10, 30);
            alien.x = GameApp.random(0, getWorldWidth() - ALIEN_SIZE);
            alien.y = getWorldHeight();
            aliens.add(alien);
        }

        if (enemy_bullet_timer >= 0.2) {
            for (Alien enemy : aliens) {
                if (enemy.alive) {
                    Bullet newBullet = new Bullet();
                    newBullet.x = (float) (enemy.x + enemy.size * 0.9);
                    newBullet.y = enemy.y;
                    newBullet.interpolator = "enemy_bullet" + enemy_bullets.size();
                    enemy_bullets.add(newBullet);
                    enemy_bullet_timer = 0;
                }
            }
        }

        spaceOffset -= BG_SPEED * delta;
        if (spaceOffset < -1 * GameApp.getTextureHeight("space-bg")) {
            spaceOffset = 0;
        }
        handleCollision(delta);

        for (int i = 0; i < 5; i++) {
            AsteriodY[i] -= (int) ((BG_SPEED * 0.3f) * delta);
            if (AsteriodY[i] < -100) {
                AsteriodY[i] = (int) (GameApp.getWorldHeight() + GameApp.randomInt(0, 200));
                AsteriodX[i] = GameApp.randomInt(0, (int) getWorldWidth());
            }
        }

        // Draw elements
        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("space-bg", 0, spaceOffset);
        GameApp.drawTexture("space-bg", 0, spaceOffset + GameApp.getTextureHeight("space-bg"));

        for (int i = 0; i < 5; i++) {
            GameApp.drawTexture("Asteroid", AsteriodX[i], AsteriodY[i], 60, 60);
        }
        for (Alien currAlien : aliens) {
            if (currAlien.alive && currAlien.health <= 0) {
                SCORE += 100;
                currAlien.alive = false;
            }
            if (currAlien.alive) {
                GameApp.drawTexture("alien", currAlien.x, currAlien.y, ALIEN_SIZE, ALIEN_SIZE);
                currAlien.y -= delta * BG_SPEED;
            }
        }

        for (Bullet bullet : player_bullets) {
            if (!GameApp.isInterpolatorFinished(bullet.interpolator) && bullet.active) {
                float bulletY = GameApp.updateInterpolator(bullet.interpolator) * delta * BULLET_SPEED + GameApp.getTextureHeight("spaceship");
                bullet.y = bulletY;
                GameApp.drawTexture("player_shot", bullet.x, bulletY, PLAYER_BULLET_SIZE, PLAYER_BULLET_SIZE);
                if (bullet.y > getWorldHeight() + 50) {
                    bullet.active = false;
                }
            }
        }

        for (Bullet bullet : enemy_bullets) {
            if (bullet.active) {
                bullet.y -= delta * BULLET_SPEED * 5;
                GameApp.drawTexture("enemy_shot", bullet.x, bullet.y, ENEMY_BULLET_SIZE, ENEMY_BULLET_SIZE);
                if (bullet.y < -20) {
                    bullet.active = false;
                }
            }
        }

        for (int i = 0; i <= player.lives / 10; i++) {
            GameApp.drawTexture("heart", 10 + i * (HEART_SIZE), getWorldHeight() - HEART_SIZE, HEART_SIZE, HEART_SIZE);
        }

        GameApp.drawText("Pixel_Emulator", "score: " + SCORE,  getWorldWidth() - 140, getWorldHeight() - 35, "white");
        GameApp.drawTexture("spaceship", player.x, player.y, SPACESHIP_SIZE, SPACESHIP_SIZE);

        GameApp.endSpriteRendering();

    }

    @Override
    public void hide() {
        GameApp.disposeTexture("spaceship");
        GameApp.disposeTexture("space-bg");
        GameApp.disposeTexture("enemy_shot");
        GameApp.disposeTexture("player_shot");
        GameApp.disposeTexture("alien");
        GameApp.disposeTexture("Asteroid");
        GameApp.disposeFont("Pixel_Emulator");
        GameApp.disposeTexture("heart");
    }

    public void handlePlayerInput(float delta) {
        if (GameApp.isKeyJustPressed(Input.Keys.P)) {
            GameApp.switchScreen("PauseScreen");
        }
        if (GameApp.isKeyPressed(Input.Keys.LEFT) || GameApp.isKeyPressed(Input.Keys.A)) {
            player.x -= SPACESHIP_SPEED * delta;
        } else if (GameApp.isKeyPressed(Input.Keys.RIGHT) || GameApp.isKeyPressed(Input.Keys.D)) {
            player.x += SPACESHIP_SPEED * delta;
        }
        player.x = GameApp.clamp(player.x, 0, getWorldWidth() - GameApp.getTextureWidth("spaceship"));

    }

    public void handleCollision(float delta) {
        for (Alien enemy : aliens) {
            if (GameApp.rectOverlap(player.x, player.y, SPACESHIP_SIZE, SPACESHIP_SIZE, enemy.x, enemy.y, ALIEN_SIZE, ALIEN_SIZE) && enemy.alive) {
                enemy.alive = false;
                player.lives -= 5;
            }
        }

        for (Bullet enemy_bullet: enemy_bullets) {
            if (GameApp.rectOverlap(player.x, player.y, SPACESHIP_SIZE, SPACESHIP_SIZE, enemy_bullet.x, enemy_bullet.y, ENEMY_BULLET_SIZE, ENEMY_BULLET_SIZE) &&
            enemy_bullet.active) {
                enemy_bullet.active = false;
                player.lives -= 1;
            }
        }

        for (Bullet player_bullet: player_bullets) {
            if (player_bullet.active) {
                for (Alien enemy: aliens) {
                    if (enemy.alive && GameApp.rectOverlap(player_bullet.x, player_bullet.y, PLAYER_BULLET_SIZE, PLAYER_BULLET_SIZE, enemy.x, enemy.y, ALIEN_SIZE, ALIEN_SIZE)) {
                        enemy.health -= 1;
                        player_bullet.active = false;
                    }
                }
            }
        }
        if (player.lives <= 0) {
            GameApp.switchScreen("GameOverScreen");
        }
    }
}
