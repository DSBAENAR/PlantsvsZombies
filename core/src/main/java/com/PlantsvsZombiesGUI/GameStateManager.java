package com.PlantsvsZombiesGUI;

public class GameStateManager {
    public enum GameState {
        RUNNING, PAUSED, GAME_OVER
    }

    private static GameState currentState = GameState.RUNNING;

    public static GameState getCurrentState() {
        return currentState;
    }

    public static void setGameState(GameState newState) {
        currentState = newState;
    }

    public static boolean isPaused() {
        return currentState == GameState.PAUSED;
    }

    public static boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public static boolean isGameOver() {
        return currentState == GameState.GAME_OVER;
    }
}
