package com.PlantsvsZombiesDomain;

/**
 * Abstract class HelmetZombie that extends Zombie class, this class is for the helmet zombies
 */
public abstract class HelmetZombie extends Zombie {
    private int damage;
    private double attackSpeed;

    /**
     * Constructor of HelmetZombie
     * @param initalPosition initial position of the zombie
     * @param health health of the zombie
     * @param price price of the zombie
     * @param damage damage of the zombie
     * @param attackSpeed attack speed of the zombie
     * @param owner owner of the zombie
     */
    public HelmetZombie(int[] initalPosition, int health, int price, int damage, double attackSpeed, Player owner) {
        super(initalPosition, health, price, owner);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public int getDamage() {
        return damage;
    }
    public abstract void attack();
}
