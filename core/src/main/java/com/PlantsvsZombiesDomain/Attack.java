package com.PlantsvsZombiesDomain;

/**
 * Interface Attack that contains the general attack of the something shooter
 */
public interface Attack {


    /**
     * Attack something in the board
     * @throws PlantsVsZombiesException if something is already in the position
     */
    public void attack() throws PlantsVsZombiesException;

    /**
     * stop the attack
     */
    public  void stopAttack();
}
