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

    }

    @Override
    public void render (float delta){
        super.render(delta);

        GameApp.clearScreen();
        GameApp.startSpriteRendering();

        GameApp.drawTexture("ManualBackground",0, 0, getWorldWidth(), getWorldHeight());
        String font = "Pixel_Emulator";
        float left = getWorldWidth() / 2f - 200f;
        float y = getWorldHeight() - 80f;
        float lh = 38f; // line height

        GameApp.drawText(font, "MANUAL", getWorldWidth()/2f - 80f, y, "white"); y -= lh * 1.6f;

        GameApp.drawText(font, "Controls:", left, y, "white"); y -= lh;
        GameApp.drawText(font, "- Move:  Arrow keys", left, y, "white"); y -= lh;
        GameApp.drawText(font, "- Pause: P", left, y, "white"); y -= lh * 1.5f;

        GameApp.drawText(font, "Goals:", left, y, "white"); y -= lh;
        GameApp.drawText(font, "- Destroy aliens, collect power-ups,", left, y, "white"); y -= lh;
        GameApp.drawText(font, "  and avoid getting hit.", left, y, "white"); y -= lh * 1.6f;

        GameApp.drawText(font, "Press ESC or SPACE to return", left, y, "yellow");

        GameApp.endSpriteRendering();

        if (GameApp.isKeyPressed(Input.Keys.SPACE)||GameApp.isKeyPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("MainMenuScreen");
        }
    }

    @Override
    public void hide() {
        GameApp.disposeFont("Pixel_Emulator");
        GameApp.disposeTexture("ManualBackground");

    }
}







