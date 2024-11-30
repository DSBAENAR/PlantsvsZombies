package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.graphics.Texture;

public class Peashooter extends PlantCard {
    public Peashooter(float x, float y) {
        // Llamamos al constructor de Plant para configurar la animación
        super(
            x,
            y,
            new Texture("Peashooter-ezgif.com-gif-to-sprite-converter.png"), // Sprite sheet de Peashooter
            25, // Número de columnas en el sprite sheet
            1,  // Número de filas en el sprite sheet
            0.05f // Duración de cada frame (0.1 segundos)
        );
    }
}
