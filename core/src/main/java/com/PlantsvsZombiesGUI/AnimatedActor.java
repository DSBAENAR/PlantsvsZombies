package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AnimatedActor extends Actor {
    private Animation<TextureRegion> animation;
    private float stateTime;
    private float x, y, width, height;

    public AnimatedActor(Animation<TextureRegion> animation, float x, float y, float width, float height) {
        this.animation = animation;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.stateTime = 0f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta; // Actualizar el tiempo de la animación
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = animation.getKeyFrame(stateTime); // Obtener el frame actual
        batch.draw(frame, x, y, width, height); // Dibujar el frame en la posición
    }
}
