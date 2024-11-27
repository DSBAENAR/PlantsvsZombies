package com.PlantsvsZombiesDomain;

public class NormalZombie extends Zombie{

    private double attackSpeed = 0.5;
    private int damage = 100;

    public NormalZombie(int[] initalPosition, Player owner) {
        super(initalPosition, 100, 100, owner);
    }

    public void attack() {

    }

}
