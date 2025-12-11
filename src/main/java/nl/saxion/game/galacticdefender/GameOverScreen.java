package nl.saxion.game.galacticdefender;
import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class GameOverScreen extends ScalableGameScreen {

    public GameOverScreen() {
        super(500, 800);
    }

    @Override
    public void show() {
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 25);
        GameApp.addTexture("GameOverBackground", "textures/Other_Backgrounds/GameOverBackground.png");
        GameApp.addParticleEffect("flame", "textures/flame-particle/flame.p");
        long effectId = GameApp.spawnParticleEffect("flame", 450, 400);
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
        GameApp.renderParticleEffects();


        GameApp.endSpriteRendering();

        if (GameApp.isKeyPressed(Input.Keys.SPACE)){
            GameApp.switchScreen("MainMenuScreen");
        }

    }


    @Override
    public void hide() {
    }

}