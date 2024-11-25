package com.PlantsvsZombiesDomain;

public abstract class AttackPlant extends Plant{

    private int damage;
    private double attackSpeed;


    public AttackPlant(int[] position) {
        super(position);
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public int getDamage() {
        return damage;
    }

    public abstract void attack();
}
