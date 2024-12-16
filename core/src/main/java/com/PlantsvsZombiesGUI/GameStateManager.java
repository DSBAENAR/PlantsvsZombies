package com.PlantsvsZombiesGUI;

public class GameStateManager {
    public enum GameState {
        PREPARATION_PLANTS,
        ROUND_1_ZOMBIES,
        ROUND_2_PLANTS,
        ROUND_2_ZOMBIES,
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    private static GameState currentState = GameState.PREPARATION_PLANTS;

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

    public static boolean isPreparation() {
        return currentState == GameState.PREPARATION_PLANTS || currentState == GameState.ROUND_2_PLANTS;
    }

    public static boolean isZombiePhase() {
        return currentState == GameState.ROUND_1_ZOMBIES || currentState == GameState.ROUND_2_ZOMBIES;
    }
    
    public static boolean isPlantPhase() {
        return currentState == GameState.PREPARATION_PLANTS || currentState == GameState.ROUND_2_PLANTS;
    }

}
