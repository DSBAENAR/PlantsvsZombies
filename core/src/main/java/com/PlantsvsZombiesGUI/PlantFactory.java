package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.graphics.Texture;

public class PlantFactory {
    public static Plant createPlant(String type, float x, float y, Texture spriteSheet) {
        switch (type) {
            case "Peashooter":
                return new Peashooter(x, y);
            case "Sunflower":
//                return new Sunflower(x, y);
            // case "WallNut":
            //     return new WallNut(x, y, spriteSheet);
            default:
                throw new IllegalArgumentException("Tipo de planta desconocido: " + type);
        }
    }
}
