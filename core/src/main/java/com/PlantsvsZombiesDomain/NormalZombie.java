package com.PlantsvsZombiesDomain;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class NormalZombie that extends Zombie class, this class is for the normal zombies
 */
public class NormalZombie extends Zombie{

    private double attackSpeed = 0.5;
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
        super(validatePosition(initalPosition), 100, 100, owner, board);
        this.board = board;
    }


    public void startAttack() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (getItsAlive()) {
                    attack();
                } else {
                    stopAttack();
                }
            }
        }, 0, 500);
    }


    public void stopAttack() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void attack() {
        Something[][] matrixBoard = board.getMatrixBoard();
        if(matrixBoard[track][column] != null && matrixBoard[track][column] instanceof Plant){
            Plant targetPlant = (Plant) matrixBoard[track][column];
            int actualHealth = targetPlant.getHealth();
            targetPlant.setHealth(actualHealth - damage);
        }
    }

}
