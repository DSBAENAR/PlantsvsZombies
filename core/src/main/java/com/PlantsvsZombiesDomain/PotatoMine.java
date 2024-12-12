package com.PlantsvsZombiesDomain;

public class PotatoMine extends DefensePlant{

    private boolean itsActive = false;


    public PotatoMine(int[] position, Player owner, Board board) throws PlantsVsZombiesException{
        super(position, 100, 50, owner, board);
    }
}
