package com.PlantsvsZombiesDomain;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class Zombie that extends Something class, this class is for the zombies
 */
public abstract class Zombie extends Something implements Attack{

    protected int[] initalPosition;
    protected int column;
    protected int track;
    protected int health;
    protected int price;
    protected int damage;
    protected long attackSpeed;
    protected boolean itsAlive;
    protected Board board;
    protected Player owner;
    protected Timer timerAlive;
    private int timerTicks = 0;
    protected boolean itsAttacking = false;

    /**
     * Constructor for Zombie
     *
     * @param initalPosition initial position
     * @param health         health of the zombie
     * @param price          price of the zombie
     * @param owner          owner
     * @param board
     */
    public Zombie(int[] initalPosition, int health, int price, int damage, long attackSpeed, Player owner, Board board) throws PlantsVsZombiesException {
        super(initalPosition, board);
        this.column = initalPosition[1];
        this.track = initalPosition[0];
        this.health = health;
        this.price = price;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.board = board;
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
     * start time
     */
    protected void startTimer() {
        timerAlive = new Timer();
        timerAlive.scheduleAtFixedRate(new TimerTask() {

            private long elapsedTime = 0;

            @Override
            public void run() {
                if (!getItsAlive()) {
                    stopTimer();
                    return;
                }

                if (elapsedTime > 0 && elapsedTime % 3000 == 0) {
                    if(!itsAttacking){
                        moveZombie();
                    }
                }
                if (elapsedTime > 0 && attackSpeed != 0) {
                    if (elapsedTime % attackSpeed  == 0 ){
                        attack();
                        itsAttacking = false;
                    }
                }
                elapsedTime += 500;
            }
        }, 0, 500);
    }

    /**
     * stop timer
     */
    public void stopTimer() {
        if (timerAlive != null) {
            timerAlive.cancel();
            timerAlive = null;
        }
    }

    protected void moveZombie() {
        if (this.column == 0) {
            stopTimer();
        } else {
            this.column = (this.column - 1);
            position[1] = this.column;
            setColumn(this.column);
        }
    }

    /**
     * This method is for the attack of each zombie, it can be overridden for different attacks
     */
    public void attack() {
        itsAttacking = true;
        Something[][] matrixBoard = board.getMatrixBoard();
        if(matrixBoard[track][column] != null && matrixBoard[track][column] instanceof Plant){
            Plant targetPlant = (Plant) matrixBoard[track][column];
            Player owner = targetPlant.getOwner();
            int actualHealth = targetPlant.getHealth();
            if ((actualHealth - damage) <= 0){
                targetPlant.setItsAlive(false);
                matrixBoard[track][column] = null;
                board.setMatrixBoard(matrixBoard);
                ArrayList<Something> inventory = owner.getInventory();
                inventory.remove(targetPlant);
                owner.setInventory(inventory);
            } else {
                targetPlant.setHealth(actualHealth - damage);
            }
        }
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
