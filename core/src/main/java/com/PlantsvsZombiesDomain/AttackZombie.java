package com.PlantsvsZombiesDomain;

public abstract class AttackZombie extends Zombie {
    private int damage;
    private double attackSpeed;


    public AttackZombie(int[] initalPosition, int health, int price, int damage, double attackSpeed, Player owner) {
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
