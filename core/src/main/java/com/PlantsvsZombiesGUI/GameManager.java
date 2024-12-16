package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.Gdx;

public class GameManager {
    private static GameManager gameManager; // Singleton
    private static int sunCounter; // Contador de soles
    private static int brainCounter; // Contador de cerebros
    private static int totalTime; // Tiempo total del juego

    private static Runnable onSunCounterChange; // Listener para cambios en sunCounter
    private static Runnable onBrainCounterChange; // Listener para cambios en brainCounter

    // Constructor privado para el singleton
    GameManager(int initialSunCounter, int initialBrainCounter) {
        GameManager.sunCounter = initialSunCounter;
        GameManager.brainCounter = initialBrainCounter;
        GameManager.totalTime = 0;
    }

    // Método para obtener la instancia del singleton
    public static GameManager getGameManager() {
        if (gameManager == null) {
            gameManager = new GameManager(0, 0); // Valores iniciales predeterminados
        }
        return gameManager;
    }

    // Getters y Setters
    public int getSunCounter() {
        return sunCounter;
    }

    public static void setSunCounter(int amount) {
        sunCounter = amount;
        notifySunCounterChange();
    }

    public int getBrainCounter() {
        return brainCounter;
    }

    public static void setBrainCounter(int amount) {
        brainCounter = amount;
        notifyBrainCounterChange();
    }

    public int getTotalGameTime() {
        return totalTime;
    }

    public static void setTotalGameTime(int Time) {
        totalTime = Time;
    }

    // Métodos para incrementar y gastar soles
    public static void incrementSunCounter(int amount) {
        sunCounter += amount;
        notifySunCounterChange();
        System.out.println("Soles incrementados: " + amount + ". Total soles: " + sunCounter);
    }

    public boolean spendSun(int amount) {
        if (sunCounter >= amount) {
            GameManager.sunCounter -= amount;
            notifySunCounterChange();
            System.out.println("Soles gastados: " + amount + ". Soles restantes: " + sunCounter);
            return true;
        }
        return false;
    }

    // Métodos para incrementar y gastar cerebros
    public static void incrementBrainCounter(int amount) {
        brainCounter += amount;
        notifyBrainCounterChange();
        System.out.println("Cerebros incrementados: " + amount + ". Total cerebros: " + brainCounter);
    }

    public boolean spendBrain(int amount) {
        if (brainCounter >= amount) {
            GameManager.brainCounter -= amount;
            notifyBrainCounterChange();
            System.out.println("Cerebros gastados: " + amount + ". Cerebros restantes: " + brainCounter);
            return true;
        }
        return false;
    }

    // Listeners para cambios en los contadores
    public void setOnSunCounterChangeListener(Runnable listener) {
        GameManager.onSunCounterChange = listener;
    }

    public void setOnBrainCounterChangeListener(Runnable listener) {
        GameManager.onBrainCounterChange = listener;
    }

    // Métodos privados para notificar cambios
    private static void notifySunCounterChange() {
        if (onSunCounterChange != null) {
            Gdx.app.postRunnable(onSunCounterChange);
        }
    }

    private static void notifyBrainCounterChange() {
        if (onBrainCounterChange != null) {
            Gdx.app.postRunnable(onBrainCounterChange);
        }
    }
}
