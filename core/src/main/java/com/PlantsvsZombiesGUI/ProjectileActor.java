package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ProjectileActor extends Actor {
	 private PlantCard parentPlant;
    private Texture texture;
    private float speed = 300; // Velocidad del proyectil (pixeles por segundo)
    private int damage = 20; // Daño del proyectil

    public ProjectileActor(float x, float y, PlantCard parentPlant) {
        this.texture = new Texture("pea.png"); // Cambia "pea.png" por la textura de tu proyectil
        this.parentPlant = parentPlant;
        // Configurar posición inicial y tamaño del proyectil
        setPosition(x, y);
        setSize(40, 40); // Tamaño del proyectil agrandado (ajusta según tus necesidades)
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Mover el proyectil hacia la derecha
        float newX = getX() + speed * delta;
        setX(newX);

        // Si el proyectil sale de la pantalla, eliminarlo
        if (getX() > getStage().getViewport().getWorldWidth()) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public PlantCard getParentPlant() {
        return parentPlant;
    }
}
