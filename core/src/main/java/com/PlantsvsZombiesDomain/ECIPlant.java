package com.PlantsvsZombiesDomain;

public class ECIPlant extends UtilityPlant{

    /**
     * Constructor SunFlower
     *
     * @param position position of the sunflower
     * @param owner    owner of the sunflower
     * @param board    board
     */
    public ECIPlant(int[] position, Player owner, Board board) throws PlantsVsZombiesException {
        super(position, 150, 75, 50, owner, board);
    }
}
