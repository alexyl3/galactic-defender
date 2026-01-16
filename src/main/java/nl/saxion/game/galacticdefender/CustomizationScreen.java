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
        GameApp.addTexture("leftArrow","textures/shop_textures/back_arrow.png");
        GameApp.addTexture("rightArrow","textures/shop_textures/right_arrow.png");
        GameApp.addTexture("spaceship2","textures/shop_textures/1_spaceship.png");
        GameApp.addTexture("spaceship3","textures/shop_textures/spaceship3.png");
        GameApp.addTexture("spaceship4","textures/shop_textures/spaceship4.png");



    }


    @Override
    public void render(float delta) {
        super.render(delta);




        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("customization_background",0,0,getWorldWidth(),getWorldHeight());
        GameApp.drawTextCentered("Pixel_Emulator","Customization Screen",getWorldWidth() / 2,620,"white");
        GameApp.drawTextCentered("Pixel_Emulator","Press enter to select ",getWorldWidth() / 2,550,"white");
        GameApp.drawTextCentered("Pixel_Emulator","your spaceship",getWorldWidth() / 2,500,"white");

        String currShip = ships[index];
        GameApp.drawTextureCentered(currShip,getWorldWidth() / 2,getWorldHeight() / 2f, GameScreen.SPACESHIP_SIZE + 50, GameScreen.SPACESHIP_SIZE + 50);


        GameApp.drawTexture("leftArrow",40,getWorldHeight() / 2f - 25,40, 50);
        GameApp.drawTexture("rightArrow",getWorldWidth() - 40-60 ,getWorldHeight() / 2f - 25, 40, 50);



        float mouseX = getMouseX();
        float mouseY = getMouseY();


        float leftX = 40;
        float leftY = getWorldHeight() / 2f - 25;
        float leftW = 40;
        float leftH = 50;

        float rightX = getWorldWidth() - 40 - 60;
        float rightY = getWorldHeight() / 2f - 25;
        float rightW = 40;
        float rightH = 50;

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
        }

        if (GameApp.isKeyJustPressed(Input.Keys.ENTER)){
            GameScreen.activeSpaceship = index;
            GameApp.switchScreen("MainMenuScreen");
        }
        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE)){
            GameApp.switchScreen("MainMenuScreen");
        }



        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {

    }

}
