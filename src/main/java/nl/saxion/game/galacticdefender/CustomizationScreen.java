package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class CustomizationScreen extends ScalableGameScreen {
    public CustomizationScreen() {
        super(500,800);
    }

    @Override
    public void show() {
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 25);


    }


    @Override
    public void render(float delta) {
        super.render(delta);

        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.drawText("Pixel_Emulator","Customization Screen",90,700,"white");
        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {

    }

}
