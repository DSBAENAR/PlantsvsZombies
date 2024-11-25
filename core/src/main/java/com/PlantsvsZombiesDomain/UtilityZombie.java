package com.PlantsvsZombiesDomain;

public abstract class UtilityZombie extends Zombie{

    private int moneySupply;


    public UtilityZombie(int[] initalPosition) {
        super(initalPosition);
    }

    public int getMoneySupply() {
        return moneySupply;
    }


    public abstract void giveMoney();

}
