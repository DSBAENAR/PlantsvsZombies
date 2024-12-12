package com.PlantsvsZombiesDomain;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Abstract class AttackZombie that extends Zombie class, this class is for the attack zombies
 */
public abstract class AttackZombie extends Zombie {

    protected int damage;
    protected long attackSpeed;
    protected Board board;
    protected Timer timer;
    protected int row;
    /**
     * Constructor of AttackZombie
     *
     * @param initalPosition initial position of the zombie
     * @param health         health of the zombie
     * @param price          price of the zombie
     * @param damage         damage of the zombie
     * @param attackSpeed    attack speed of the zombie
     * @param owner          owner of the zombie
     * @param board          board of the game
     */
    public AttackZombie(int[] initalPosition, int health, int price, int damage, long attackSpeed, Player owner, Board board) throws PlantsVsZombiesException{
        super(initalPosition, health, price, damage, attackSpeed, owner, board);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.board = board;
        this.row = initalPosition[0];
    }

    /**
     * getter of attack speed
     * @return attack speed
     */
    public long getAttackSpeed() {
        return attackSpeed;
    }

    /**
     * getter of damage
     * @return damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * This method is for the attack of each attack zombie
     */
    public void attack() {
        boolean hasAttacked = false; // variable para rastrear si se ha realizado el ataque
        Something[][] matrixBoard = board.getMatrixBoard();
        if(matrixBoard != null) {
            for (int i = 9; i >= 0; i--) {
                if (matrixBoard[row][i] != null && matrixBoard[row][i] instanceof Plant && !hasAttacked) {
                    Plant targetPlant = (Plant) matrixBoard[row][i];
                    Player owner = targetPlant.getOwner();
                    int actualHealth = targetPlant.getHealth();
                    if ((actualHealth - damage) <= 0){
                        targetPlant.setItsAlive(false);
                        matrixBoard[row][i] = null;
                        board.setMatrixBoard(matrixBoard);
                        ArrayList<Something> inventory = owner.getInventory();
                        inventory.remove(targetPlant);
                        owner.setInventory(inventory);
                    } else {
                        targetPlant.setHealth(actualHealth - damage);
                    }
                    hasAttacked = true;
                }
            }
        }
    }

}
