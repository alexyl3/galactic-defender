package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.util.ArrayList;
import java.util.Collections;


public class LeaderboardScreen extends ScalableGameScreen{
    ArrayList<Score> results;
    public LeaderboardScreen() {
        super(500, 800);
    }

    @Override
    public void show() {
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 25);
        GameApp.addFont("main_text", "fonts/Pixel_Emulator.otf", 18);
        GameApp.addTexture("arrow", "textures/shop_textures/back_arrow.png");
        GameApp.addTexture("background", "textures/Other_Backgrounds/background.png");
        results = GameOverScreen.scores;
        Collections.sort(results);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        float mouseX = getMouseX();
        float mouseY = getMouseY();

        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE) || (GameApp.pointInRect(mouseX, mouseY, 20, getWorldHeight() - 60, 40, 40) && GameApp.isButtonJustPressed(Input.Buttons.LEFT))) {
            GameApp.switchScreen("MainMenuScreen");
        }

        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("background",0,0, getWorldWidth(), getWorldHeight());
        GameApp.drawTexture("arrow", 20,getWorldHeight() - 60, 40, 40);
        if (results.isEmpty()) {
            GameApp.drawText("Pixel_Emulator", "No records yet \nYou can be the first!", 50, 600, "white");
        } else {
            float i = 0;
            for (Score score: results) {
                GameApp.drawText("main_text", score.username, 50, 600 - i * 20, "white");
                GameApp.drawText("main_text", score.score + "", 150, 600 - i * 20, "white");
                i += 1;
            }
        }
        GameApp.endSpriteRendering();

    }


    @Override
    public void hide() {
        GameApp.disposeTexture("background");
        GameApp.disposeTexture("arrow");
        GameApp.disposeFont("Pixel_Emulator");
        GameApp.disposeFont("main_text");
    }

}
