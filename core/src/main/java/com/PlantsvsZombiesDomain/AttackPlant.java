package com.PlantsvsZombiesDomain;


/**
 * Abstract class AttackPlant that extends Plant class, this class is for the attack plants
 */
public abstract class AttackPlant extends Plant{

    private int damage;
    private double attackSpeed;


    /**
     * Constructor of AttackPlant
     * @param position position of the plant
     * @param health health of the plant
     * @param price price of the plant
     * @param owner owner of the plant
     */
    public AttackPlant(int[] position, int health, int price, Player owner) throws PlantsVsZombiesException {
        super(position,health,price,owner);
    }

    /**
     * get the attack speed
     * @return
     */
    public double getAttackSpeed() {
        return attackSpeed;
    }

    /**
     * get the damage
     * @return
     */
    public int getDamage() {
        return damage;
    }

    /**
     * This method is for the attack of each plant
     */
    public abstract void attack();
}
