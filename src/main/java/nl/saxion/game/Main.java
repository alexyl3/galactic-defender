package nl.saxion.game;

import nl.saxion.game.galacticdefender.GameOverScreen;
import nl.saxion.game.galacticdefender.GameScreen;
import nl.saxion.game.galacticdefender.MainMenuScreen;
import nl.saxion.game.galacticdefender.ManualScreen;
import nl.saxion.gameapp.GameApp;

public class Main {
    public static void main(String[] args) {
        // Add screens
        GameApp.addScreen("MainMenuScreen", new MainMenuScreen());
        GameApp.addScreen("GameScreen", new GameScreen());
        GameApp.addScreen("GameOverScreen", new GameOverScreen());
        GameApp.addScreen("ManualScreen", new ManualScreen());

        // Start game loop and show main menu screen
        GameApp.start("Galactic defender", 500, 800, 60, false, "MainMenuScreen");
    }
}
