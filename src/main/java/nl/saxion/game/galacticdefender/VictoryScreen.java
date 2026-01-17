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
        GameApp.addSound("victory", "audio/victory.wav");
        GameApp.addSpriteSheet("fire", "textures/other_graphics/fireworks.png", 200, 250);
        GameApp.addAnimationFromSpritesheet("fireworks", "fire", 0.2f, true);

        GameApp.playSound("victory", 0.7f);
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        GameApp.updateAnimation("fireworks");
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
        float textY1 = getWorldHeight()/2f+20;
        GameApp.drawTextCentered(title,"!! VICTORY !!",GameApp.getWorldWidth() / 2,textY1,"yellow-500");
        GameApp.drawTextCentered(title," Press ENTER \nto go to menu",GameApp.getWorldWidth() / 2,textY1 - 100,"white");
        GameApp.drawAnimation("fireworks", 20, 500, 200, 250, 0, true, false);
        GameApp.drawAnimation("fireworks", 290, 500);
        GameApp.endSpriteRendering();

    }


    @Override
    public void hide() {
        GameApp.disposeTexture("background");
        GameApp.disposeFont("Pixel_Emulator");
        GameApp.disposeSpritesheet("fire");
        GameApp.disposeSound("victory");
    }

}
