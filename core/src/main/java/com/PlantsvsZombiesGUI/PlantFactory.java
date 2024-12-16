
package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.graphics.Texture;
import com.PlantsvsZombiesDomain.*;

public class PlantFactory {
    public static PlantCard createPlant(String plantType, int x, int y, Board board) {
        try {
            if (plantType.equals("PeaShooter")) {
                int price = 100;
                if (GameManager.getGameManager().spendSun(price)) {
                    AttackPlant peashooterLogic = new PeaShooter(new int[]{x, y}, new HumanPlayer("Player 1",50,true), board);
                    Texture spriteSheet = new Texture("PeaShooterSprite.png"); // Ruta a tu sprite
                    return new PlantCard(x, y, spriteSheet, 25, 1, 0.05f, peashooterLogic);
                } else {
                    System.out.println("No tienes suficientes soles para plantar un PeaShooter.");
                }
            } else if (plantType.equals("WallNut")) {
                int price = 50;
                if (GameManager.getGameManager().spendSun(price)) {
                    DefensePlant wallNutLogic = new WallNut(new int[]{x, y}, null, board);
                    Texture spriteSheet = new Texture("WallNutSprite.png"); // Ruta a tu sprite
                    return new PlantCard(x, y, spriteSheet, 17, 1, 0.05f, wallNutLogic);
                } else {
                    System.out.println("No tienes suficientes soles para plantar un WallNut.");
                }
            } else if (plantType.equals("Sunflower")) {
                int price = 50;
                if (GameManager.getGameManager().spendSun(price)) {
                    UtilityPlant sunflowerLogic = new SunFlower(new int[]{x, y},  new HumanPlayer("Player 1",50,true), board);
                    Texture spriteSheet = new Texture("SunflowerSprite.png"); // Ruta a tu sprite
                    return new PlantCard(x, y, spriteSheet, 25, 1, 0.05f, sunflowerLogic);
                } else {
                    System.out.println("No tienes suficientes soles para plantar un Sunflower.");
                }
            }
            
            else if (plantType.equals("PotatoMine")) {
                int price = 50;
                if (GameManager.getGameManager().spendSun(price)) {
                    PotatoMine potatoLogic = new PotatoMine(new int[]{x, y},  new HumanPlayer("Player 1",50,true), board);
                    Texture spriteSheet = new Texture("PotatoMineSprite.png"); // Ruta a tu sprite
                    return new PlantCard(x, y, spriteSheet, 11, 1, 0.05f, potatoLogic);
                } else {
                    System.out.println("No tienes suficientes soles para plantar un Sunflower.");
                }
            }
            
            
            else if (plantType.equals("ECIPlant")) {
                int price = 50;
                if (GameManager.getGameManager().spendSun(price)) {
                    ECIPlant eciLogic = new ECIPlant(new int[]{x, y},  new HumanPlayer("Player 1",50,true), board);
                    Texture spriteSheet = new Texture("EciPlantSprite.png"); // Ruta a tu sprite
                    return new PlantCard(x, y, spriteSheet, 25, 1, 0.05f, eciLogic);
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
