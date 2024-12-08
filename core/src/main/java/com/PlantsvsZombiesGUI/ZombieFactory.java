package com.PlantsvsZombiesGUI;

import com.PlantsvsZombiesDomain.Board;
import com.PlantsvsZombiesDomain.BrainsteinZombie;
import com.PlantsvsZombiesDomain.Buckethead;
import com.PlantsvsZombiesDomain.HelmetZombie;
import com.PlantsvsZombiesDomain.NormalZombie;
import com.PlantsvsZombiesDomain.Zombie.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ZombieFactory {
    public static ZombieCard createZombie(String zombieType, int x, int y, Board board) {
        Texture zombieSheet;
        int frameCols, frameRows;
        float frameDuration;

        try {
            // Configurar los valores según el tipo de zombie
            if (zombieType.equalsIgnoreCase("Normal")) {
                NormalZombie normalZombie = new NormalZombie(new int[]{x, y}, null, board);
                zombieSheet = new Texture("NormalZombiePrepare.png"); // Ruta a tu sprite
                frameCols = 15;
                frameRows = 1;
                frameDuration = 0.1f;
                return new ZombieCard(x, y, normalZombie, zombieSheet, frameCols, frameRows, frameDuration);
            } 
            else if (zombieType.equalsIgnoreCase("Brainstein")) {
                BrainsteinZombie fastZombie = new BrainsteinZombie(new int[]{x, y}, null, board);
                zombieSheet = new Texture("FastZombieSprite.png"); // Ruta a tu sprite
                frameCols = 8;
                frameRows = 1;
                frameDuration = 0.05f; // Animación más rápida
                return new ZombieCard(x, y, fastZombie, zombieSheet, frameCols, frameRows, frameDuration);
            } 
            else if (zombieType.equalsIgnoreCase("Bucket")) {
                HelmetZombie Helmet = new Buckethead(new int[]{x, y}, null, board);
                zombieSheet = new Texture("StrongZombieSprite.png"); // Ruta a tu sprite
                frameCols = 6;
                frameRows = 1;
                frameDuration = 0.15f; // Animación más lenta
                return new ZombieCard(x, y, Helmet, zombieSheet, frameCols, frameRows, frameDuration);
            } 
            else {
                throw new IllegalArgumentException("Tipo de zombie desconocido: " + zombieType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // En caso de error, retorna null
        }
    }
}
