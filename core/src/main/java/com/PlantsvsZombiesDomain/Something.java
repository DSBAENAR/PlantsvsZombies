package com.PlantsvsZombiesDomain;

/**
 * Abstract class Something, somethic cuold be a Zombie, a Plant or a Pruner
 */
public abstract class Something{
    private int[] position;

    /**
     * Constructor of Something
     * @param position
     */
    public Something(int[] position) {
        this.position = position;
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
