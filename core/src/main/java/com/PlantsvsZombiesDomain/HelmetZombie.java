package com.PlantsvsZombiesDomain;

public abstract class HelmetZombie extends Zombie {
    private int damage;
    private double attackSpeed;


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
