
package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.graphics.Texture;
import com.PlantsvsZombiesDomain.*;

public class PlantFactory {
    public static PlantCard createPlant(String plantType, int x, int y, Board board) {
        try {
            if (plantType.equals("PeaShooter")) {
                int price = 100;
                if (GameScreen.spendSun(price)) { // Validar si hay suficientes soles
                    AttackPlant peashooterLogic = new PeaShooter(new int[]{x, y}, new HumanPlayer("Player 1",50,true), board);
                    Texture spriteSheet = new Texture("PeaShooterSprite.png"); // Ruta a tu sprite
                    return new PlantCard(x, y, spriteSheet, 25, 1, 0.1f, peashooterLogic);
                } else {
                    System.out.println("No tienes suficientes soles para plantar un PeaShooter.");
                }
            } else if (plantType.equals("WallNut")) {
                int price = 50;
                if (GameScreen.spendSun(price)) { // Validar si hay suficientes soles
                    DefensePlant wallNutLogic = new WallNut(new int[]{x, y}, null, board);
                    Texture spriteSheet = new Texture("WallNutSprite.png"); // Ruta a tu sprite
                    return new PlantCard(x, y, spriteSheet, 25, 1, 0.05f, wallNutLogic);
                } else {
                    System.out.println("No tienes suficientes soles para plantar un WallNut.");
                }
            } else if (plantType.equals("Sunflower")) {
                int price = 50;
                if (GameScreen.spendSun(price)) { // Validar si hay suficientes soles
                    UtilityPlant sunflowerLogic = new SunFlower(new int[]{x, y},  new HumanPlayer("Player 1",50,true), board);
                    Texture spriteSheet = new Texture("SunflowerSprite.png"); // Ruta a tu sprite
                    return new PlantCard(x, y, spriteSheet, 25, 1, 0.05f, sunflowerLogic);
                } else {
                    System.out.println("No tienes suficientes soles para plantar un Sunflower.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
