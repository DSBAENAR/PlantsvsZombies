package com.PlantsvsZombiesDomain;

import java.util.Timer;

/**
 * Abstract class HelmetZombie that extends Zombie class, this class is for the helmet zombies
 */
public abstract class HelmetZombie extends Zombie {
    protected int damage;
    protected long attackSpeed;
    protected Board board;

    /**
     * Constructor of HelmetZombie
     *
     * @param initalPosition initial position of the zombie
     * @param health         health of the zombie
     * @param price          price of the zombie
     * @param damage         damage of the zombie
     * @param attackSpeed    attack speed of the zombie
     * @param owner          owner of the zombie
     * @param board
     */
    public HelmetZombie(int[] initalPosition, int health, int price, int damage, long attackSpeed, Player owner, Board board) throws PlantsVsZombiesException {
        super(validatePosition(initalPosition), health, price, damage, attackSpeed, owner, board);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }
    public long getAttackSpeed() {
        return attackSpeed;
    }

    public int getDamage() {
        return damage;
    }

}
