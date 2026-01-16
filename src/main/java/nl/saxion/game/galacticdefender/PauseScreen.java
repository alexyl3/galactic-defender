package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class PauseScreen extends ScalableGameScreen{
    public PauseScreen() {
        super(500, 800);
    }

    @Override
    public void show() {
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 25);
        GameApp.addTexture("background", "textures/Other_Backgrounds/background.png");
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameScreen.START_GAME = 1;
            GameApp.switchScreen("MainMenuScreen");
        } else if (GameApp.isKeyJustPressed(Input.Keys.SPACE) && GameScreen.STAGE != 4) {
            GameApp.switchScreen("GameScreen");
        } else if (GameApp.isKeyJustPressed(Input.Keys.SPACE) && GameScreen.STAGE == 4) {
            GameApp.switchScreen("BossFightScreen");
        }
        GameApp.clearScreen();
        GameApp.startSpriteRendering();

        GameApp.drawTexture("background",0,0,getWorldWidth(),getWorldHeight());
        String title = "Pixel_Emulator";
        float textX1 = getWorldWidth()/2f-100;
        float textY1 = getWorldHeight()/2f+20;
        GameApp.drawText(title,"Paused",textX1,textY1,"white");
        GameApp.drawTextCentered(title,"Press space to continue",getWorldWidth() / 2,textY1-100,"white");
        GameApp.drawTextCentered(title,"Press esc to exit",getWorldWidth() / 2,textY1-150,"white");

        GameApp.endSpriteRendering();

    }


    @Override
    public void hide() {
        GameApp.disposeTexture("background");
        GameApp.disposeFont("Pixel_Emulator");
    }

}
