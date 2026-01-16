package nl.saxion.game.galacticdefender;
import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class ManualScreen extends ScalableGameScreen {

    public ManualScreen() {
        super(500,800);
    }

    @Override
    public void show(){
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 25);
        GameApp.addTexture("ManualBackground", "textures/Other_graphics/ManualBackground.png");
        GameApp.addTexture("arrow", "textures/shop_textures/back_arrow.png");
    }

    @Override
    public void render (float delta){
        super.render(delta);

        float mouseX = getMouseX();
        float mouseY = getMouseY();

        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE) || (GameApp.pointInRect(mouseX, mouseY, 20, getWorldHeight() - 60, 40, 40) && GameApp.isButtonJustPressed(Input.Buttons.LEFT))) {
            GameApp.switchScreen("MainMenuScreen");
        }

        GameApp.clearScreen();
        GameApp.startSpriteRendering();

        GameApp.drawTexture("ManualBackground",0, 0, getWorldWidth(), getWorldHeight());
        String title = "Pixel_Emulator";
        float textX1 = getWorldWidth() / 2f - 200f;
        float textY1= getWorldHeight() - 80f;

        GameApp.drawText(title, "MANUAL", textX1+160,textY1+40 , "yellow-300");


        GameApp.drawText(title, "Controls:", textX1, textY1-260, "yellow-300");
        GameApp.drawText(title, "- Move:  Arrow keys", textX1-30, textY1-320, "white");
        GameApp.drawText(title, "- Pause: P", textX1-30,textY1-360, "white");

        GameApp.drawText(title, "Goals:", textX1, textY1-460, "yellow-300");
        GameApp.drawText(title, "- Destroy aliens", textX1-30, textY1-520, "white");
        GameApp.drawText(title, "- collect power-ups", textX1-30, textY1-560, "white");
        GameApp.drawText(title, "- and avoid getting hit.", textX1-30, textY1-600, "white");

        GameApp.drawText(title, "Press SPACE to return", textX1, textY1-700, "yellow-300");
        GameApp.drawTexture("arrow", 20,getWorldHeight() - 60, 40, 40);

        GameApp.endSpriteRendering();

        if (GameApp.isKeyPressed(Input.Keys.SPACE)) {
            GameApp.switchScreen("MainMenuScreen");
        }
    }

    @Override
    public void hide() {
        GameApp.disposeFont("Pixel_Emulator");
        GameApp.disposeTexture("ManualBackground");
        GameApp.disposeTexture("arrow");

    }
}