package com.PlantsvsZombiesDomain;

public abstract class HelmetZombie extends Zombie {
    private int damage;
    private double attackSpeed;


    public HelmetZombie(int[] initalPosition) {
        super(initalPosition);
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public int getDamage() {
        return damage;
    }
    public abstract void attack();
}
