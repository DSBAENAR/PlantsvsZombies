package com.PlantsvsZombiesDomain;

public abstract class Plant extends Something{

    private int health;
    private int price;
    private boolean itsAlive;

    public Plant(int[] position) {
        super(position);
        this.itsAlive = true;
    }

    public int getHealth() {
        return health;
    }

    public int getPrice() {
        return price;
    }


    public boolean isItsAlive() {
        return itsAlive;
    }

    public void setItsAlive(boolean itsAlive) {
        this.itsAlive = itsAlive;
    }

}
