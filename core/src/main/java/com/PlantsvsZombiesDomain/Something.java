package com.PlantsvsZombiesDomain;

/**
 * Abstract class Something, somethic cuold be a Zombie, a Plant or a Pruner
 */
public abstract class Something{
    private int[] position;
    private Board board;

    /**
     * Constructor of Something
     *
     * @param position
     * @param board
     */
    public Something(int[] position, Board board) {
        this.position = position;
        this.board = board;
    }

    /**
     * get the position of the something
     * @return position
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * set the position of the something
     * @param position
     */
    public void setPosition(int[] position) {
        this.position = position;
    }
}
