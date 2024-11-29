package com.PlantsvsZombiesDomain;

/**
 * Interface that contains the general game moves
 */
public interface GameMoves {

    /**
     * To put something in the board (Plants, Zombies, Pruners)
     * @param position position of the something
     * @param something the something
     * @throws PlantsVsZombiesException if something is already in the position
     */
    public void putSomething(int[] position, Something something) throws PlantsVsZombiesException;

    /**
     * To delete something in the board (Plants, Zombies, Pruners)
     * @param position position of the something
     * @param something the something
     * @throws PlantsVsZombiesException if there is no something in the position
     */
    public void deleteSomething(int[] position, Something something) throws PlantsVsZombiesException;

}
