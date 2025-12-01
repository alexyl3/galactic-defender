package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class NewStageScreen extends ScalableGameScreen {
    float x_1 = 0;
    float x_2 = getWorldWidth();
    public NewStageScreen() {
        super(500, 800);
    }
    @Override
    public void show() {
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 25);
        GameApp.addTexture("GameOverBackground", "textures/Other_Backgrounds/GameOverBackground.png");
        GameApp.debug(GameScreen.SCORE);
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        if (x_1 < getWorldWidth() / 2) {
            x_1 += delta * 250;
        }
        if (x_2 > getWorldWidth() / 2) {
            x_2 -= delta * 250;
        }
        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("GameOverBackground",0,0,getWorldWidth(),getWorldHeight());
        GameApp.drawTextCentered("Pixel_Emulator", "Stage " + GameScreen.STAGE, x_1, getWorldHeight() / 2, "white");
        GameApp.drawTextCentered("Pixel_Emulator", "Press SPACE to continue", x_2, getWorldHeight() / 2 - 50, "white");
        GameApp.endSpriteRendering();

        if (GameApp.isKeyPressed(Input.Keys.SPACE)){
            GameApp.switchScreen("GameScreen");
        }

    }


    @Override
    public void hide() {
    }

}