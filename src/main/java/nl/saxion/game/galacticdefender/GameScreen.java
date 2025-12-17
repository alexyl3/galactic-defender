package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class GameScreen extends ScalableGameScreen {
    public static final int BG_SPEED = 700;
    public static final int SPACESHIP_SPEED = 300;
    public static final int SPACESHIP_SIZE = 90;
    public static final int BULLET_SPEED = 500;
    public static final int HEART_SIZE = 50;
    public static final int ALIEN_SIZE = 120;
    public static final int ENEMY_BULLET_SIZE = 50;
    public static final int PLAYER_BULLET_SIZE = 30;
    public static final int BOOSTER_SIZE = 30;
    public static int SCORE = 0;
    public static int coin_display = 0;
    public static int STAGE = 0;
    ArrayList<String> environments =  new ArrayList<>(Arrays.asList("basic", "fire", "ice", "desert"
    ));
    ArrayList<Integer> available = new ArrayList<>(Arrays.asList(0, 50, 120, 100, 150, 170, 200, 230, 250, 300, 320, 350, 400));
    float timeElapsed = 0;
    float spaceOffset;
    float positions_timer = 0;
    float player_bullet_timer = 0;
    float enemy_bullet_timer = 0;
    float alien_timer = 0;
    float asteroid_timer = 0;
    float booster_timer = 0;
    float shield_activated_timer = 0;
    float bullet_activated_timer = 0;
    Booster booster_activated = new Booster();
    float coin_timer = 0;
    SpaceShip player;
    ArrayList<Bullet> player_bullets = new ArrayList<>();
    ArrayList<Bullet> enemy_bullets = new ArrayList<>();

    ArrayList<Alien> aliens = new ArrayList<>();
    ArrayList<Asteroid> asteroids = new ArrayList<>();
    ArrayList<Coin> coins = new ArrayList<>();

    ArrayList<Booster> collected_boosters = new ArrayList<>();
    ArrayList<Booster> boosters = new ArrayList<>();

    public GameScreen() {
        super(500, 800);
    }

    @Override
    public void show() {
        spaceOffset = 0;
        GameApp.addTexture("space-bg", "textures/" + environments.get(STAGE) + "_textures/space.png");
        GameApp.addTexture("spaceship", "textures/" + environments.get(STAGE) + "_textures/spaceship.png");
        GameApp.addTexture("alien", "textures/" + environments.get(STAGE) + "_textures/alien.png");

        GameApp.addTexture("player_shot", "textures/Other_graphics/shot.png");
        GameApp.addTexture("heart", "textures/Other_graphics/heart.png");
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 16);
        GameApp.addTexture("enemy_shot", "textures/Other_graphics/BulletFire.png");
        GameApp.addTexture("Asteroid", "textures/Other_graphics/Asteroid.png");
        GameApp.addTexture("bullet_booster", "textures/Other_graphics/bullet_booster.png");
        GameApp.addTexture("shield_booster", "textures/Other_graphics/shield_booster.png");
        GameApp.addTexture("health_booster", "textures/Other_graphics/health_booster.png");
        GameApp.addTexture("shield", "textures/Other_graphics/spr_shield.png");
        GameApp.addTexture("coin","textures/basic_textures/coin.png");

        GameApp.addSound("laser", "audio/Laser.wav");
        GameApp.addSound("explosion", "audio/SpaceshipExplosion.wav");
        GameApp.addSound("booster_pickup", "audio/Booster_pickup.wav");

        player = new SpaceShip();
        player.x = getWorldWidth() / 2;
        player.y = 0;
        player.lives = 49;

    }
    @Override
    public void render(float delta) {
        super.render(delta);

        timeElapsed += delta;
        player_bullet_timer += delta;
        enemy_bullet_timer += delta;
        positions_timer += delta;
        alien_timer += delta;
        asteroid_timer += delta;
        booster_timer += delta;
        shield_activated_timer -= delta;
        bullet_activated_timer -= delta;
        coin_timer += delta;
        SCORE += (int)(delta * 60);

        if (SCORE > 500 + STAGE * 500) {
            STAGE ++;
            GameApp.switchScreen("NewStageScreen");
        }

        if (player.lives <= 0) {
            GameApp.playSound("explosion");
            startGame();
            GameApp.switchScreen("GameOverScreen");
        }

        if (positions_timer > 0.5) {
            available = new ArrayList<>(Arrays.asList(0, 50, 100, 150, 200, 250, 300, 350, 400));
            positions_timer = 0;
        }
        handlePlayerInput(delta);
        createNewEntities();

        spaceOffset -= BG_SPEED * delta  * (1 + (float) 0.25 * STAGE);
        if (spaceOffset < -1 * GameApp.getTextureHeight("space-bg")) {
            spaceOffset = 0;
        }

        handleCollision();

        // Draw elements
        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("space-bg", 0, spaceOffset);
        GameApp.drawTexture("space-bg", 0, spaceOffset + GameApp.getTextureHeight("space-bg"));

        drawEntities(delta);

        for (int i = 0; i <= player.lives / 10; i++) {
            GameApp.drawTexture("heart", 10 + i * (HEART_SIZE), getWorldHeight() - HEART_SIZE, HEART_SIZE, HEART_SIZE);
        }

        GameApp.drawText("Pixel_Emulator", "score: " + SCORE,  getWorldWidth() - 140, getWorldHeight() - 35, "white");
        GameApp.drawText("Pixel_Emulator", "coins: " + coin_display,  getWorldWidth() - 140, getWorldHeight() - 65, "white");
        GameApp.drawTexture("spaceship", player.x, player.y, SPACESHIP_SIZE, SPACESHIP_SIZE);
        if (booster_activated.type.equals("shield_booster") && shield_activated_timer > 0) {
            GameApp.drawTexture("shield", player.x - 5, player.y - 5, SPACESHIP_SIZE + 10
                    , SPACESHIP_SIZE + 10);
        }
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
        GameApp.disposeTexture("shield_booster");
        GameApp.disposeTexture("bullet_booster");

        GameApp.disposeTexture("shield");

        GameApp.disposeSound("laser");
        GameApp.disposeSound("explosion");
        GameApp.disposeSound("booster_pickup");
    }

    public void handlePlayerInput(float delta) {
        if (GameApp.isKeyJustPressed(Input.Keys.P)) {
            GameApp.switchScreen("PauseScreen");
        }
        if (GameApp.isKeyJustPressed(Input.Keys.E) && shield_activated_timer <= 0 && bullet_activated_timer <= 0) {
            booster_activated = collected_boosters.getLast();
            collected_boosters.removeLast();
            if (booster_activated.type.equals("shield_booster")) {
                shield_activated_timer = 5;
            } else {
                bullet_activated_timer = 5;
            }
        }
        if (GameApp.isKeyPressed(Input.Keys.LEFT) || GameApp.isKeyPressed(Input.Keys.A)) {
            player.x -= SPACESHIP_SPEED * delta * (1 + (float) 0.25 * STAGE);
        } else if (GameApp.isKeyPressed(Input.Keys.RIGHT) || GameApp.isKeyPressed(Input.Keys.D)) {
            player.x += SPACESHIP_SPEED * delta  * (1 + (float) 0.25 * STAGE);
        }
        player.x = GameApp.clamp(player.x, 0, getWorldWidth() - SPACESHIP_SIZE);

    }

    public void handleCollision() {
        for (Alien enemy : aliens) {
            if (GameApp.rectOverlap(player.x, player.y, SPACESHIP_SIZE, SPACESHIP_SIZE, enemy.x, enemy.y, ALIEN_SIZE, ALIEN_SIZE) && enemy.alive) {
                enemy.alive = false;
                if (shield_activated_timer <= 0) {
                    player.lives -= 5;
                }
                if (player.lives <= 0) {
                    GameApp.playSound("explosion");
                    startGame();
                    GameApp.switchScreen("GameOverScreen");
                }
            }
        }

        for (Bullet enemy_bullet: enemy_bullets) {
            if (GameApp.rectOverlap(player.x, player.y, SPACESHIP_SIZE, SPACESHIP_SIZE, enemy_bullet.x, enemy_bullet.y, ENEMY_BULLET_SIZE, ENEMY_BULLET_SIZE) &&
                    enemy_bullet.active) {
                enemy_bullet.active = false;
                if (shield_activated_timer <= 0) {
                    player.lives -= 1;
                }
                if (player.lives <= 0) {
                    GameApp.playSound("explosion");
                    startGame();
                    GameApp.switchScreen("GameOverScreen");
                }
            }
        }

        for (Booster booster : boosters) {
            if (GameApp.rectOverlap(player.x, player.y, SPACESHIP_SIZE, SPACESHIP_SIZE, booster.x, booster.y, BOOSTER_SIZE, BOOSTER_SIZE) && booster.active) {
                booster.active = false;
                GameApp.playSound("booster_pickup");
                if (booster.type.equals("health_booster")) {
                    player.lives += 10;
                } else {
                    collected_boosters.add(booster);
                }
            }
        }

        for (Asteroid asteroid : asteroids) {
            if (GameApp.rectOverlap(asteroid.x, asteroid.y, 80, 80, player.x, player.y, SPACESHIP_SIZE, SPACESHIP_SIZE) &&
                    asteroid.active) {
                asteroid.active = false;
                player.lives -= 3;
                if (player.lives <= 0) {
                    GameApp.playSound("explosion");
                    startGame();
                    GameApp.switchScreen("GameOverScreen");
                }
            }
        }

        for (Coin coin : coins) {
            if (GameApp.rectOverlap(coin.x, coin.y, 80, 80, player.x, player.y, SPACESHIP_SIZE, SPACESHIP_SIZE) &&
                    coin.active) {
                GameApp.playSound("booster_pickup");
                coin.active = false;
                coin_display++;
            }
        }

        for (Bullet player_bullet : player_bullets) {
            if (player_bullet.active) {
                for (Alien enemy : aliens) {
                    if (enemy.alive && GameApp.rectOverlap(player_bullet.x, player_bullet.y, PLAYER_BULLET_SIZE, PLAYER_BULLET_SIZE, enemy.x, enemy.y, ALIEN_SIZE, ALIEN_SIZE)) {
                        enemy.health -= 3;
                        player_bullet.active = false;
                        if (enemy.health <= 0) {
                            enemy.alive = false;
                        }
                    }
                }
            }
        }
    }
    public void createNewEntities() {
        if (player_bullet_timer >= 0.15) {
            GameApp.playSound("laser");
            if (bullet_activated_timer > 0 ) {
                Bullet newBullet = new Bullet();
                GameApp.addInterpolator("player_bullet" + player_bullets.size(), 0, getWorldHeight(), 5f, "pow2");
                newBullet.x = (float) (player.x + SPACESHIP_SIZE / 2.6 - 10);
                newBullet.y = getWorldHeight();
                newBullet.interpolator = "player_bullet" + player_bullets.size();
                player_bullets.add(newBullet);
                newBullet = new Bullet();
                GameApp.addInterpolator("player_bullet" + player_bullets.size(), 0, getWorldHeight(), 5f, "pow2");
                newBullet.x = (float) (player.x + SPACESHIP_SIZE / 2.6 + 10);
                newBullet.y = getWorldHeight();
                newBullet.interpolator = "player_bullet" + player_bullets.size();
                player_bullets.add(newBullet);
            } else {
                Bullet newBullet = new Bullet();
                GameApp.addInterpolator("player_bullet" + player_bullets.size(), 0, getWorldHeight(), 5f, "pow2");
                newBullet.x = (float) (player.x + SPACESHIP_SIZE / 2.6);
                newBullet.y = getWorldHeight();
                newBullet.interpolator = "player_bullet" + player_bullets.size();
                player_bullets.add(newBullet);
            }
            player_bullet_timer = 0;
        }
        if (alien_timer > 2) {
            alien_timer = 0;
            Alien alien = new Alien();
            alien.size = GameApp.randomInt(10, 30);
            int selected = GameApp.randomInt(0, available.size());
            alien.x = available.get(selected);
            available.remove(selected);
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
        if (asteroid_timer > 0.5) {
            asteroid_timer = 0;
            Asteroid newAsteroid = new Asteroid();
            int selected = GameApp.randomInt(0, available.size());
            newAsteroid.x = available.get(selected);
            available.remove(selected);
            newAsteroid.y = GameApp.randomInt(800, 1000);
            newAsteroid.speed = 6;
            newAsteroid.active = true;
            asteroids.add(newAsteroid);
        }
        if (booster_timer > 3) {
            booster_timer = 0;
            Booster newBooster = new Booster();
            newBooster.type = GameApp.random(Arrays.asList("shield_booster", "bullet_booster", "health_booster"));
            int selected = GameApp.randomInt(0, available.size());
            newBooster.x = available.get(selected);
            available.remove(selected);
            newBooster.y = GameApp.random(getWorldHeight(), getWorldHeight() + 2 * BOOSTER_SIZE);
            boosters.add(newBooster);
        }
        if(coin_timer > 1){
            coin_timer = 0;
            Coin newCoin = new Coin();
            int selected = GameApp.randomInt(0, available.size());
            newCoin.x = available.get(selected);
            available.remove(selected);
            newCoin.y = ( (int) getWorldHeight());
            newCoin.speed = 10;
            newCoin.active = true;
            coins.add(newCoin);
        }
    }

    void drawEntities(float delta) {
        for (Alien currAlien : aliens) {
            if (currAlien.alive) {
                GameApp.drawTexture("alien", currAlien.x, currAlien.y, ALIEN_SIZE, ALIEN_SIZE);
                currAlien.y -= delta * BG_SPEED * (1 + (float) 0.25 * STAGE);
            }
        }

        for (Booster booster : boosters) {
            if (booster.active) {
                GameApp.drawTexture(booster.type, booster.x, booster.y, BOOSTER_SIZE, BOOSTER_SIZE);
                booster.y -= delta * BG_SPEED * (1 + (float) 0.25 * STAGE);
            }
        }

        for (Bullet bullet : player_bullets) {
            if (!GameApp.isInterpolatorFinished(bullet.interpolator) && bullet.active) {
                float bulletY = (GameApp.updateInterpolator(bullet.interpolator) * delta * BULLET_SPEED + SPACESHIP_SIZE)  * (1 + (float) 0.25 * STAGE);
                bullet.y = bulletY;
                GameApp.drawTexture("player_shot", bullet.x, bulletY);
                if (bullet.y > getWorldHeight() + 50) {
                    bullet.active = false;
                }
            }
        }

        for (Bullet bullet : enemy_bullets) {
            if (bullet.active) {
                bullet.y -= delta * BULLET_SPEED * 5 * (1 + (float) 0.25 * STAGE);
                GameApp.drawTexture("enemy_shot", bullet.x, bullet.y, ENEMY_BULLET_SIZE, ENEMY_BULLET_SIZE);
                if (bullet.y < -20) {
                    bullet.active = false;
                }
            }
        }

        for(Asteroid currAsteroid:asteroids){
            GameApp.drawTexture("Asteroid",currAsteroid.x,currAsteroid.y,80,80);
            currAsteroid.y -= delta * BG_SPEED * (1 + (float) 0.25 * STAGE);
        }
        for(Coin currCoin:coins){
            if(currCoin.active){
                currCoin.y -= delta * BG_SPEED * (1 + (float) 0.25 * STAGE);

                GameApp.drawTexture("coin",currCoin.x,currCoin.y,40,40);
                if(currCoin.y < -40){
                    currCoin.active = false;
                }
            }}

        int cnt = 0;
        for (Booster booster: collected_boosters) {
            if (!booster.used) {
                GameApp.drawTexture(booster.type ,20, getWorldHeight() - 50 - (1 + cnt) * BOOSTER_SIZE - cnt * 10, BOOSTER_SIZE, BOOSTER_SIZE);
                cnt ++;
            }
        }
    }

    public void startGame() {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        collected_boosters = new ArrayList<>();
        player_bullet_timer = 0;
        enemy_bullet_timer = 0;
        alien_timer = 0;
        asteroid_timer = 0;
        booster_timer = 0;
        available = new ArrayList<>(Arrays.asList(0, 50, 120, 100, 150, 170, 200, 230, 250, 300, 320, 350, 400));
        positions_timer = 0;
        player = new SpaceShip();
        player_bullets = new ArrayList<>();
        enemy_bullets = new ArrayList<>();

        aliens = new ArrayList<>();
        asteroids = new ArrayList<>();

        collected_boosters = new ArrayList<>();
        boosters = new ArrayList<>();

        shield_activated_timer = 0;
        bullet_activated_timer = 0;
    }}