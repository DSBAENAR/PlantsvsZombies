package com.PlantsvsZombiesDomain;

import java.util.Timer;
import java.util.TimerTask;

public abstract class UtilityPlant extends Plant{

    protected int moneySupply;
    protected Timer timer;

    public UtilityPlant(int[] position, int health, int price, int MoneySupply, Player owner) {
        super(position, health, price, owner);
        this.moneySupply = MoneySupply;
        startGeneratingMoney();
    }

    public int getMoneySupply() {
        return moneySupply;
    }


    public void giveMoney() {
        if (owner != null) {
            int initialMoney = owner.getMoney();
            owner.setMoney(initialMoney + moneySupply);

        } else {
            System.out.println("The owner of the plant is null. Excepcion para crear");}
    }

    protected void startGeneratingMoney() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isItsAlive()) {
                    giveMoney();
                } else {
                    stopGeneratingMoney();
                }
            }
        }, 0, 100);
    }

    public void stopGeneratingMoney() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
