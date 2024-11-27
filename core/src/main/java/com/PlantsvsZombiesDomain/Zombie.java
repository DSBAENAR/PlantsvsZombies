package com.PlantsvsZombiesDomain;

public abstract class Zombie extends Something {

    private int[] initalPosition;
    private int health;
    private int price;
    private boolean itsAlive;
    protected Player owner;

    public Zombie(int[] initalPosition, int health, int price, Player owner) {
        super(initalPosition);
        this.health = health;
        this.price = price;
        this.itsAlive = true;
        this.owner = owner;
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
