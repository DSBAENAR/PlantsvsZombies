package com.PlantsvsZombiesDomain;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

/**
 * Principal class of the game
 */
public class PlantsVsZombies {

    private Board board;
    private Player player1;
    private Player player2;
    private int time;
    private Timer timer;
    private String gameMode;
    private boolean turn; // if true player 1, else player 2
    private ArrayList<Something> player1Inventory;
    private ArrayList<Something> player2Inventory;


    /**
     * Constructor
     * @param board board of the game
     * @param time time of the game
     * @param gameMode mode of the game
     * @param player1 one of the players
     * @param player2 the other player
     */
    public PlantsVsZombies(Board board, int time, String gameMode, Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.time = time;
        this.gameMode = gameMode;
        player1Inventory = new ArrayList<>();
        player2Inventory = new ArrayList<>();
        turn = true;
        startTimer();
    }

    /**
     * get the board
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * get the player 2
     * @return the player 2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * set the player 2
     * @param player2 the player 2
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * get the player 1
     * @return the player 1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * set the player 1
     * @param player1 the player 1
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    /**
     * get the turn of the player
     * @return the turn
     */
    public boolean getTurn() {
        return turn;
    }

    /**
     * get the time
     * @return the time
     */
    public int getTime() {
        return time;
    }


    /**
     * To put something in the board (Plants, Zombies, Pruners)
     * @param position position of the something
     * @param something the something
     */
    public void putSomething(int[] position, Something something){
        try {
            if (turn) {
                player1.putSomething(position, something);
                board.putSomething(position, something);
            } else {
                player2.putSomething(position, something);
                board.putSomething(position, something);
            }
            turn = !turn;
        } catch (PlantsVsZombiesException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * To delete something in the board (Plants, Zombies, Pruners)
     * @param position position of the something
     * @param something the something
     */
    public void deleteSomething(int[] position, Something something) throws PlantsVsZombiesException {
        if(turn){
            player1.deleteSomething(position, something);
            board.deleteSomething(position, something);
        }
    }


    /**
     * Start the timer
     */
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time++;
                if(time % 10 == 0){
                    incrementMoney();
                }
            }
        }, 1000, 1000);
    }

    /**
     * Stop the timer
     */
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * Reset the time
     */
    public void resetTime() {
        time = 0;
    }

    /**
     * incerment the money of each player, if the player is a plant give 25, else give 50
     */
    private void incrementMoney() {
        if(player1.isPlant()){
            player1.setMoney(player1.getMoney() + 25);
            player2.setMoney(player2.getMoney() + 50);
        } else {
            player2.setMoney(player2.getMoney() + 25);
            player1.setMoney(player1.getMoney() + 50);
        }
    }

}
