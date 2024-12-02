package com.PlantsvsZombiesDomain;

/**
 * Abstract class AttackZombie that extends Zombie class, this class is for the attack zombies
 */
public abstract class AttackZombie extends Zombie {
    private int damage;
    private double attackSpeed;

    /**
     * Constructor of AttackZombie
     * @param initalPosition initial position of the zombie
     * @param health health of the zombie
     * @param price price of the zombie
     * @param damage damage of the zombie
     * @param attackSpeed attack speed of the zombie
     * @param owner owner of the zombie
     */
    public AttackZombie(int[] initalPosition, int health, int price, int damage, double attackSpeed, Player owner) throws PlantsVsZombiesException{
        super(initalPosition, health, price, owner);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    /**
     * getter of attack speed
     * @return attack speed
     */
    public double getAttackSpeed() {
        return attackSpeed;
    }

    /**
     * getter of damage
     * @return damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * This method is for the attack of each zombie
     */
    public abstract void attack();
}
