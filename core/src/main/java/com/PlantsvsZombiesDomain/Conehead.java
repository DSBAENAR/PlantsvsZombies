package com.PlantsvsZombiesDomain;

public class Conehead extends HelmetZombie{

    public Conehead(int[] initalPosition, Player owner, Board board) throws PlantsVsZombiesException {
        super(initalPosition, 380, 150, 100, 500, owner, board);
    }
}
