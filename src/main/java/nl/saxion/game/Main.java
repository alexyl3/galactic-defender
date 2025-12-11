package nl.saxion.game;

import nl.saxion.game.galacticdefender.*;
import nl.saxion.gameapp.GameApp;

public class Main {
    public static void main(String[] args) {
        // Add screens
        GameApp.addScreen("MainMenuScreen", new MainMenuScreen());
        GameApp.addScreen("GameScreen", new GameScreen());
        GameApp.addScreen("PauseScreen", new PauseScreen());
        GameApp.addScreen("GameOverScreen", new GameOverScreen());
        GameApp.addScreen("ManualScreen", new ManualScreen());
        GameApp.addScreen("NewStageScreen", new NewStageScreen());

        // Start game loop and show main menu screen
        GameApp.start("Galactic defender", 500, 800, 60, false, "MainMenuScreen");
    }
}
