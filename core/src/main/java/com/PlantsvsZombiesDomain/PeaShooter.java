package com.PlantsvsZombiesDomain;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PeaShooter extends AttackPlant {
    public PeaShooter(int[] position, Player owner, Board board) throws PlantsVsZombiesException {
        super(position, 300, 100, 20, 1500, owner, board);
        startAttack();
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
        }, 1500, 1500);
    }

    public void stopAttack() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * This method is for the attack of each plant
     */
    public void attack() {
        ArrayList<Zombie> track = board.getTrack(this.row);
        if (!track.isEmpty()) {
            Zombie targetZombie = track.get(0);
            targetZombie.setHealth(targetZombie.getHealth() - 20);
        }
    }




}
