package com.PlantsvsZombiesDomain;

import java.util.ArrayList;

/**
 * This class is for the board of the game, this class contains the matrix of the board, the zombies, the plants and the pruners
 */
public class Board implements GameMoves {

    private Something[][] matrixBoard;
    private int sizeHeight;
    private int sizeWidth;
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<Plant> plants = new ArrayList<>();


    /**
     * Constructor of the board
     * @param sizeHeight size of the board
     * @param sizeWidth width of the board
     */
    public Board(int sizeHeight, int sizeWidth) {
        this.sizeHeight = sizeHeight;
        this.sizeWidth = sizeWidth;
        this.matrixBoard = new Something[sizeHeight][sizeWidth];
    }

    /**
     * This method returns the matrix of the board
     * @return matrix of the board
     */
    public Something[][] getMatrixBoard() {
        return matrixBoard;
    }

    /**
     * Used to put something in the board
     * @param position
     * @param something
     */
    @Override
    public  void putSomething(int[] position, Something something) throws PlantsVsZombiesException{
        if(matrixBoard[position[0]][position[1]] != null){
            throw new PlantsVsZombiesException(PlantsVsZombiesException.SOMETHING_ALREADY_IN_POSITION);
        } else {
            matrixBoard[position[0]][position[1]] = something;
        }
    }

    /**
     * Used to delete something in the board
     * @param position
     * @param something
     */
    @Override
    public  void deleteSomething(int[] position, Something something) throws PlantsVsZombiesException{
        //In construction...
    }
}
