package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class NewStageScreen extends ScalableGameScreen {
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

        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("GameOverBackground",0,0,getWorldWidth(),getWorldHeight());
        GameApp.drawTextCentered("Pixel_Emulator", "Stage " + GameScreen.STAGE, getWorldWidth() / 2, getWorldHeight() / 2, "white");
        GameApp.drawTextCentered("Pixel_Emulator", "Press SPACE to continue", getWorldWidth() / 2, getWorldHeight() / 2 - 200, "white");
        GameApp.endSpriteRendering();

        if (GameApp.isKeyPressed(Input.Keys.SPACE)){
            GameApp.switchScreen("GameScreen");
        }

    }


    @Override
    public void hide() {
    }

}