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
        GameApp.addTexture("GameOverBackground", "textures/Other_Backgrounds/GameOverBackground.png");
//        GameApp.addParticleEffect("flame", "textures/flame-particle/flame.p");
//        long effectId = GameApp.spawnParticleEffect("flame", 450, 400);
        GameApp.debug(GameScreen.SCORE);
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.updateParticleEffects();

        GameApp.drawTexture("GameOverBackground",0,0,getWorldWidth(),getWorldHeight());
        String title = "Pixel_Emulator";
        float textX1 = getWorldWidth()/2f-100;
        float textY1 = getWorldHeight()/2f+20;
        GameApp.drawText(title,"GAME OVER",textX1,textY1,"white");
        GameApp.drawText(title,"Press space to go back",textX1-130,textY1-200,"white");
        GameApp.drawText(title,"to the main menu",textX1-90,textY1-250,"white");
        GameApp.drawText(title,"Press C to continue \nby spending 5 coins",textX1-120,textY1-300,"white");
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