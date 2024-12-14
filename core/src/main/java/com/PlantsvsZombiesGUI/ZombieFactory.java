package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.graphics.Texture;

public class ZombieFactory {

    public static ZombieCard createZombie(String zombieType, int row, int col, float tileSize) {
        Texture zombieSheet;
        int frameCols, frameRows;
        float frameDuration;

        try {
            // Calcular posiciones visuales
            float adjustedX = GameScreen.GRID_X_OFFSET + col * tileSize + tileSize / 2;
            float adjustedY = GameScreen.GRID_Y_OFFSET + row * tileSize + tileSize / 2;

            // Configurar los valores según el tipo de zombie
            if (zombieType.equalsIgnoreCase("NormalZombie")) {
                zombieSheet = new Texture("NormalZombiePrepare.png");
                frameCols = 15;
                frameRows = 1;
                frameDuration = 0.1f;
                return new ZombieCard(adjustedX, adjustedY, zombieSheet, frameCols, frameRows, frameDuration,100);

            } else if (zombieType.equalsIgnoreCase("Brainstein")) {
                zombieSheet = new Texture("BrainsteinSprite.png");
                frameCols = 8;
                frameRows = 1;
                frameDuration = 0.05f;
                return new ZombieCard(adjustedX, adjustedY, zombieSheet, frameCols, frameRows, frameDuration,100);

            } else if (zombieType.equalsIgnoreCase("Buckethead")) {
                zombieSheet = new Texture("BucketheadSprite.png");
                frameCols = 47;
                frameRows = 1;
                frameDuration = 0.15f;
                return new ZombieCard(adjustedX, adjustedY, zombieSheet, frameCols, frameRows, frameDuration,800);
               

            }
            else if (zombieType.equalsIgnoreCase("Conehead")) {
                zombieSheet = new Texture("ConeheadSprite.png");
                //iria 1 frame cols
                frameCols = 47;
                frameRows = 1;
                frameDuration = 0.15f;
                return new ZombieCard(adjustedX, adjustedY, zombieSheet, frameCols, frameRows, frameDuration,380);
            }
            else {
                throw new IllegalArgumentException("Tipo de zombie desconocido: " + zombieType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al crear el zombie de tipo: " + zombieType);
            return null; // En caso de error, retorna null
        }
    }
}
