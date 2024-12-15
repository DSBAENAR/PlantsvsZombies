package com.PlantsvsZombiesDomain;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LawnMower extends Something {

    private Timer timerAlive;
    private Board board;
    /**
     * Constructor of Something
     * @param position
     * @param board
     */
    public LawnMower(int[] position, Board board) {
        super(position, board);
        this.board = board;
        row = position[0];
        column = position[1];
        this.itsAlive = true;
        zombieCheckTimer();

    }

    /**
     * get if the lawnMower is alive
     * @return itsAlive indicate if the lawnMower is alive
     */
    public boolean getItsAlive() {
        return itsAlive;
    }

    /**
     * set if the lawnMower is alive
     * @param itsAlive indicate if the lawnMower is alive
     */
    public void setItsAlive(boolean itsAlive) {
        this.itsAlive = itsAlive;
    }

    /**
     * start time
     */
    public void zombieCheckTimer() {
        timerAlive = new Timer();
        timerAlive.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                checkZombies();
            }
        }, 0, 500);
    }


    /**
     * check if there are zombies in the same column
     */
    private void checkZombies() {
        ArrayList<Zombie> zombies = board.getTrack(this.row);
        for (Zombie zombie : zombies) {
            if (zombie.getColumn() == this.column) {
                killZombies();
                zombies.clear();
                board.setTrack(zombies, this.row);
                itsAlive = false;
                stopAttack();
                break;
            }
        }
    }

    /**
     * kill all the zombies in the same track
     */
    private void killZombies() {
        ArrayList<Zombie> zombies = board.getTrack(this.row);
        for (Zombie zombie : zombies) {
            zombie.setHealth(0);
            zombie.setItsAlive(false);
            itsAlive = false;
        }
        board.setTrack(zombies, this.row);
    }

    /**
     * stop the attack
     */
    public  void stopAttack(){
        if (timerAlive != null) {
            timerAlive.cancel();
            timerAlive = null;
        }
    }

}
