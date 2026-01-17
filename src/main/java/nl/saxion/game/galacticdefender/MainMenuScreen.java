package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class MainMenuScreen extends ScalableGameScreen {
    public static String username = "";
    String currentText = "";
    boolean usernameSaved = false;
    String usernameColor = "white";
    float cursorBlink = 0;
    public MainMenuScreen() {
        super(500, 800);
    }

    @Override
    public void show() {
        GameApp.addFont("basic", "fonts/basic.ttf", 100);
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 20);
        GameApp.addTexture("background", "textures/Other_Backgrounds/background.png");
        GameApp.addTexture("play_button", "textures/other_graphics/play_button.png");
        GameApp.addTexture("Button", "textures/other_graphics/Button.png");
        GameApp.addTexture("leaderboard_button","textures/other_graphics/leaderboard_button.png");
        GameApp.addTexture("frame","textures/other_graphics/neon_frame.png");
        GameApp.addFont("Game_Paused","fonts/Game_Paused.otf",40);
        GameApp.addTexture("Customise_button", "textures/other_graphics/customise_button-removebg-preview.png");
        GameApp.addTexture("shop_button","textures/other_graphics/shop_button.png");

        GameApp.addMusic("menu_music", "audio/game_loop_music.mp3");
        GameApp.playMusic("menu_music", true, 0.7f);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        cursorBlink += delta;

        float mouseX = getMouseX();
        float mouseY = getMouseY();
        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)&&GameApp.pointInRect(mouseX,mouseY,getWorldWidth()-50,getWorldHeight()-50,GameApp.getTextureWidth("Button"),GameApp.getTextureHeight("Button"))){
            GameApp.switchScreen("ManualScreen");
        }
        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)&&GameApp.pointInRect(mouseX,mouseY,getWorldWidth()-95,getWorldHeight()-50, 32, 32)){
            GameApp.switchScreen("LeaderboardScreen");
        }
        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)&&GameApp.pointInRect(mouseX,mouseY,getWorldWidth()-500,getWorldHeight()-100,GameApp.getTextureWidth("customise_button"),GameApp.getTextureHeight("customise_button"))){
            GameApp.switchScreen("CustomizationScreen");
        }
        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)&&GameApp.pointInRect(mouseX,mouseY,getWorldWidth()-510,getWorldHeight()-180,GameApp.getTextureWidth("shop_button"),GameApp.getTextureHeight("shop_button"))){
            GameApp.switchScreen("ShopScreen");
        }
        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)&&GameApp.pointInCircle(mouseX,mouseY, 260, 330, 60)){
            GameApp.switchScreen("GameScreen");
        }

        // Render the main menu
        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();
        GameApp.drawTexture("background",0,0, getWorldWidth(), getWorldHeight());

        float btnX = getWorldWidth() / 2f - 50;   // button width ~160
        float btnY = getWorldHeight() / 2f - 130;  // lower

        GameApp.drawTexture("play_button", btnX, btnY, 120, 120);

        String title = "Game_Paused";
        float textY = getWorldHeight()/2f+20;
        GameApp.drawTexture("Button",getWorldWidth()-50,getWorldHeight()-50);
        GameApp.drawTexture("leaderboard_button",getWorldWidth()-95,getWorldHeight()-50, 32, 32);
        GameApp.drawTexture("customise_button",getWorldWidth()-500,getWorldHeight()-100);
        GameApp.drawTexture("shop_button",getWorldWidth()-510,getWorldHeight()-180);
        GameApp.drawTextCentered(title,"Galactic Defender",GameApp.getWorldWidth() / 2, textY+60,"white");
        GameApp.drawTextureCentered("frame", GameApp.getWorldWidth() / 2, textY+60, 500, 120);
        GameApp.drawText("Pixel_Emulator", "Press ENTER to save", 50, textY - 230, "white");
        GameApp.drawText("Pixel_Emulator", "Enter username: ", 50, textY - 200, "white");
        if (usernameSaved) {
            usernameColor = "yellow-400";
        } else {
            usernameColor = "white";
        }
        if (cursorBlink < 0.5) {
            GameApp.drawText("Pixel_Emulator",  currentText + "|", 300, textY - 200, usernameColor);
        } else {
            if (cursorBlink > 1) {
                cursorBlink = 0;
            }
            GameApp.drawText("Pixel_Emulator", currentText, 280, textY - 200, usernameColor);
        }
        GameApp.endSpriteRendering();

    }

    @Override
    public void hide() {

        GameApp.disposeFont("basic");
        GameApp.disposeFont("Pixel_Emulator");
        GameApp.disposeTexture("background");
        GameApp.disposeTexture("play_button");
        GameApp.disposeTexture("Button");
        GameApp.disposeTexture("shop_button");
        GameApp.disposeTexture("Customise_button");
        GameApp.disposeMusic("menu_music");
        GameApp.disposeTexture("leaderboard_button");
    }

    @Override
    public boolean keyTyped(char character) {
        if(character == '\b') {
            if(!currentText.isEmpty()) {
                usernameSaved = false;
                currentText = currentText.substring(0, currentText.length() - 1);
            }
        } else if(character == '\r' || character == '\n') {
            usernameSaved = true;
            username = currentText;
        } else {
            usernameSaved = false;
            currentText += character;
        }
        return true;
    }
}
