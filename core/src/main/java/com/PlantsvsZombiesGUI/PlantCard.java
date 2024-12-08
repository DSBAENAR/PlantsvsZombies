package com.PlantsvsZombiesGUI;

import com.PlantsvsZombiesDomain.AttackPlant;
import com.PlantsvsZombiesDomain.Plant;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlantCard extends Actor {
    private Plant plantLogic; // Puede ser AttackPlant, DefensePlant, etc.
    private Texture texture;
    private Animation<TextureRegion> animation;
    private float stateTime;
    private boolean isAnimated;

    public PlantCard(float x, float y, Texture spriteSheet, int frameCols, int frameRows, float frameDuration, Plant plantLogic) {
        this.isAnimated = true;
        this.stateTime = 0f;
        this.plantLogic = plantLogic; // Enlace a la lógica

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

        this.animation = new Animation<>(frameDuration, frames);
        this.animation.setPlayMode(Animation.PlayMode.LOOP);

        // Configurar el tamaño del primer frame
        setPosition(x, y);
        setSize(frames[0].getRegionWidth(), frames[0].getRegionHeight());
    }

    public Plant getPlantLogic() {
        return plantLogic;
    }
    

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isAnimated) {
            stateTime += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
        } else {
            batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
     // Verificar si la planta lógica es de ataque
        if (plantLogic instanceof AttackPlant) {
            AttackPlant attackPlant = (AttackPlant) plantLogic;

            // Si no ha iniciado el ataque, empieza
            if (attackPlant.getItsAlive()) {
                attackPlant.startAttack();
            }
        }
    }


}

