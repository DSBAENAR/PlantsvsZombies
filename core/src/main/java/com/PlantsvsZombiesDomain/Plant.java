package com.PlantsvsZombiesDomain;

public abstract class Plant extends Something{

    private int health;
    private int price;
    private boolean itsAlive;
    protected Player owner;

    public Plant(int[] position, int health, int price, Player owner) {
        super(position);
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

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }


    public boolean isItsAlive() {
        return itsAlive;
    }

    public void setItsAlive(boolean itsAlive) {
        this.itsAlive = itsAlive;
    }

}
