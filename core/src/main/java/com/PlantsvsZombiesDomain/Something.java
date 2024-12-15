package com.PlantsvsZombiesDomain;

/**
 * Abstract class Something, somethic cuold be a Zombie, a Plant or a Pruner
 */
public abstract class Something{
    protected int[] position;
    private Board board;
    protected int row;
    protected int column;
    protected boolean itsAlive;

    /**
     * Constructor of Something
     *
     * @param position
     * @param board
     */
    public Something(int[] position, Board board) {
        this.position = position;
        this.board = board;
        this.row = position[0];
        this.column = position[1];
    }

    /**
     * get the position of the something
     * @return position
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * get the Row of the something
     * @return row of the something
     */
    public int getRow() {
        return row;
    }

    /**
     * set the Column of the something
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * get the Column of the something
     * @return column of the something
     */
    public int getColumn() {
        return column;
    }

    /**
     * set the Column of the something
     * @param column of the something
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * set the position of the something
     * @param position
     */
    public void setPosition(int[] position) {
        this.position = position;
    }
}
