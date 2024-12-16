package com.PlantsvsZombiesGUI;

import com.PlantsvsZombiesDomain.AttackPlant;
import com.PlantsvsZombiesDomain.AttackZombie;
import com.PlantsvsZombiesDomain.UtilityZombie;
import com.PlantsvsZombiesDomain.Zombie;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class ZombieCard extends Actor {
    private Animation<TextureRegion> animation;
    private float stateTime;
    private Zombie zombieLogic; // Instancia de la lógica del zombie
    private boolean isAnimated;
    private float attackTimer = 0f; // Temporizador para ataques
    private float attackCooldown; // Tiempo entre ataques

    public ZombieCard(float x, float y, Texture spriteSheet, int frameCols, int frameRows, float frameDuration, Zombie zombieLogic) {
        this.stateTime = 0f;
        this.zombieLogic = zombieLogic;
        this.isAnimated = true;
        this.attackCooldown = 1.5f; // Default cooldown

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

        // Configurar el tamaño y posición del zombie
        setPosition(x, y);
        setSize(frames[0].getRegionWidth(), frames[0].getRegionHeight());
    }

    public Zombie getZombieLogic() {
        return zombieLogic;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isAnimated) {
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!zombieLogic.getItsAlive()) {
        	remove();
            return; // No hacer nada si el juego está en pausa o el zombie está muerto
        }
        
        if (GameStateManager.isPaused()) {
            return; // No hacer nada si el juego está en pausa o el zombie está muerto
        }
        

        // Verificar si el zombie es de utilidad
        if (zombieLogic instanceof UtilityZombie) {
            UtilityZombie utilityZombie = (UtilityZombie) zombieLogic;

            if (zombieLogic.getHealth() > 0) {
                utilityZombie.setOnMoneyGeneratedCallback(() -> {
                    Gdx.app.postRunnable(() -> {
                        GameManager.incrementBrainCounter(utilityZombie.getMoneySupply());
                    });
                });
            } else {
                utilityZombie.setOnMoneyGeneratedCallback(null);
            }

            return; // No mover ni atacar si es un UtilityZombie
        }

        attackTimer += delta;
     
        
       
            if (attackTimer >= attackCooldown) {
                float newX = getX() - 25 * delta;
                setX(newX);
            }
        
     
        
     // Verificar si la lógica del zombie es de ataque
        if (zombieLogic instanceof AttackZombie) {
            AttackZombie attackZombie = (AttackZombie) zombieLogic;

            attackTimer += delta; // Incrementar el temporizador de ataque

            if (attackZombie.getItsAlive() && attackTimer >= attackCooldown) { // Ataque cada `attackCooldown` segundos
                attackTimer = 0f; // Reiniciar el temporizador
                attackZombie.attack(); // Ejecutar el ataque del zombie
                System.out.println("Zombie de ataque realizó un ataque.");
            }
        }
        
        
        

     // Comprobar colisión con plantas
        for (Actor actor : getStage().getActors()) {
            if (actor instanceof PlantCard) {
                PlantCard plantCard = (PlantCard) actor;

                if (collidesWithPlant(plantCard)) {
                    // Detener el movimiento y atacar
                    if (attackTimer >= attackCooldown) {
                        attackPlant(plantCard); 
                        attackTimer = 0f;
                    }
                    return; // Detener el movimiento mientras ataca
                }
            }
        }

        
        if (getX() < PVPScreen.GRID_X_OFFSET) {
            setX(PVPScreen.GRID_X_OFFSET); 
            zombieLogic.setItsAlive(false); 
            System.out.println("Zombie alcanzó el borde izquierdo.");
        }
    }
    
    

    public void attackPlant(PlantCard plantCard) {
        if (plantCard.getPlantLogic() != null && zombieLogic != null) {
            // Reducir la salud de la planta utilizando el daño definido en zombieLogic
            int damage = zombieLogic.getDamage();
            plantCard.getPlantLogic().reduceHealth(damage);

            System.out.println("Zombie atacó a una planta. Salud restante de la planta: " 
                               + plantCard.getPlantLogic().getHealth());

            // Verificar si la planta está muerta
            if (plantCard.getPlantLogic().getHealth() <= 0) {
                plantCard.remove(); // Eliminar la planta del escenario
                System.out.println("Planta destruida por el zombie.");
            }
        }
    }


	public int getHealth() {
        return zombieLogic.getHealth(); // Obtener la salud desde la lógica
    }

    public void reduceHealth(int amount) {
        if (zombieLogic != null) {
            zombieLogic.setHealth(zombieLogic.getHealth() - amount);
            System.out.println("Zombie dañado. Salud restante: " + zombieLogic.getHealth());
            if (zombieLogic.getHealth() <= 0) {
                zombieLogic.setItsAlive(false);
                remove(); // Eliminar el zombie del escenario
                System.out.println("Zombie eliminado.");
            }
        }
    }


    private boolean collidesWithPlant(PlantCard plantCard) {
        return getBoundingRectangle().overlaps(plantCard.getBoundingRectangle());
    }

    public Rectangle getBoundingRectangle() {
        float offsetX = getWidth() * 0.1f;
        float offsetY = getHeight() * 0.1f;
        float adjustedWidth = getWidth() * 0.8f;
        float adjustedHeight = getHeight() * 0.8f;
        return new Rectangle(getX() + offsetX, getY() + offsetY, adjustedWidth, adjustedHeight);
    }

	public boolean isAlive() {
		return zombieLogic.getItsAlive();
	}
	
	public void checkForPlants(Array<PlantCard> plants) {
	    float zombieY = getY(); // Posición Y del zombie (fila)
	    if (!(zombieLogic instanceof AttackZombie)) {
	        return; // Si no es un zombie de ataque, no hacer nada
	    }
	    for (PlantCard plant : plants) {
	        float plantY = plant.getY();
	        float plantX = plant.getX();

	        // Verifica si la planta está en la misma fila y delante del zombie
	        if (Math.abs(zombieY - plantY) < GameScreen.TILE_SIZE / 2 && plantX < getX()) {
	            if (zombieLogic instanceof Zombie) {
	                if (zombieLogic.getItsAlive()) {
	                    zombieLogic.attack(); // Iniciar ataque del zombie
	                    System.out.println("Zombie detectó planta en fila " + (int) (zombieY / GameScreen.TILE_SIZE));
	                }
	            }
	            break; // Salir del bucle después de encontrar una planta
	        }
	    }
	}

}
