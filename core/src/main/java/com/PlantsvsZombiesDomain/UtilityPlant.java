package com.PlantsvsZombiesDomain;

public abstract class UtilityPlant extends Plant{

    private int moneySupply;


    public UtilityPlant(int[] position) {
        super(position);
    }

    public int getMoneySupply() {
        return moneySupply;
    }

    public abstract void giveMoney();

}
