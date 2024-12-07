package com.PlantsvsZombiesDomain;

/**
 * Abstract class AttackZombie that extends Zombie class, this class is for the attack zombies
 */
public abstract class AttackZombie extends Zombie {
    protected int damage;
    protected long attackSpeed;
    protected Board board;
    /**
     * Constructor of AttackZombie
     *
     * @param initalPosition initial position of the zombie
     * @param health         health of the zombie
     * @param price          price of the zombie
     * @param damage         damage of the zombie
     * @param attackSpeed    attack speed of the zombie
     * @param owner          owner of the zombie
     * @param board
     */
    public AttackZombie(int[] initalPosition, int health, int price, int damage, long attackSpeed, Player owner, Board board) throws PlantsVsZombiesException{
        super(initalPosition, health, price, damage, attackSpeed, owner, board);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    /**
     * getter of attack speed
     * @return attack speed
     */
    public long getAttackSpeed() {
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
