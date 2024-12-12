
package com.PlantsvsZombiesGUI;

import com.PlantsvsZombiesDomain.AttackPlant;
import com.PlantsvsZombiesDomain.Plant;
import com.PlantsvsZombiesDomain.UtilityPlant;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class PlantCard extends Actor {
    private Plant plantLogic; // Puede ser AttackPlant, DefensePlant, etc.
    private Texture texture;
    private Animation<TextureRegion> animation;
    private float stateTime;
    private boolean isAnimated;
    private float shootTimer = 0f; // Tiempo acumulado para disparar
    private float shootInterval = 1.5f; // Intervalo de disparo en segundos
    private boolean hasActiveProjectile = false;

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

    public String getType() {
        if (plantLogic != null) {
            return plantLogic.getClass().getSimpleName(); // Devuelve el nombre de la clase lógica
        }
        return "Unknown"; // Devuelve "Unknown" si no hay lógica asociada
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
            shootTimer += delta;
            // Si no ha iniciado el ataque, empieza
            if (attackPlant.getItsAlive() && shootTimer >= shootInterval) {
            	shootTimer = 0f;
                attackPlant.startAttack();
            }
        }
        
     
        if (plantLogic instanceof UtilityPlant) {
            UtilityPlant utilityPlant = (UtilityPlant) plantLogic;
            utilityPlant.setOnMoneyGeneratedCallback(() -> {
                Gdx.app.postRunnable(() -> {
                    GameScreen.incrementSunCounter(utilityPlant.getMoneySupply());
                });
            });
        }

        // Eliminar la planta si su salud llega a 0
        if (plantLogic.getHealth() <= 0) {
            remove(); // Eliminar del escenario
            System.out.println("Planta eliminada: " + getType());
        }
    }

    public void reduceHealth(int amount) {
        if (plantLogic != null) {
            plantLogic.reduceHealth(amount); // Delegar a la lógica de la planta
            if (!plantLogic.getItsAlive()) {
                remove(); // Eliminar del escenario si la planta está muerta
                System.out.println("Planta eliminada: " + getType());
            }
        }
    }
    
    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public int getHealth() {
        if (plantLogic != null) {
            return plantLogic.getHealth(); // Obtener salud desde la lógica de la planta
        }
        return 0; // Si no hay lógica asociada, devuelve 0
    }
    
    public void checkForZombies(Array<ZombieCard> zombies) {
        float plantY = getY(); // Obtener la posición Y de la planta (fila)
        
        for (ZombieCard zombie : zombies) {
            float zombieY = zombie.getY(); // Obtener la posición Y del zombie
            float zombieX = zombie.getX(); // Obtener la posición X del zombie
            
            // Verifica si el zombie está en la misma fila y delante de la planta
            if (Math.abs(plantY - zombieY) < GameScreen.TILE_SIZE / 2 && zombieX > getX()) {
                // Si hay un zombie en la misma fila, inicia el ataque
                if (plantLogic instanceof AttackPlant) {
                    AttackPlant attackPlant = (AttackPlant) plantLogic;
                    if (attackPlant.getItsAlive()) {
                        attackPlant.startAttack();
                        fireProjectile();
                        System.out.println("Planta " + getType() + " atacando zombie en fila " + (int) (plantY / GameScreen.TILE_SIZE));
                    }
                }
                break; // Salir del bucle después de encontrar un zombie
            }
        }
    }
    
    public void fireProjectile() {
        if (hasActiveProjectile) return; // No disparar si ya hay un proyectil activo

        if (getStage() != null) {
            ProjectileActor projectile = new ProjectileActor(getX() + getWidth(), getY() + getHeight() / 2, this);
            getStage().addActor(projectile);
            hasActiveProjectile = true; // Marca que hay un proyectil activo
            System.out.println("Proyectil disparado por planta " + getType());
        }
    }

    public void setHasActiveProjectile(boolean hasActiveProjectile) {
        this.hasActiveProjectile = hasActiveProjectile;
    }
    
    
}
