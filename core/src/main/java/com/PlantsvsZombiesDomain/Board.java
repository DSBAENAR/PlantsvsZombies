package com.PlantsvsZombiesDomain;

import java.util.ArrayList;

public class Board implements GameMoves {

    private Something[][] matrixBoard;
    private int sizeHeight;
    private int sizeWidth;
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<Plant> plants = new ArrayList<>();


    public Board(int sizeHeight, int sizeWidth) {
        this.sizeHeight = sizeHeight;
        this.sizeWidth = sizeWidth;
        this.matrixBoard = new Something[sizeHeight][sizeWidth];
    }

    public Something[][] getMatrixBoard() {
        return matrixBoard;
    }

    @Override
    public  void putSomething(int[] position, Something something){
        if(matrixBoard[position[0]][position[1]] == null){
            matrixBoard[position[0]][position[1]] = something;
        }
    }

    @Override
    public  void deleteSomething(int[] position, Something something){
        //In construction...
    }




}
