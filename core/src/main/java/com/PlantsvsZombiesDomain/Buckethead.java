package com.PlantsvsZombiesDomain;
import java.util.Timer;
import java.util.TimerTask;



public class Buckethead extends HelmetZombie {
    public Buckethead(int[] initalPosition, Player owner, Board board) throws PlantsVsZombiesException {
        super(initalPosition, 800, 200, 100, 500, owner, board);
        startAttack();
    }

    @Override
    public void attack() {
        Something[][] matrixBoard = board.getMatrixBoard();
        if(matrixBoard[track][column] != null && matrixBoard[track][column] instanceof Plant){
            Plant targetPlant = (Plant) matrixBoard[track][column];
            int actualHealth = targetPlant.getHealth();
            targetPlant.setHealth(actualHealth - damage);
        }
    }


    @Override
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

    @Override
    public void stopAttack() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
