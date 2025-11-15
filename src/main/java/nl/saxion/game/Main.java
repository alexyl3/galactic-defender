package nl.saxion.game;

import nl.saxion.game.galacticdefender.GameScreen;
import nl.saxion.game.galacticdefender.MainMenuScreen;
import nl.saxion.gameapp.GameApp;

public class Main {
    public static void main(String[] args) {
        // Add screens
        GameApp.addScreen("MainMenuScreen", new MainMenuScreen());
        GameApp.addScreen("GameScreen", new GameScreen());

        // Start game loop and show main menu screen
        GameApp.start("Your Game Name", 800, 450, 60, false, "MainMenuScreen");
    }
}
