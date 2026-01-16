package nl.saxion.game.galacticdefender;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopScreen extends ScalableGameScreen {
    public static final int itemsInShop = 3;
    public static final int itemSize = 100;
    public static final ArrayList<Integer> prices = new ArrayList<>(Arrays.asList(0, 40, 80));
    public static ArrayList<Integer> availableSpaceships = new ArrayList<>(List.of(0));
    ArrayList<String> descriptions = new ArrayList<>(Arrays.asList("Basic", "Double bullets", "Asteroid protection"));
    public ShopScreen() {
        super(500,800);
    }

    @Override
    public void show() {
        GameApp.addFont("Pixel_Emulator", "fonts/Pixel_Emulator.otf", 25);
        GameApp.addFont("Pixel_Emulator_B", "fonts/Pixel_Emulator.otf", 40);
        GameApp.addTexture("shop_background", "textures/Other_Backgrounds/shop_background.png");
        GameApp.addTexture("arrow", "textures/shop_textures/back_arrow.png");
        GameApp.addTexture("active", "textures/shop_textures/active_button.png");
        GameApp.addTexture("activate", "textures/shop_textures/activate_button.png");
        GameApp.addTexture("buy", "textures/shop_textures/buy_button.png");
        GameApp.addTexture("coin","textures/basic_textures/coin.png");
        for (int i = 0; i < itemsInShop; i++) {
            GameApp.addTexture("item" + i,"textures/shop_textures/" + i + "_spaceship.png");
        }
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        handlePlayerInput();
        GameApp.clearScreen();
        GameApp.startSpriteRendering();
        GameApp.drawTexture("shop_background",0,0,getWorldWidth(),getWorldHeight());
        GameApp.drawText("Pixel_Emulator", "coins: " + GameScreen.coin_display,  getWorldWidth() - 180, getWorldHeight() - 55, "white");
        GameApp.drawTexture("arrow", 20,getWorldHeight() - 60, 40, 40);
        GameApp.drawText("Pixel_Emulator_B","Shop",120,GameApp.getWorldHeight() - 55,"white");
        for (int i = 0; i < itemsInShop; i++) {
            GameApp.drawTexture("item" + i, 40, getWorldHeight() - 100 - (1 + i) * (60 + itemSize), itemSize, itemSize);
            if (i == GameScreen.activeSpaceship) {
                GameApp.drawTexture("active", 300, getWorldHeight() - 100 - (1 + i) * (60 + itemSize));
            } else if (availableSpaceships.contains(i)) {
                GameApp.drawTexture("activate", 300, getWorldHeight() - 100 - (1 + i) * (60 + itemSize));
            } else {
                GameApp.drawTexture("coin",180, getWorldHeight() - 100 - (1 + i) * (40 + itemSize), 20, 20 );
                GameApp.drawText("Pixel_Emulator", prices.get(i) + "" ,220, getWorldHeight() - 100 - (1 + i) * (60 + itemSize), "white");
                GameApp.drawTexture("buy", 300, getWorldHeight() - 100 - (1 + i) * (60 + itemSize));
            }
            GameApp.drawText("Pixel_Emulator", descriptions.get(i), 40, getWorldHeight() + 20 - (1 + i) * (60 + itemSize), "white");
        }

        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {
        GameApp.disposeFont("Pixel_Emulator");
        GameApp.disposeFont("Pixel_Emulator_B");
        GameApp.disposeTexture("shop_background");
        GameApp.disposeTexture("arrow");
        for (int i = 0; i < itemsInShop; i++) {
            GameApp.disposeTexture("item" + i);
        }
        GameApp.disposeTexture("active");
        GameApp.disposeTexture("activate");
        GameApp.disposeTexture("buy");
        GameApp.disposeTexture("coin");
    }

    public void handlePlayerInput() {
        float mouseX = getMouseX();
        float mouseY = getMouseY();

        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE) || (GameApp.pointInRect(mouseX, mouseY, 20, getWorldHeight() - 60, 40, 40) && GameApp.isButtonJustPressed(Input.Buttons.LEFT))) {
            GameApp.switchScreen("MainMenuScreen");
        }
        for (int i = 0; i < itemsInShop; i++) {
            if (GameApp.isButtonJustPressed(Input.Buttons.LEFT) && GameApp.pointInRect(mouseX, mouseY, 300, getWorldHeight() - 100 - (1 + i) * (60 + itemSize), 180, 70)) {
                if (availableSpaceships.contains(i) && GameScreen.activeSpaceship != i) {
                    GameScreen.activeSpaceship = i;
                } else if (!availableSpaceships.contains(i) && GameScreen.coin_display >= prices.get(i)) {
                    GameScreen.coin_display -= prices.get(i);
                    availableSpaceships.add(i);
                }
            }

        }

    }
}
