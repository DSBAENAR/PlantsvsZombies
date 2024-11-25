package com.PlantsvsZombiesDomain;

public abstract class Zombie extends Something {

    private int[] initalPosition;
    private int health;
    private int damage;
    private int price;
    private boolean itsAlive;

    public Zombie(int[] initalPosition) {
        super(initalPosition);
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
