package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class CustomizationScreen extends ScalableGameScreen {
    String[] ships = {"spaceship1","spaceship2","spaceship3","spaceship4"};
    int index = 0;

    public CustomizationScreen() {
        super(500,800);

    }

    @Override
    public void show() {
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 25);
        GameApp.addTexture("customization_background","textures/Other_Backgrounds/customization_bg.jpg");
        GameApp.addTexture("spaceship1","textures/shop_textures/0_spaceship.png");
        GameApp.addTexture("leftArrow","textures/shop_textures/arrow_left.png");
        GameApp.addTexture("rightArrow","textures/shop_textures/arrow_right.png");
        GameApp.addTexture("spaceship2","textures/shop_textures/1_spaceship.png");
        GameApp.addTexture("spaceship3","textures/shop_textures/spaceship3.png");
        GameApp.addTexture("spaceship4","textures/shop_textures/spaceship4.png");
        GameApp.addTexture("arrow", "textures/shop_textures/back_arrow.png");

    }


    @Override
    public void render(float delta) {
        super.render(delta);




        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("customization_background",0,0,getWorldWidth(),getWorldHeight());
        GameApp.drawTextCentered("Pixel_Emulator","Customization Screen",getWorldWidth() / 2,620,"white");
        GameApp.drawTextCentered("Pixel_Emulator","Select your spaceship",getWorldWidth() / 2,550,"white");

        String currShip = ships[index];
        GameApp.drawTextureCentered(currShip,getWorldWidth() / 2,getWorldHeight() / 2f, GameScreen.SPACESHIP_SIZE + 50, GameScreen.SPACESHIP_SIZE + 50);


        GameApp.drawTexture("leftArrow",50,getWorldHeight() / 2f - 25,40, 50);
        GameApp.drawTexture("rightArrow",getWorldWidth() - 40-60 ,getWorldHeight() / 2f - 25, 40, 50);

        GameApp.drawTexture("arrow", 20,getWorldHeight() - 60, 40, 40);

        float mouseX = getMouseX();
        float mouseY = getMouseY();


        float leftX = 50;
        float leftY = getWorldHeight() / 2f - 25;
        float leftW = 40;
        float leftH = 50;

        float rightX = getWorldWidth() - 40 - 60;
        float rightY = getWorldHeight() / 2f - 25;
        float rightW = 40;
        float rightH = 50;

        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE) || (GameApp.pointInRect(mouseX, mouseY, 20, getWorldHeight() - 60, 40, 40) && GameApp.isButtonJustPressed(Input.Buttons.LEFT))) {
            GameApp.switchScreen("MainMenuScreen");
        }

        if (GameApp.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (GameApp.pointInRect(mouseX, mouseY, leftX, leftY, leftW, leftH)) {
                index--;
                if (index < 0) {
                    index = ships.length - 1;
                }


            } else if ((GameApp.isButtonJustPressed(Input.Buttons.LEFT))) {
                if (GameApp.pointInRect(mouseX, mouseY, rightX, rightY, rightW, rightH)) {
                    index++;
                    if (index >= ships.length) {
                        index = 0;
                    }
                }
            }
            GameScreen.activeSpaceship = index;
        }

        if (GameApp.isKeyJustPressed(Input.Keys.ENTER) || GameApp.isKeyJustPressed(Input.Keys.ESCAPE)){
            GameApp.switchScreen("MainMenuScreen");
        }

        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {

    }

}
