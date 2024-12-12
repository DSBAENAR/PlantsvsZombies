package com.PlantsvsZombiesDomain;

import java.util.ArrayList;

/**
 * This class is for the board of the game, this class contains the matrix of the board, the zombies, the plants and the pruners
 */
public class Board implements GameMoves {

    private Something[][] matrixBoard;
    private int sizeHeight;
    private int sizeWidth;
    private float tileSize;
    private ArrayList<Zombie> zombies;
    private ArrayList<Plant> plants;
    private ArrayList<Zombie> track0;
    private ArrayList<Zombie> track1;
    private ArrayList<Zombie> track2;
    private ArrayList<Zombie> track3;
    private ArrayList<Zombie> track4;
    private Player player1;
    private Player player2;



    /**
     * Constructor of the board
     * @param sizeHeight size of the board
     * @param sizeWidth width of the board
     */
    public Board(int sizeHeight, int sizeWidth, Player player1, Player player2) {
        this.sizeHeight = sizeHeight;
        this.sizeWidth = sizeWidth;
        this.matrixBoard = new Something[sizeHeight][sizeWidth];
        this.player1 = player1;
        this.player2 = player2;
        this.track0 = new ArrayList<>();
        this.track1 = new ArrayList<>();
        this.track2 = new ArrayList<>();
        this.track3 = new ArrayList<>();
        this.track4 = new ArrayList<>();
        fillBoardWithLawnMowers();
    }

    public float getTileSize() {
        return tileSize;
    }

    public int getSizeHeight() {
        return sizeHeight;
    }

    public int getSizeWidth() {
        return sizeWidth;
    }





    /**
     * get the player 1
     * @return player 1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * get the player 2
     * @return player 2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * get a specific track
     * @return zombies
     */
    public ArrayList<Zombie> getTrack(int track) {
        switch (track) {
            case 0:
                return track0;
            case 1:
                return track1;
            case 2:
                return track2;
            case 3:
                return track3;
            case 4:
                return track4;
            default:
                return null;
        }
    }

    /**
     * set a specific track
     * @param track track
     * @param numbertrack number of the track
     */
    public void setTrack(ArrayList<Zombie> track, int numbertrack) {
        switch (numbertrack) {
            case 0:
                track0 = track;
                break;
            case 1:
                track1 = track;
                break;
            case 2:
                track2 = track;
                break;
            case 3:
                track3 = track;
                break;
            case 4:
                track4 = track;
                break;
        }
    }

    /**
     * This method returns the matrix of the board
     * @return matrix of the board
     */
    public Something[][] getMatrixBoard() {
        return matrixBoard;
    }

    /**
     * This method sets the matrix of the board
     * @param matrixBoard
     */
    public void setMatrixBoard(Something[][] matrixBoard) {
        this.matrixBoard = matrixBoard;
    }

    /**
     * Used to put something in the board
     * @param position
     * @param something
     * @throws PlantsVsZombiesException if something is already in the position
     */
    @Override
    public  void putSomething(int[] position, Something something) throws PlantsVsZombiesException{
        if(something instanceof Zombie){
            // If the column is 9 (Only zombies in column 9)
            if (position[1] == 9) {
                switch (position[0]) {
                    case 0:
                        track0.add((Zombie) something);
                        break;
                    case 1:
                        track1.add((Zombie) something);
                        break;
                    case 2:
                        track2.add((Zombie) something);
                        break;
                    case 3:
                        track3.add((Zombie) something);
                        break;
                    case 4:
                        track4.add((Zombie) something);
                        break;
                }
            } else {
                throw new PlantsVsZombiesException(PlantsVsZombiesException.ARGUMENTS_NOT_VALID);
            }
        } else {
            if(matrixBoard[position[0]][position[1]] != null){
                throw new PlantsVsZombiesException(PlantsVsZombiesException.SOMETHING_ALREADY_IN_POSITION);
            } else {
                //If the column is 0 (Only prunners in column 0), throw an exception
                if(position[1] == 0){
                    throw new PlantsVsZombiesException(PlantsVsZombiesException.ARGUMENTS_NOT_VALID);
                } else {
                    matrixBoard[position[0]][position[1]] = something;
                }
            }
        }
    }

    /**
     * Used to delete something in the board
     * @param position
     * @param something
     * @throws PlantsVsZombiesException if there is no something in the position
     */
    @Override
    public  void deleteSomething(int[] position, Something something) throws PlantsVsZombiesException{
        //In construction...
    }

    /**
     * Used to fill the board with LawnMowers in the first row of the board
     */
    private void fillBoardWithLawnMowers() {
        for (int i = 0; i < sizeHeight; i++) {
            matrixBoard[i][0] = new LawnMower(new int[] { i, 0 }, this);
        }
    }
}
