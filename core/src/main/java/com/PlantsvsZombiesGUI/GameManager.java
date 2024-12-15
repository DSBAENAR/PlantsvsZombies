package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.Gdx;

public class GameManager {
    private static GameManager gameManager; // Singleton
    private static int sunCounter; // Contador de soles
	private static Runnable onSunCounterChange;
	GameManager(int sunCounter) {
    	GameManager.sunCounter = sunCounter;
    }

    public static GameManager getGameManager() {
        if (gameManager == null) {
        	gameManager = new GameManager(1050);
        }
        return gameManager;
    }

    public int getSunCounter() {
        return sunCounter;
    }

    public void setSunCounter(int amount) {
        sunCounter = amount;
    }

    public static void incrementSunCounter(int amount) {
        sunCounter += amount;
        notifySunCounterChange();
        System.out.println("Soles incrementados: " + amount + ". Total soles: " + sunCounter);
    }
    public boolean spendSun(int amount) {
        if (sunCounter >= amount) {
            sunCounter -= amount;
            notifySunCounterChange();
            System.out.println("Soles gastados: " + amount + ". Soles restantes: " + sunCounter);
            return true;
        }
        return false;
    }
    
    private static void notifySunCounterChange() {
        if (onSunCounterChange != null) {
            Gdx.app.postRunnable(onSunCounterChange); // Ejecutar en el hilo principal de LibGDX
        }
    }

    public void setOnSunCounterChange(Runnable listener) {
    	onSunCounterChange = listener;
    }
}

