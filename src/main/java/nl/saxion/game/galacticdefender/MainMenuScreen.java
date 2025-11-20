package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class MainMenuScreen extends ScalableGameScreen {
    public MainMenuScreen() {
        super(1280, 720);
    }

    @Override
    public void show() {
        GameApp.addFont("basic", "fonts/basic.ttf", 100);
        GameApp.addFont("basic", "fonts/basic.ttf", 100);
        GameApp.addFont("FreePixel", "fonts/FreePixel.ttf", 70);
        GameApp.addTexture("background","textures/background.png");
        GameApp.addTexture("play_button", "textures/play_button.png");
        GameApp.addTexture("Rectangle_box","textures/Rectangle_box.png");
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // When the user presses enter, go to the next screen
        if (GameApp.isKeyJustPressed(Input.Keys.ENTER)) {
            GameApp.switchScreen("GameScreen");
        }

        // Render the main menu
        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();
        GameApp.drawTextureCentered("background",getWorldWidth()/2f,getWorldHeight()/2f);
        GameApp.drawTextureCentered("Rectangle_box",getWorldWidth()/2f,getWorldHeight()/2f+40,750,500);

        float btnX = getWorldWidth() / 2f - 40;   // button width ~160
        float btnY = getWorldHeight() / 2f - 120;  // lower

        GameApp.drawTexture("play_button", btnX, btnY, 120, 80);
        GameApp.endSpriteRendering();

        GameApp.startSpriteRendering();
        String title = "FreePixel";
        float textX = getWorldWidth()/2f-300;
        float textY = getWorldHeight()/2f+20;
        GameApp.drawText(title,"Galactic Defender",textX,textY,"white");
        GameApp.endSpriteRendering();
        if (GameApp.isKeyPressed(Input.Keys.ESCAPE)){
            GameApp.switchScreen("YourGameScreen");}
    }

    @Override
    public void hide() {
        GameApp.disposeFont("basic");
    }
}
