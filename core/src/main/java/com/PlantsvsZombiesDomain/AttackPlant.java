package com.PlantsvsZombiesDomain;

public abstract class AttackPlant extends Plant{

    private int damage;
    private double attackSpeed;


    public AttackPlant(int[] position, int health, int price, Player owner) {
        super(position,health,price,owner);
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public int getDamage() {
        return damage;
    }

    public abstract void attack();
}
