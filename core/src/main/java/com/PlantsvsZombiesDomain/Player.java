package com.PlantsvsZombiesDomain;

import java.util.ArrayList;

/**
 * Class Player that extends GameMoves, this class is for the players
 */
public abstract class Player implements GameMoves{

    protected String name;
    protected int money;
    protected boolean isPlant;
    protected ArrayList<Something> inventory;
    protected Board board;
    protected int gameMode;
    protected int strategy;

    /**
     * Constructor of the playrt
     *
     * @param name     name of the player
     * @param money    money of the player
     * @param isPlant  if the player is a plant
     */
    public Player(String name, int money, boolean isPlant) {
        this.name = name;
        this.money = money;
        this.isPlant = isPlant;
        this.inventory = new ArrayList<>();
    }

    /**
     * Set the board for the player
     * @param board the board to assign
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Get the board assigned to the player
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Set the strategy of the player
     * @param strategy the strategy to assign
     */
    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    /**
     * Get the strategy of the player
     * @return the strategy
     */
    public int getStrategy() {
        return strategy;
    }


    /**
     * get the game mode
     * @return gameMode
     */
    public int getGameMode() {
        return gameMode;
    }

    /**
     * set the game mode
     * @param gameMode game mode
     */
    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * get the name of the player
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set the name of the player
     * @param name name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the money of the player
     * @return money
     */
    public int getMoney() {
        return money;
    }

    /**
     * set the money of the player
     * @param money money of the player
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * get if the player is a plant
     * @return isPlant
     */
    public boolean isPlant() {
        return isPlant;
    }

    /**
     * set if the player is a plant
     * @param plant
     */
    public void setPlant(boolean plant) {
        isPlant = plant;
    }

    /**
     * set the inventory of the player
     * @param inventory inventory of the player
     */
    public void setInventory(ArrayList<Something> inventory) {
        this.inventory = inventory;
    }

    /**
     * get the inventory of the player
     * @return inventory
     */
    public ArrayList<Something> getInventory() {
        return inventory;
    }

    /**
     * To put something in the board (Plants, Zombies, Pruners)
     * @param position position of the something
     * @param something the something
     */
    @Override
    public abstract void putSomething(int[] position, Something something) throws PlantsVsZombiesException;



    /**
     * To delete something in the inventory (Plants)
     * @param position position of the something
     * @param something the something
     */
    @Override
    public abstract void deleteSomething(int[] position, Something something) throws PlantsVsZombiesException;


}
