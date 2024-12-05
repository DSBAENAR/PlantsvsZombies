package com.PlantsvsZombiesDomain;

/**
 * Class DefensePlant that extends Plant class, this class is for the defense plants
 */
public abstract class DefensePlant extends Plant {

    /**
     * Constructor for objects of class DefensePlant
     *
     * @param position position of the defense plant
     * @param health   health of the defense plant
     * @param price    price of the defense plant
     * @param owner    owner of the defense plant
     * @param board
     */
    public DefensePlant(int[] position, int health, int price, Player owner, Board board) throws PlantsVsZombiesException{
        super(position, health, price, owner, board);
    }

}
