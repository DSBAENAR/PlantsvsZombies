package com.PlantsvsZombiesDomain;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class UtilityPlant that extends Plant class, this class is for the utility plants
 */
public abstract class UtilityPlant extends Plant{

    protected int moneySupply;
    protected Timer timer;

    /**
     * Constructor UtilityPlant
     *
     * @param position    position of the plant
     * @param health      health of the plant
     * @param price       price of the plant
     * @param MoneySupply money supply of the plant
     * @param owner       owner of the plant
     * @param board
     */
    public UtilityPlant(int[] position, int health, int price, int MoneySupply, Player owner, Board board) throws PlantsVsZombiesException {
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
     * Give the generated money to the owner
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
