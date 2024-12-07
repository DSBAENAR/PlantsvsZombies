package com.PlantsvsZombiesDomain;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class Zombie that extends Something class, this class is for the zombies
 */
public abstract class Zombie extends Something {

    protected int[] initalPosition;
    protected int column;
    protected int track;
    protected int health;
    protected int price;
    private boolean itsAlive;
    protected Player owner;
    protected Timer timerAlive;
    private int timerTicks = 0;

    /**
     * Constructor for Zombie
     *
     * @param initalPosition initial position
     * @param health         health of the zombie
     * @param price          price of the zombie
     * @param owner          owner
     * @param board
     */
    public Zombie(int[] initalPosition, int health, int price, Player owner, Board board) throws PlantsVsZombiesException {
        super(initalPosition, board);
        this.column = initalPosition[1];
        this.track = initalPosition[0];
        this.health = health;
        this.price = price;
        this.itsAlive = true;
        this.owner = owner;
        startTimer();
    }

    /**
     * get the owner
     * @return owner of the zombie
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * set the owner
     * @param owner owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * get the health
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * set the health
     * @param health health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * get the price
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * get if the zombie is alive
     * @return itsAlive
     */
    public boolean getItsAlive() {
        return itsAlive;
    }

    public int[] getZombiePosition() {
        int[] zombiePosition = new int[]{track, column};
        return zombiePosition;
    }

    /**
     * set if the zombie is alive
     * @param itsAlive
     */
    public void setItsAlive(boolean itsAlive) {
        this.itsAlive = itsAlive;
    }

    /**
     * validate the position, if the column is not 9 throw an exception
     * @param position initial position of the zombie
     * @throws PlantsVsZombiesException if the column is not 9
     */
    protected static int[] validatePosition(int[] position) throws PlantsVsZombiesException {
        if (position[1] != 9) {
            throw new PlantsVsZombiesException(PlantsVsZombiesException.ARGUMENTS_NOT_VALID);
        }
        return position;
    }

    /**
     * start generating money
     */
    protected void startTimer() {
        timerAlive = new Timer();
        timerAlive.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (getItsAlive()) {
                    moveZombie();
                } else {
                    stopTimer();
                }
            }
        }, 3000, 3000); // Primera ejecución después de 3 segundos, luego cada 3 segundos
    }

    private void moveZombie() {
        column = (column - 1);
    }

    /**
     * stop generating money
     */
    public void stopTimer() {
        if (timerAlive != null) {
            timerAlive.cancel();
            timerAlive = null;
        }
    }
}
