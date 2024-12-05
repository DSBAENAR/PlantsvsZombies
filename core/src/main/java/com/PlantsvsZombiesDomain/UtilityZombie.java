package com.PlantsvsZombiesDomain;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class UtilityZombie that extends Zombie class, this class is for the utility zombies
 */
public abstract class UtilityZombie extends Zombie{

    protected int moneySupply;
    protected Timer timer;

    /**
     * Constructor of UtilityZombie
     *
     * @param position    position of the zombie
     * @param health      health of the zombie
     * @param price       price of the zombie
     * @param MoneySupply money supply
     * @param owner       owner
     * @param board
     */
    public UtilityZombie(int[] position, int health, int price, int MoneySupply, Player owner, Board board) throws PlantsVsZombiesException {
        super(position, health, price, owner, board);
        this.moneySupply = MoneySupply;
        startGeneratingMoney();
    }

    /**
     * get the money supply
     * @return money supply
     */
    public int getMoneySupply() {
        return moneySupply;
    }

    /**
     * give the generated money to the owner
     */
    public void giveMoney() {
        if (owner != null) {
            int initialMoney = owner.getMoney();
            owner.setMoney(initialMoney + moneySupply);

        } else {
            System.out.println("The owner of the plant is null. Excepcion para crear");}
    }

    /**
     * start generating money
     */
    protected void startGeneratingMoney() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (getItsAlive()) {
                    giveMoney();
                } else {
                    stopGeneratingMoney();
                }
            }
        }, 20000, 20000);
    }

    /**
     * stop generating money
     */
    public void stopGeneratingMoney() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
