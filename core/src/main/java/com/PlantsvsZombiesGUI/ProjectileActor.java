package com.PlantsvsZombiesGUI;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ProjectileActor extends Actor {
    private Texture texture;

    public ProjectileActor(Texture texture, float x, float y) {
        this.texture = texture;
        setPosition(x, y);
        setSize(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    @Override
    public void act(float delta) {
        super.act(delta);

        // Mueve el proyectil hacia la derecha
        float speed = 300 * delta; // Velocidad en píxeles por segundo
        setX(getX() + speed);

        // Si el proyectil se sale de la pantalla, elimínalo
        if (getX() > Gdx.graphics.getWidth()) {
            remove();
        }
    }

}


