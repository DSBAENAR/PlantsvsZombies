package com.PlantsvsZombiesGUI;

import com.PlantsvsZombiesDomain.Board;
import com.badlogic.gdx.graphics.Texture;
import com.PlantsvsZombiesDomain.*;

public class PlantFactory {
	public static PlantCard createPlant(String plantType, int x, int y, Board board) {
	    try {

	        if (plantType.equals("Peashooter")) {
	            AttackPlant peashooterLogic = new PeaShooter(new int[]{x, y}, null, board);
	            Texture spriteSheet = new Texture("PeaShooterSprite.png"); // Ruta a tu sprite
	            return new PlantCard(x, y, spriteSheet, 25, 1, 0.05f, peashooterLogic);
	        } else if (plantType.equals("WallNut")) {
	            DefensePlant wallNutLogic = new WallNut(new int[]{x, y}, null, board);
	            Texture spriteSheet = new Texture("WallNutSprite.png"); // Ruta a tu sprite
	            return new PlantCard(x, y, spriteSheet, 1, 1, 0.0f, wallNutLogic);
	        } else if (plantType.equals("Sunflower")) {
	            UtilityPlant sunflowerLogic = new SunFlower(new int[]{x, y}, null, board);
	            Texture spriteSheet = new Texture("SunflowerSprite.png"); // Ruta a tu sprite
	            return new PlantCard(x, y, spriteSheet, 4, 1, 0.1f, sunflowerLogic);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}

