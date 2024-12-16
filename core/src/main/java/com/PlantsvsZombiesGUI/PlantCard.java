
package com.PlantsvsZombiesGUI;

import com.PlantsvsZombiesDomain.AttackPlant;
import com.PlantsvsZombiesDomain.ECIPlant;
import com.PlantsvsZombiesDomain.Plant;
import com.PlantsvsZombiesDomain.PotatoMine;
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
            return plantLogic.getClass().getSimpleName();
        }
        return "Unknown"; 
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
        shootTimer += delta;
        if (GameStateManager.isPaused()) {
            return;
        }
        
     // Verificar si la planta lógica es de tipo PotatoMine
        if (plantLogic instanceof PotatoMine) {
            PotatoMine potatoMine = (PotatoMine) plantLogic;

            if (!potatoMine.getItsActive()) {
                System.out.println("PotatoMine aún no está activo.");
                return; // No hacer nada hasta que se active
            }

            // Verificar si hay zombies cercanos y explotar
            checkForZombiesAndExplode();
            return; // Termina aquí porque PotatoMine no tiene otras acciones
        }

     // Verificar si la planta lógica es de ataque
        if (plantLogic instanceof AttackPlant) {
            AttackPlant attackPlant = (AttackPlant) plantLogic;

            if (attackPlant.getItsAlive() && shootTimer >= 1.5f) { // Disparo cada 1.5 segundos estrictos
                shootTimer = 0f; // Reinicia el temporizador
                fireProjectile(); // Llama al método para disparar el proyectil
                attackPlant.startAttack();
                System.out.println("Proyectil disparado por planta " + getType());
            }
        }
        
        
     // Verificar si la planta lógica es de ataque
        if (plantLogic instanceof UtilityPlant) {
        	UtilityPlant utilityPlant = (UtilityPlant) plantLogic;

            if (plantLogic.getHealth() > 0) {
                utilityPlant.setOnMoneyGeneratedCallback(() -> {
                    Gdx.app.postRunnable(() -> {
                        GameManager.incrementSunCounter(utilityPlant.getMoneySupply());
                    });
                });
            } else {
            	utilityPlant.setOnMoneyGeneratedCallback(null);
            }
        }
        

        
     
        if (plantLogic instanceof ECIPlant) {
        	ECIPlant utilityPlant = (ECIPlant) plantLogic;

            if (plantLogic.getHealth() > 0) {
                utilityPlant.setOnMoneyGeneratedCallback(() -> {
                    Gdx.app.postRunnable(() -> {
                        GameManager.incrementSunCounter(utilityPlant.getMoneySupply());
                    });
                });
            } else {
            	utilityPlant.setOnMoneyGeneratedCallback(null);
            }
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
            float zombieY = zombie.getY(); 
            float zombieX = zombie.getX(); 

            // Verifica si el zombie está en la misma fila y delante de la planta
            if (Math.abs(plantY - zombieY) < GameScreen.TILE_SIZE / 2 && zombieX > getX()) {
                if (plantLogic instanceof AttackPlant) {
                    AttackPlant attackPlant = (AttackPlant) plantLogic;
                    if (attackPlant.getItsAlive()) {
                        attackPlant.startAttack();
                        System.out.println("Planta " + getType() + " detectó zombie en fila " + (int) (plantY / GameScreen.TILE_SIZE));
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
        }
    }

    public void setHasActiveProjectile(boolean hasActiveProjectile) {
        this.hasActiveProjectile = hasActiveProjectile;
    }
    

    private void checkForZombiesAndExplode() {
        if (!(plantLogic instanceof PotatoMine)) return;

        PotatoMine potatoMine = (PotatoMine) plantLogic;

        if (!potatoMine.getItsActive()) {
            return; // La mina no está activa aún
        }

        // Verificar si el PlantCard está asociado a un Stage
        if (getStage() == null) {
            System.out.println("El PlantCard no está asociado a ningún Stage.");
            return; // Salir si no hay Stage
        }

        // Verificar colisión con zombies
        Array<Actor> actors = getStage().getActors();
        for (Actor actor : actors) {
            if (actor instanceof ZombieCard) {
                ZombieCard zombie = (ZombieCard) actor;

                if (collidesWithZombie(zombie)) {
                    explodePotatoMine(zombie);
                    return;
                }
            }
        }
    }



	
    private void explodePotatoMine(ZombieCard zombie) {
        if (zombie == null) {
            System.out.println("Zombie es null, no se puede explotar.");
            return;
        }

        PotatoMine potatoMine = (PotatoMine) plantLogic;
        if (potatoMine == null) {
            System.out.println("PotatoMine lógica es null.");
            return;
        }

        potatoMine.setItsAlive(false); // Marca la mina como destruida
        potatoMine.setItsActive(false); // Desactiva la mina
        remove(); // Eliminar la mina del escenario
        zombie.reduceHealth(1000); // Daño masivo al zombie
        System.out.println("PotatoMine explotó y destruyó un zombie.");
    }

	
	private boolean collidesWithZombie(ZombieCard zombie) {
	    return getBoundingRectangle().overlaps(zombie.getBoundingRectangle());
	}
    
    
}
