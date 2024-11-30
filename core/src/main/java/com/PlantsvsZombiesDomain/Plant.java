package com.PlantsvsZombiesDomain;

/**
 * Class Plant that extends Something class, this class is for the plants
 */
public abstract class Plant extends Something{

    private int health;
    private int price;
    private boolean itsAlive;
    protected Player owner;

    /**
     * Constructor of the Plant
     * @param position position of the plant
     * @param health health of the plant
     * @param price price of the plant
     * @param owner owner of the plant
     */
    public Plant(int[] position, int health, int price, Player owner)  throws PlantsVsZombiesException {
        super(position);
        this.health = health;
        this.price = price;
        this.itsAlive = true;
        this.owner = owner;
    }

    /**
     * get the health
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * get the price
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * set the owner
     * @param owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * get the owner
     * @return owner
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * get if the plant is alive
     * @return itsAlive
     */
    public boolean getItsAlive() {
        return itsAlive;
    }

    /**
     * set if the plant is alive
     * @param itsAlive
     */
    public void setItsAlive(boolean itsAlive) {
        this.itsAlive = itsAlive;
    }

}
