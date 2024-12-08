package com.PlantsvsZombiesDomain;

/**
 * Class Buckethead that extends Zombie class, this class is for the Buckethead zombies
 */
public class Buckethead extends HelmetZombie {
    public Buckethead(int[] initalPosition, Player owner, Board board) throws PlantsVsZombiesException {
        super(initalPosition, 800, 200, 100, 500, owner, board);
    }

}
