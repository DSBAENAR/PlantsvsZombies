package com.PlantsvsZombiesGUI;

import com.PlantsvsZombiesDomain.Zombie;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ZombieCard extends Actor {
	private Animation<TextureRegion> animation;
	private float stateTime;
	private Texture texture;
    private Zombie zombie; // Referencia al objeto lógico Zombie
    private Stage stage;
    private boolean isAnimated;
    

    public ZombieCard(float x, float y,Zombie zombie, Texture spriteSheet, int frameCols, int frameRows, float frameDuration) {
        this.stateTime = 0f;
        this.zombie = zombie; // Enlace a la lógica
        this.stage = stage;
        
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
        
        // Sincronizar posición lógica con la visual
        int[] logicalPosition = zombie.getZombiePosition();
        setPosition(logicalPosition[1] * 100, logicalPosition[0] * 100); // Ajusta escala según tus celdas
        
        // Eliminar el zombie si su salud llega a 0
        if (!zombie.getItsAlive() || zombie.getHealth() <= 0) {
            stage.getActors().removeValue(this, true); // Elimina del escenario
        }
    }

    public Zombie getZombie() {
        return zombie;
    }
}
