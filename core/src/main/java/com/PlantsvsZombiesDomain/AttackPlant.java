package com.PlantsvsZombiesDomain;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Abstract class AttackPlant that extends Plant class, this class is for the attack plants
 */
public abstract class AttackPlant extends Plant{

    protected int damage;
    protected long attackSpeed;
    protected Timer timer;
    protected ArrayList<Zombie> trackZombies;
    protected Board board;
    protected int row;

    /**
     * Constructor of AttackPlant
     *
     * @param position position of the plant
     * @param health   health of the plant
     * @param price    price of the plant
     * @param owner    owner of the plant
     * @param board    board of the game
     */
    public AttackPlant(int[] position, int health, int price, int damage, long attackSpeed, Player owner, Board board) throws PlantsVsZombiesException {
        super(position,health,price,owner, board);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.trackZombies = board.getTrack(position[0]);
        this.board = board;
        this.row = position[0];
    }

    public void setDamage(int damage) {
		this.damage = damage;
	}

    /**
     * get the attack speed
     * @return
     */
    public double getAttackSpeed() {
        return attackSpeed;
    }


    public void setAttackSpeed(long attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	/**
     * get the damage
     * @return
     */
    public int getDamage() {
        return damage;
    }
    /**
     * start generating money
     */
    public abstract void startAttack();

    /**
     * This method is for the attack of each plant
     */
    public void attack() {
        ArrayList<Zombie> track = board.getTrack(this.row);
        if (track != null && !track.isEmpty()) {
            Zombie targetZombie = track.get(0);
            if (targetZombie != null) {
                int actualHealth = targetZombie.getHealth();
                if(actualHealth - damage <= 0){
                    targetZombie.setItsAlive(false);
                    Player owner = targetZombie.getOwner();
                    ArrayList<Something> inventory = owner.getInventory();
                    inventory.remove(targetZombie);
                    owner.setInventory(inventory);
                    track.remove(0);
                } else {
                    targetZombie.setHealth(targetZombie.getHealth() - damage);
                }
            }
        }
    }
}
