package com.PlantsvsZombiesDomain;

/**
 * Class Zombie that extends Something class, this class is for the zombies
 */
public abstract class Zombie extends Something {

    private int[] initalPosition;
    private int health;
    private int price;
    private boolean itsAlive;
    protected Player owner;

    /**
     * Constructor for Zombie
     * @param initalPosition initial position
     * @param health health of the zombie
     * @param price price of the zombie
     * @param owner owner
     */
    public Zombie(int[] initalPosition, int health, int price, Player owner) throws PlantsVsZombiesException {
        super(initalPosition);
        this.health = health;
        this.price = price;
        this.itsAlive = true;
        this.owner = owner;
    }

    /**
     * get the owner
     * @return owner of the zombie
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * set the owner
     * @param owner owner
     */
    public void setOwner(Player owner) {
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
     * get if the zombie is alive
     * @return itsAlive
     */
    public boolean getItsAlive() {
        return itsAlive;
    }

    /**
     * set if the zombie is alive
     * @param itsAlive
     */
    public void setItsAlive(boolean itsAlive) {
        this.itsAlive = itsAlive;
    }

    /**
     * validate the position, if the column is not 9 throw an exception
     * @param position initial position of the zombie
     * @throws PlantsVsZombiesException if the column is not 9
     */
    protected static int[] validatePosition(int[] position) throws PlantsVsZombiesException {
        if (position[1] != 9) {
            throw new PlantsVsZombiesException(PlantsVsZombiesException.ARGUMENTS_NOT_VALID);
        }
        return position;
    }
}
