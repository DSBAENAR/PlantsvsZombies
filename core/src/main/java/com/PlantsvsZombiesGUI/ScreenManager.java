package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class ScreenManager {
    private static ScreenManager instance;
    private Game game;
    private Screen currentScreen;

    private ScreenManager(Game game) {
        this.game = game;
    }

    public static ScreenManager getInstance(Game game) {
        if (instance == null) {
            instance = new ScreenManager(game);
        }
        return instance;
    }

    public void setScreen(Screen newScreen) {
        if (currentScreen != null) {
            currentScreen.dispose(); // Libera recursos de la pantalla actual.
        }
        currentScreen = newScreen;
        game.setScreen(newScreen); // Cambia la pantalla en el juego.
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }
}

