package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class VictoryScreen extends ScalableGameScreen{
    public VictoryScreen() {
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

        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE) | GameApp.isKeyJustPressed(Input.Keys.ENTER)) {
            Score score = new Score();
            score.score = GameScreen.SCORE + 1000;
            score.username = MainMenuScreen.username;
            GameOverScreen.scores.add(score);
            GameScreen.SCORE = 0;
            GameScreen.STAGE = 0;
            GameScreen.START_GAME = 1;
            GameApp.switchScreen("MainMenuScreen");
        }
        GameApp.clearScreen();
        GameApp.startSpriteRendering();

        GameApp.drawTexture("background",0,0,getWorldWidth(),getWorldHeight());
        String title = "Pixel_Emulator";
        float textX1 = getWorldWidth()/2f-100;
        float textY1 = getWorldHeight()/2f+20;
        GameApp.drawText(title,"VICTORY",textX1,textY1,"white");
        GameApp.drawText(title,"Press ENTER to go to menu",textX1-130,textY1-200,"white");

        GameApp.endSpriteRendering();

    }


    @Override
    public void hide() {
        GameApp.disposeTexture("background");
        GameApp.disposeFont("Pixel_Emulator");
    }

}
