package com.PlantsvsZombiesDomain;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class NormalZombie that extends Zombie class, this class is for the normal zombies
 */
public class NormalZombie extends Zombie{

    private long attackSpeed = 500;
    private int damage = 100;
    private Timer timer;
    private Board board;
    /**
     * Constructor of NormalZombie
     *
     * @param initalPosition initial position of the zombie
     * @param owner          player that owns the zombie
     * @param board
     */
    public NormalZombie(int[] initalPosition, Player owner, Board board) throws PlantsVsZombiesException {
        super(validatePosition(initalPosition), 100, 100, 100, 500, owner, board);
        this.board = board;
    }
}
