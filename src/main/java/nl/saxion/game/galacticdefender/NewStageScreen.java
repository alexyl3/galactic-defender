package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class NewStageScreen extends ScalableGameScreen {
    float centreY;
    float text1Y;
    float text2Y;
    float centreY2;

    public NewStageScreen() {
        super(500, 800);
    }
    @Override
    public void show() {
        centreY = getWorldHeight()/2f+60;
        centreY2 = getWorldHeight()/2f-30;
        text1Y = getWorldHeight();
        text2Y = 0;
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 25);
        GameApp.addTexture("GameOverBackground", "textures/Other_Backgrounds/GameOverBackground.png");
    }


    @Override
    public void render(float delta) {
        super.render(delta);


        if (text1Y > centreY){
            text1Y -= delta*200;
            if(text1Y < centreY){
                text1Y = centreY;
            }
        }

        if(text2Y < centreY2){
            text2Y += delta*200;
            if(text2Y>centreY2){
                text2Y = centreY2;
            }

        }

        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("GameOverBackground",0,0,getWorldWidth(),getWorldHeight());
        GameApp.drawTextCentered("Pixel_Emulator", "Round " + GameScreen.STAGE , 300,text1Y, "white");
        GameApp.drawTextCentered("Pixel_Emulator", "Complete " , 300, text2Y, "white");
        GameApp.drawTextCentered("Pixel_Emulator", "Press SPACE \n to continue", 300, getWorldHeight() / 2 - 100, "white");

        GameApp.endSpriteRendering();

        if (GameApp.isKeyPressed(Input.Keys.SPACE)){
            GameApp.switchScreen("GameScreen");
        }

    }


    @Override
    public void hide() {
        GameApp.disposeTexture("GameOverBackground");
        GameApp.disposeFont("Pixel_Emulator");
    }

}
