package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.util.ArrayList;
import java.util.Arrays;

public class GameScreen extends ScalableGameScreen {
    int[] AsteriodX = new int[6];
    int[] AsteriodY = new int[6];
    public static final int BG_SPEED = 700;
    public static final int SPACESHIP_SPEED = 300;
    public static final int SPACESHIP_SIZE = 80;
    public static final int BULLET_SPEED = 500;
    float timeElapsed = 0;
    float spaceOffset;
    float bullet_timer = 0;
    SpaceShip player;
    ArrayList<Float> bullets = new ArrayList<>();

    public GameScreen() {
        super(500, 800);
    }

    @Override
    public void show() {
        spaceOffset = 0;
        GameApp.addTexture("space-bg", "textures/space.png");
        GameApp.addTexture("spaceship", "textures/spaceship.png");
        GameApp.addTexture("shot", "textures/shot.png");
        GameApp.addFont("basic", "fonts/basic.ttf", 50);
        player = new SpaceShip();
        player.x = getWorldWidth() / 2;
        player.y = 0;
        int topMin = (int) (GameApp.getWorldHeight() - 200);
        int topMax = (int) GameApp.getWorldHeight();

        for (int index = 0; index < 5; index++) {
            AsteriodX[index] = GameApp.randomInt(0, (int) GameApp.getWorldWidth());
            AsteriodY[index] = GameApp.randomInt(topMin, topMax);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        timeElapsed += delta;
        bullet_timer += delta;

        handlePlayerInput(delta);

        if (bullet_timer >= 0.15) {
            GameApp.addInterpolator("bullet" + bullets.size(), 0f, GameApp.getWorldHeight(), 5f, "pow2");
            bullets.add(player.x + GameApp.getTextureWidth("spaceship") / 2);
            bullet_timer = 0;
        }

        spaceOffset -= BG_SPEED * delta;
        if (spaceOffset < -1 * GameApp.getTextureHeight("space-bg")) {
            spaceOffset = 0;
        }

        for(int i = 0; i<5 ; i++){
            AsteriodY[i] -= (int) ((BG_SPEED*0.3f) * delta);
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
        GameApp.drawTexture("spaceship", player.x, player.y, SPACESHIP_SIZE, SPACESHIP_SIZE);

        for (int i = 0; i < bullets.size(); i++) {
            if (!GameApp.isInterpolatorFinished("bullet" + i)) {
                float bulletY = GameApp.updateInterpolator("bullet" + i) * delta * BULLET_SPEED + GameApp.getTextureHeight("spaceship");
                GameApp.drawTexture("shot", bullets.get(i), bulletY);
            }
        }
        for (int i = 0; i < 6; i++) {
            GameApp.drawTexture("asteriod", AsteriodX[i] - 50, AsteriodY[i] - 70, 50, 30);
        }
        GameApp.endSpriteRendering();

    }

    @Override
    public void hide() {
        GameApp.disposeTexture("spaceship");
        GameApp.disposeTexture("space-bg");
        GameApp.disposeFont("basic");
    }

    public void handlePlayerInput(float delta) {
        if (GameApp.isKeyPressed(Input.Keys.LEFT) || GameApp.isKeyPressed(Input.Keys.A)) {
            player.x -= SPACESHIP_SPEED * delta;
        } else if (GameApp.isKeyPressed(Input.Keys.RIGHT) || GameApp.isKeyPressed(Input.Keys.D)) {
            player.x += SPACESHIP_SPEED * delta;
        }
        player.x = GameApp.clamp(player.x, 0, getWorldWidth() - GameApp.getTextureWidth("spaceship"));

    }
}
