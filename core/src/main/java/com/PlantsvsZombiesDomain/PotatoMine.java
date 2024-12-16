package com.PlantsvsZombiesDomain;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class PotatoMine that extends DefensePlant class, this class is for the potato mine
 */
public class PotatoMine extends DefensePlant{

    private Timer timer;
    private Timer detectionTimer;
    private boolean itsActive = false;




    /**
     * Constructor of the class
     * @param position position of the potato mine
     * @param owner owner of the potato mine
     * @param board board
     * @throws PlantsVsZombiesException if the position is not valid
     */
    public PotatoMine(int[] position, Player owner, Board board) throws PlantsVsZombiesException{
        super(position, 150, 50, owner, board);
        startActivationTimer();
        startDetectionTimer();
    }

    /**
     * get if the potato mine is active
     * @return
     */
    public boolean getItsActive() {
        return itsActive;
    }

    /**
     * set if the potato mine is active
     * @param itsActive
     */
    public void setItsActive(boolean itsActive) {
        this.itsActive = itsActive;
    }


    /**
     * Activates the potato mine
     */
    private void activate() {
        if (getItsAlive()) {
            itsActive = true;
        }
    }

    /**
     * Starts the activation timer
     */
    private void startActivationTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activate();
                cancelTimer();
            }
        }, 14000);
    }

    /**
     * Starts the detection timer to check for zombies every 500 ms
     */
    private void startDetectionTimer() {
        detectionTimer = new Timer();
        detectionTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                autoDestroy();
            }
        }, 0, 500); // Check every 500 ms
    }

    /**
     * Cancels the timer of activation
     */
    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * If a Zombie hits an active potato mine, the potato mine and the zombie are destroyed
     */
    public void autoDestroy() {
        if (itsActive && getItsAlive()) {
            ArrayList<Zombie> track = board.getTrack(position[0]);
            if (track != null && !track.isEmpty()) {
                Zombie targetZombie = track.get(0);
                if (targetZombie != null && targetZombie.getPosition()[1] == position[1]) {
                    destroyZombie(targetZombie);
                    destroyPotatoMine();
                }
            }
        }
    }

    /**
     * Handles the destruction of a zombie
     * @param zombie the zombie to destroy
     */
    private void destroyZombie(Zombie zombie) {
        zombie.setItsAlive(false); // Marca al zombi como muerto
        Player zombieOwner = zombie.getOwner();
        if (zombieOwner != null) {
            ArrayList<Something> inventory = zombieOwner.getInventory();
            inventory.remove(zombie);
            zombieOwner.setInventory(inventory);
        }
        ArrayList<Zombie> track = board.getTrack(position[0]);
        if (track != null) {
            board.setTrack(track, position[0]);
            track.remove(zombie);
        }
    }

    /**
     * Handles the destruction of the potato mine
     */
    private void destroyPotatoMine() {
        setItsAlive(false);
        Something[][] matrixBoard = board.getMatrixBoard();
        matrixBoard[position[0]][position[1]] = null;
        board.setMatrixBoard(matrixBoard);
        ArrayList<Something> inventory = owner.getInventory();
        inventory.remove(this);
        owner.setInventory(inventory);

        if (detectionTimer != null) {
            detectionTimer.cancel();
            detectionTimer = null;
        }
    }


}
