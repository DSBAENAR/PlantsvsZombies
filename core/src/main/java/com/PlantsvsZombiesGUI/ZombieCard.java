package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Rectangle;

public class ZombieCard extends Actor {
    private Animation<TextureRegion> animation;
    private float stateTime;
    private Texture texture;
    private int[] position; // Posición lógica del zombie
    private boolean isAlive;
    private boolean isAnimated;
    private int health; // Salud del zombie
    private float attackCooldown = 1.5f; // Tiempo entre ataques en segundos
    private float attackTimer = 0f; // Temporizador para el ataque


    public ZombieCard(float x, float y, Texture spriteSheet, int frameCols, int frameRows, float frameDuration, int initialHealth) {
        this.stateTime = 0f;
        this.texture = spriteSheet;
        this.position = new int[]{(int) (y / GameScreen.TILE_SIZE), (int) (x / GameScreen.TILE_SIZE)};
        this.isAlive = true;
        this.isAnimated = true;
        this.health = initialHealth; // Inicializar la salud
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

    public int[] getPosition() {
        return position;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isAlive() {
        return isAlive;
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

        if (!isAlive) {
            remove();
            return;
        }

        // Incrementar el temporizador del ataque
        attackTimer += delta;

        // Mover el zombie hacia la izquierda si no está atacando
        if (attackTimer >= attackCooldown) {
            float newX = getX() - 50 * delta; // Velocidad del zombie
            setX(newX);
        }

        // Actualizar la posición lógica del zombie
        int col = (int) ((getX() - GameScreen.GRID_X_OFFSET) / GameScreen.TILE_SIZE);
        position[1] = Math.max(col, 0); // Evita que salga del tablero

        // Comprobar colisión con plantas
        for (Actor actor : getStage().getActors()) {
            if (actor instanceof PlantCard) {
                PlantCard plantCard = (PlantCard) actor;

                // Verificar colisión
                if (collidesWithPlant(plantCard)) {
                    // Detener el movimiento y atacar
                    if (attackTimer >= attackCooldown) {
                        attackPlant(plantCard); // Atacar la planta
                        attackTimer = 0f; // Reiniciar el temporizador del ataque
                    }
                    return; // Detener el movimiento mientras ataca
                }
            }
        }

        // Detener al zombie si llega al borde izquierdo
        if (getX() < GameScreen.GRID_X_OFFSET) {
            setX(GameScreen.GRID_X_OFFSET); // Mantén al zombie en el borde
            isAlive = false; // Considerar al zombie "muerto" o detenido
            System.out.println("Zombie alcanzó el borde izquierdo.");
        }
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth(int amount) {
        if (isAlive) {
            health -= amount; // Reducir la salud
            System.out.println("Zombie dañado. Salud restante: " + health);
            if (health <= 0) {
                isAlive = false; // Marcar como muerto
                remove(); // Eliminar del escenario
                System.out.println("Zombie eliminado.");
            }
        }
    }

    private boolean collidesWithPlant(PlantCard plantCard) {
        return getBoundingRectangle().overlaps(plantCard.getBoundingRectangle());
    }
    
    public Rectangle getBoundingRectangle() {
        // Ajustar los márgenes del colider si el sprite incluye áreas transparentes
        float offsetX = getWidth() * 0.1f; // Reducir los lados
        float offsetY = getHeight() * 0.1f; // Reducir la parte superior e inferior
        float adjustedWidth = getWidth() * 0.8f; // Ajustar el ancho
        float adjustedHeight = getHeight() * 0.8f; // Ajustar la altura
        return new Rectangle(getX() + offsetX, getY() + offsetY, adjustedWidth, adjustedHeight);
    }

    public void attackPlant(PlantCard plantCard) {
        if (plantCard.getPlantLogic() != null) {
            // Reducir la vida de la planta
            plantCard.getPlantLogic().reduceHealth(100); // Cada mordida inflige 100 puntos de daño
            System.out.println("Zombie atacó a una planta. Salud restante de la planta: " + plantCard.getPlantLogic().getHealth());

            // Si la planta muere, eliminarla
            if (plantCard.getPlantLogic().getHealth() <= 0) {
                plantCard.remove();
                System.out.println("Planta destruida por el zombie.");
            }
        }
    }

	public int getRowFromPosition(float y) {
		return (int) ((y - GameScreen.GRID_Y_OFFSET) / GameScreen.TILE_SIZE);
	}

   
}
