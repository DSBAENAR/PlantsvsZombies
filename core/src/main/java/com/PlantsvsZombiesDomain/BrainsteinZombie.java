package com.PlantsvsZombiesDomain;

/**
 * Class BrainsteinZombie that extends UtilityZombie, this class is for the brainstein zombies
 */
public class BrainsteinZombie extends UtilityZombie{

    /**
     * Constructor of BrainsteinZombie
     * @param position position of the zombie
     * @param owner owner of the zombie
     */
    public BrainsteinZombie(int[] position, Player owner) {
        super(position, 300, 50, 25, owner);
    }
}
