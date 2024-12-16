package com.PlantsvsZombiesGUI;

import com.PlantsvsZombiesDomain.*;
import com.badlogic.gdx.graphics.Texture;

public class ZombieFactory {
    private static boolean validatePrice = false;

    public static void setValidatePrice(boolean validate) {
        validatePrice = validate;
    }

    public static ZombieCard createZombie(String zombieType, int row, int col, Board board) {
        try {
            int price = getZombiePrice(zombieType); // Obtén el precio del zombie
            
            // Verificar el precio solo si está habilitado
            if (validatePrice && !GameManager.getGameManager().spendBrain(price)) {
                System.out.println("No tienes suficientes cerebros para crear un " + zombieType);
                return null; // No se puede crear el zombie
            }
            
            // Crear el zombie si el jugador tiene suficientes cerebros
            if (zombieType.equalsIgnoreCase("NormalZombie")) {
                NormalZombie normalZombieLogic = new NormalZombie(new int[]{row, col}, new HumanPlayer("Player 1", 50, true), board);
                Texture spriteSheet = new Texture("NormalZombiePrepare.png");
                return new ZombieCard(
                        calculateX(col),
                        calculateY(row),
                        spriteSheet,
                        15, 1, 0.05f,
                        normalZombieLogic
                );
            } else if (zombieType.equalsIgnoreCase("Brainstein")) {
                BrainsteinZombie brainsteinLogic = new BrainsteinZombie(new int[]{row, col}, new HumanPlayer("Player 1", 50, true), board);
                Texture spriteSheet = new Texture("BrainsteinSprite.png");
                return new ZombieCard(
                        calculateX(col),
                        calculateY(row),
                        spriteSheet,
                        15, 1, 0.05f,
                        brainsteinLogic
                );
            } else if (zombieType.equalsIgnoreCase("Buckethead")) {
                Buckethead bucketheadLogic = new Buckethead(new int[]{row, col}, new HumanPlayer("Player 1", 50, true), board);
                Texture spriteSheet = new Texture("BucketheadSprite.png");
                return new ZombieCard(
                        calculateX(col),
                        calculateY(row),
                        spriteSheet,
                        47, 1, 0.05f,
                        bucketheadLogic
                );
            } else if (zombieType.equalsIgnoreCase("Conehead")) {
                Conehead coneheadLogic = new Conehead(new int[]{row, col}, new HumanPlayer("Player 1", 50, true), board);
                Texture spriteSheet = new Texture("ConeheadSprite.png");
                return new ZombieCard(
                        calculateX(col),
                        calculateY(row),
                        spriteSheet,
                        47, 1, 0.05f,
                        coneheadLogic
                );
            } else {
                System.out.println("Tipo de zombie desconocido: " + zombieType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para definir el precio de cada tipo de zombie
    private static int getZombiePrice(String zombieType) {
        if (zombieType.equalsIgnoreCase("NormalZombie")) return 100;
        if (zombieType.equalsIgnoreCase("Brainstein")) return 200;
        if (zombieType.equalsIgnoreCase("Buckethead")) return 300;
        if (zombieType.equalsIgnoreCase("Conehead")) return 150;
        return 0; // Por defecto si no se encuentra el tipo
    }

    private static float calculateX(int col) {
        return GameScreen.GRID_X_OFFSET + col * GameScreen.TILE_SIZE + GameScreen.TILE_SIZE / 2;
    }

    private static float calculateY(int row) {
        return GameScreen.GRID_Y_OFFSET + row * GameScreen.TILE_SIZE + GameScreen.TILE_SIZE / 2;
    }
}
