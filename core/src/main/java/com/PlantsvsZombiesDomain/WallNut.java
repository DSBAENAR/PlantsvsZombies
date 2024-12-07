package com.PlantsvsZombiesDomain;

public class WallNut extends DefensePlant {

    public WallNut(int[] position, Player owner, Board board) throws PlantsVsZombiesException{
        super(position, 4000, 50, owner, board);
    }
}
