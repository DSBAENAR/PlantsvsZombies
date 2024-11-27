package com.PlantsvsZombiesDomain;

/**
 * Interface that contains the general game moves
 */
public interface GameMoves {

    /**
     * To put something in the board (Plants, Zombies, Pruners)
     * @param position position of the something
     * @param something the something
     */
    public void putSomething(int[] position, Something something);

    /**
     * To delete something in the board (Plants, Zombies, Pruners)
     * @param position position of the something
     * @param something the something
     */
    public void deleteSomething(int[] position, Something something);

}
