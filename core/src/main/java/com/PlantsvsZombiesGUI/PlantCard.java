package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public  class PlantCard extends Actor {
    private Texture texture; // Para plantas estáticas
    private Animation<TextureRegion> animation; // Para plantas animadas
    private float stateTime; // Tiempo acumulado para la animación
    private boolean isAnimated; // Indica si esta planta tiene una animación
    // Constructor para animaciones
    public PlantCard(float x, float y, Texture spriteSheet, int frameCols, int frameRows, float frameDuration) {
        this.isAnimated = true;
        this.stateTime = 0f;

        // Dividir el sprite sheet en frames
        TextureRegion[][] tmp = TextureRegion.split(
            spriteSheet,
            spriteSheet.getWidth() / frameCols,
            spriteSheet.getHeight() / frameRows
        );

        // Convertir la matriz en un array lineal
        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        // Crear la animación
        this.animation = new Animation<>(frameDuration, frames);
        this.animation.setPlayMode(Animation.PlayMode.LOOP);

        // Configurar el tamaño del primer frame
        setPosition(x, y);
        setSize(frames[0].getRegionWidth(), frames[0].getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isAnimated) {
            // Dibujar la animación
            stateTime += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
        } else {
            // Dibujar la textura estática
            batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        }
    }
    
    
    @Override
    public void act(float delta) {
        super.act(delta);
        // Lógica adicional para plantas
        
    }
}
