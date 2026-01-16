package nl.saxion.game.galacticdefender;
import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.util.ArrayList;

public class GameOverScreen extends ScalableGameScreen {
    public static ArrayList<Score> scores = new ArrayList<>();
    public GameOverScreen() {
        super(500, 800);
    }

    @Override
    public void show() {
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 25);
        GameApp.addFont("title", "fonts/Pixel_Emulator.otf", 40);
        GameApp.addSound("lose", "audio/lose_sound.wav");
        GameApp.addTexture("GameOverBackground", "textures/Other_Backgrounds/GameOverBackground.png");
        GameApp.debug(GameScreen.SCORE);
        GameApp.playSound("lose");
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.updateParticleEffects();

        GameApp.drawTexture("GameOverBackground",0,0,getWorldWidth(),getWorldHeight());
        String title = "Pixel_Emulator";
        float textY1 = getWorldHeight()/2f+20;
        GameApp.drawTextCentered("title","GAME OVER",getWorldWidth() / 2,textY1,"white");
        GameApp.drawTextCentered(title,"Press space to go",getWorldWidth() / 2,textY1-100,"white");
        GameApp.drawTextCentered(title,"to the main menu",getWorldWidth() / 2,textY1-130,"white");
        GameApp.drawTextCentered(title,"Press C to continue",getWorldWidth() / 2,textY1-200,"white");
        GameApp.drawTextCentered(title,"by spending 5 coins",getWorldWidth() / 2,textY1-230,"white");
        GameApp.renderParticleEffects();


        GameApp.endSpriteRendering();

        if (GameApp.isKeyPressed(Input.Keys.SPACE)){
            Score score = new Score();
            score.score = GameScreen.SCORE;
            score.username = MainMenuScreen.username;
            scores.add(score);
            GameScreen.SCORE = 0;
            GameScreen.STAGE = 0;
            GameScreen.START_GAME = 1;
            GameApp.switchScreen("MainMenuScreen");

        }
        if(GameApp.isKeyPressed(Input.Keys.C)){
            if(GameScreen.coin_display >= 5)
            { GameScreen.coin_display -=5;
            GameApp.switchScreen("GameScreen");}
        }

    }


    @Override
    public void hide() {
    }

}