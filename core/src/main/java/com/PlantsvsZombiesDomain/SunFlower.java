package com.PlantsvsZombiesDomain;

/**
 * Class SunFlower that extends UtilityPlant class, this class is for the sunflower
 */
public class SunFlower extends UtilityPlant{


    /**
     * Constructor SunFlower
     *
     * @param position position of the sunflower
     * @param owner    owner of the sunflower
     * @param board
     */
    public SunFlower(int[] position, Player owner, Board board) throws PlantsVsZombiesException {
        super(position, 300, 50, 25, owner, board);
    }

}
