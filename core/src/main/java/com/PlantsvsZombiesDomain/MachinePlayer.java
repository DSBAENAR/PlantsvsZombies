package com.PlantsvsZombiesDomain;

public class MachinePlayer extends Player{




    /**
     * Constructor of HumanPlayer
     * @param name of the player
     * @param money its the initial money of the player
     * @param isPlant if the player is a plant (True) or a zombie (False)
     */
    public MachinePlayer(String name, int money, boolean isPlant) {
        super(name, money, isPlant);
    }

    @Override
    public void putSomething(int[] position, Something something) throws PlantsVsZombiesException {

    }

    @Override
    public void deleteSomething(int[] position, Something something) throws PlantsVsZombiesException {

    }


}
