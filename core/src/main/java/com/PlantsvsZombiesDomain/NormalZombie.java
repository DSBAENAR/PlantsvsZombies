package com.PlantsvsZombiesDomain;

/**
 * Class NormalZombie that extends Zombie class, this class is for the normal zombies
 */
public class NormalZombie extends Zombie{

    private double attackSpeed = 0.5;
    private int damage = 100;

    /**
     * Constructor of NormalZombie
     *
     * @param initalPosition initial position of the zombie
     * @param owner          player that owns the zombie
     * @param board
     */
    public NormalZombie(int[] initalPosition, Player owner, Board board) throws PlantsVsZombiesException {
        super(validatePosition(initalPosition), 100, 100, owner, board);
    }

    /**
     * This method is for the attack of the normal ombie
     */
    public void attack() {

    }

}
