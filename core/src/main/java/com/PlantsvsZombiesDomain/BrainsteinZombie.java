package com.PlantsvsZombiesDomain;

/**
 * Class BrainsteinZombie that extends UtilityZombie, this class is for the brainstein zombies
 */
public class BrainsteinZombie extends UtilityZombie{

    /**
     * Constructor of BrainsteinZombie
     *
     * @param position position of the zombie
     * @param owner    owner of the zombie
     * @param board
     */
    public BrainsteinZombie(int[] position, Player owner, Board board) throws PlantsVsZombiesException {
        super(validatePosition(position), 300, 50, 25, owner, board);
    }

    /**
     * In this case the zombie doesn't move
     */
    @Override
    public void moveZombie() {
    }
}
