package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class MainMenuScreen extends ScalableGameScreen {
    public MainMenuScreen() {
        super(500, 800);
    }

    @Override
    public void show() {
        GameApp.addFont("basic", "fonts/basic.ttf", 100);
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 32);
        GameApp.addTexture("background", "textures/Other_Backgrounds/background.png");
        GameApp.addTexture("play_button", "textures/Other_graphics/play_button.png");
        GameApp.addTexture("Rectangle_box", "textures/Other_graphics/Rectangle_box.png");
        GameApp.addTexture("asteriod", "textures/Other_graphics/asteriod.png");
        GameApp.addTexture("Button","textures/Other_graphics/Button.png");
        GameApp.addTexture("Customise_button","textures/other_graphics/customise_button.png");
        GameApp.addFont("Game_Paused","fonts/Game_Paused.otf",32);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // When the user presses enter, go to the next screen
        if (GameApp.isKeyJustPressed(Input.Keys.ENTER)) {
            GameApp.switchScreen("GameScreen");
        }

        float mouseX = getMouseX();
        float mouseY = getMouseY();
        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)&&GameApp.pointInRect(mouseX,mouseY,getWorldWidth()-50,getWorldHeight()-50,GameApp.getTextureWidth("Button"),GameApp.getTextureHeight("Button"))){
            GameApp.switchScreen("ManualScreen");
        }
        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)&&GameApp.pointInRect(mouseX,mouseY,getWorldWidth()-500,getWorldHeight()-100,GameApp.getTextureWidth("customise_button"),GameApp.getTextureHeight("customise_button"))){
            GameApp.switchScreen("CustomizationScreen");
        }

        // Render the main menu
        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();
        GameApp.drawTexture("background",0,0, getWorldWidth(), getWorldHeight());
        GameApp.drawTextureCentered("Rectangle_box",getWorldWidth()/2f,getWorldHeight()/2f+40,getWorldWidth()+50,100);

        float btnX = getWorldWidth() / 2f - 40;   // button width ~160
        float btnY = getWorldHeight() / 2f - 120;  // lower

        GameApp.drawTexture("play_button", btnX, btnY, 120, 80);

        String title = "Game_Paused";
        float textX = getWorldWidth()/2f-300;
        float textY = getWorldHeight()/2f+20;
        GameApp.drawTexture("Button",getWorldWidth()-50,getWorldHeight()-50);
        GameApp.drawTexture("customise_button",getWorldWidth()-500,getWorldHeight()-100);
        GameApp.drawText(title,"Galactic Defender",textX+100,textY+10,"white");
        GameApp.endSpriteRendering();
        if (GameApp.isKeyPressed(Input.Keys.ESCAPE)){
            GameApp.switchScreen("YourGameScreen");}


    }

    @Override
    public void hide() {

        GameApp.disposeFont("basic");
        GameApp.disposeFont("Pixel_Emulator");
        GameApp.disposeTexture("background");
        GameApp.disposeTexture("play_button");
        GameApp.disposeTexture("Rectangle_box");
        GameApp.disposeTexture("Button");
    }
}
