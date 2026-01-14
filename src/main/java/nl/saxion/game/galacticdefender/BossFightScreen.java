package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.util.ArrayList;

public class BossFightScreen  extends ScalableGameScreen {
    SpaceShip player;
    float player_bullet_timer = 0;
    float enemy_bullet_timer = 0;
    Alien boss;
    ArrayList<Bullet> player_bullets = new ArrayList<>();
    ArrayList<Bullet> enemy_bullets = new ArrayList<>();

    public BossFightScreen() {
        super(500, 800);
    }

    @Override
    public void show() {
        player = new SpaceShip();
        player.x = (getWorldWidth() - GameScreen.SPACESHIP_SIZE) / 2;
        player.y = 0;
        player.lives = 49;
        boss = new Alien();
        boss.health = 100;
        boss.x = 50;
        boss.y = 520;
        GameApp.addTexture("background", "textures/basic_textures/space.png");
        GameApp.addTexture("boss", "textures/Other_graphics/BossSpaceship.png");
        GameApp.addTexture("heart", "textures/Other_graphics/heart.png");
        GameApp.addTexture("enemy_shot", "textures/Other_graphics/BulletFire.png");
        GameApp.addTexture("player_shot", "textures/Other_graphics/shot.png");
        GameApp.addSound("explosion", "audio/SpaceshipExplosion.wav");
        GameApp.addSound("laser", "audio/Laser.wav");
        GameApp.addTexture("spaceship", "textures/shop_textures/" + GameScreen.activeSpaceship + "_spaceship.png");

    }


    @Override
    public void render(float delta) {
        super.render(delta);

        player_bullet_timer += delta;
        enemy_bullet_timer += delta;

        handlePlayerInput(delta);
        createNewEntities();
        handleCollision();

        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("background", 0, 0);
        GameApp.drawTexture("spaceship", player.x, player.y, GameScreen.SPACESHIP_SIZE, GameScreen.SPACESHIP_SIZE);
        GameApp.drawTexture("boss", boss.x, boss.y, 250, 200);
        drawEntities(delta);

        for (int i = 0; i <= player.lives / 10; i++) {
            GameApp.drawTexture("heart", 10 + i * (GameScreen.HEART_SIZE), getWorldHeight() - GameScreen.HEART_SIZE, GameScreen.HEART_SIZE, GameScreen.HEART_SIZE);}
        GameApp.endSpriteRendering();
        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(boss.x + 75, boss.y + 210, 100, 10, "white");
        GameApp.drawRect(boss.x + 75, boss.y + 210, boss.health, 10, "yellow-500");
        GameApp.endShapeRendering();

    }


    @Override
    public void hide() {
        GameApp.disposeTexture("background");
        GameApp.disposeTexture("heart");
        GameApp.disposeTexture("enemy_shot");
        GameApp.disposeTexture("spaceship");
        GameApp.disposeTexture("player_shot");
        GameApp.disposeSound("explosion");
        GameApp.disposeSound("laser");
    }

    public void handlePlayerInput(float delta) {
        if (GameApp.isKeyJustPressed(Input.Keys.P)) {
            GameApp.switchScreen("PauseScreen");
        }
        if (GameApp.isKeyPressed(Input.Keys.LEFT) || GameApp.isKeyPressed(Input.Keys.A)) {
            player.x -= GameScreen.SPACESHIP_SPEED * delta;
        } else if (GameApp.isKeyPressed(Input.Keys.RIGHT) || GameApp.isKeyPressed(Input.Keys.D)) {
            player.x += GameScreen.SPACESHIP_SPEED * delta;
        }
        player.x = GameApp.clamp(player.x, 0, getWorldWidth() - GameScreen.SPACESHIP_SIZE);
        if (GameApp.isKeyPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("PauseScreen");
        }
    }

    public void createNewEntities() {
        if (player_bullet_timer >= 0.15) {
            Bullet newBullet = new Bullet();
            GameApp.addInterpolator("player_bullet" + player_bullets.size(), 0, getWorldHeight(), 5f, "pow2");
            newBullet.x = (float) (player.x + GameScreen.SPACESHIP_SIZE / 2.6);
            newBullet.y = getWorldHeight();
            newBullet.interpolator = "player_bullet" + player_bullets.size();
            player_bullets.add(newBullet);
            player_bullet_timer = 0;
        }
        if (enemy_bullet_timer >= 0.2) {
            Bullet newBullet = new Bullet();
            newBullet.x = boss.x + 125 + GameApp.randomInt(-100, 100);
            newBullet.y = 520;
            newBullet.interpolator = "enemy_bullet" + enemy_bullets.size();
            enemy_bullets.add(newBullet);
            enemy_bullet_timer = 0;
                }
    }
    void drawEntities(float delta) {
        for (Bullet bullet : player_bullets) {
            if (!GameApp.isInterpolatorFinished(bullet.interpolator) && bullet.active) {
                float bulletY = (GameApp.updateInterpolator(bullet.interpolator) * delta * GameScreen.BULLET_SPEED + GameScreen.SPACESHIP_SIZE);
                bullet.y = bulletY;
                GameApp.drawTexture("player_shot", bullet.x, bulletY);
                if (bullet.y > getWorldHeight() + 50) {
                    bullet.active = false;
                }
            }
        }

        for (Bullet bullet : enemy_bullets) {
            if (bullet.active) {
                bullet.y -= delta * GameScreen.BULLET_SPEED * 2;
                GameApp.drawTexture("enemy_shot", bullet.x, bullet.y, GameScreen.ENEMY_BULLET_SIZE, GameScreen.ENEMY_BULLET_SIZE);
                if (bullet.y < -20) {
                    bullet.active = false;
                }
            }
        }}
    public void handleCollision() {
        for (Bullet enemy_bullet : enemy_bullets) {
            if (GameApp.rectOverlap(player.x, player.y, GameScreen.SPACESHIP_SIZE, GameScreen.SPACESHIP_SIZE, enemy_bullet.x, enemy_bullet.y, GameScreen.ENEMY_BULLET_SIZE, GameScreen.ENEMY_BULLET_SIZE) &&
                    enemy_bullet.active) {
                enemy_bullet.active = false;
                player.lives -= 3;
                if (player.lives <= 0) {
                    GameApp.playSound("explosion");
                    GameApp.switchScreen("GameOverScreen");
                }
            }
        }
        for (Bullet bullet : player_bullets) {
            if (GameApp.rectOverlap(boss.x, boss.y, 250, 200, bullet.x, bullet.y, GameScreen.PLAYER_BULLET_SIZE, GameScreen.PLAYER_BULLET_SIZE) &&
                    bullet.active) {
                bullet.active = false;
                boss.health -= 2;
                GameApp.playSound("laser");
                if (bullet.x <= boss.x + 125) {
                   boss.x = GameApp.clamp(boss.x + GameApp.random(-20, 50), 0, 250);
                } else {
                    boss.x = GameApp.clamp(boss.x - GameApp.random(-20, 50), 0, 250);
                }
                if (boss.health <= 0) {
                    GameApp.playSound("explosion");
                    GameApp.switchScreen("VictoryScreen");
                }
            }
        }


    }

    }
