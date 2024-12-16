package com.PlantsvsZombiesGUI;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LawnMowerActor extends Actor {

    private boolean itsAlive = true;
    private int row;
    private boolean isMoving = false;
    private float speed = 200f; // Velocidad en píxeles por segundo
	private Texture lawnMowerTexture;

    public LawnMowerActor(int row) {
        this.row = row;
        lawnMowerTexture = new Texture("LawnCleaner1.png");
     // Ajustar el tamaño del actor a las dimensiones reales de la imagen
        setSize(lawnMowerTexture.getWidth(), lawnMowerTexture.getHeight());
        setPositionBasedOnGrid();
        setBounds(getX(), getY(), getWidth(), getHeight());
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        if (GameStateManager.isPaused()) {
            return; // No hacer nada si el juego está en pausa
        }

     // Mover la cortadora si está activa
        if (isMoving) {
            float targetX = getStage().getViewport().getWorldWidth(); // Fin del tablero
            float newX = getX() + speed * delta;

            if (newX >= targetX) {
                newX = targetX;
                isMoving = false;
                remove(); // Eliminar la cortadora al salir del tablero
                return;
            }

            setPosition(newX, getY());

            // Eliminar zombies en la fila
            ArrayList<Actor> actorsToRemove = new ArrayList<>();
            for (Actor actor : getStage().getActors()) {
                if (actor instanceof ZombieCard) {
                    ZombieCard zombie = (ZombieCard) actor;
                    if (getRowFromPosition(zombie.getY()) == getRow()) {
                        actorsToRemove.add(zombie);
                    }
                }
            }

            // Eliminar zombies fuera del bucle
            for (Actor zombie : actorsToRemove) {
                zombie.remove();
            }
        } else {
            // Detectar colisiones con zombies si la cortadora no está activa
            for (Actor actor : getStage().getActors()) {
                if (actor instanceof ZombieCard) {
                    ZombieCard zombie = (ZombieCard) actor;
                    if (getBoundingRectangle().overlaps(zombie.getBoundingRectangle())) {
                        activateLawnMower(); // Activar la cortadora
                        break;
                    }
                }
            }
        }
    }
        
    private int getRowFromPosition(float y) {
		// TODO Auto-generated method stub
    	return (int) ((y - GameScreen.GRID_Y_OFFSET) / GameScreen.TILE_SIZE);
	}

	@SuppressWarnings("unused")
	private boolean collidesWith(ZombieCard zombie) {
        return getBoundingRectangle().overlaps(zombie.getBoundingRectangle());
    }

	public Rectangle getBoundingRectangle() {
	    return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}



	public void activateLawnMower() {
	    if (isMoving) return; // Evitar activaciones múltiples
	    isMoving = true;
	    setItsAlive(false); // Marcar la cortadora como inactiva
	}



    private void setItsAlive(boolean alive) {
		this.itsAlive = alive;
		
	}

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(lawnMowerTexture, getX(), getY(), getWidth(), getHeight());
    }

    private void setPositionBasedOnGrid() {
        float x = GameScreen.GRID_X_OFFSET - GameScreen.TILE_SIZE + 50; // Posición en la columna izquierda del grid
        float y = GameScreen.GRID_Y_OFFSET + row * GameScreen.TILE_SIZE; // Alinear verticalmente con la fila
        setPosition(x, y);
    }

    public boolean getItsAlive() {
        return itsAlive;
    }

    public int getRow() {
        return row;
    }
}