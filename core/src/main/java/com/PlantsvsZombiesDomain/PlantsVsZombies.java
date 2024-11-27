package com.PlantsvsZombiesDomain;

import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

public class PlantsVsZombies {

    private Board board;
    private ArrayList<Player> players ;
    private int time;
    private Timer timer;
    private String gameMode;
    private boolean turn; // if true player 1, else player 2
    private ArrayList<Something> player1Inventory;
    private ArrayList<Something> player2Inventory;


    public PlantsVsZombies(Board board, int time, String gameMode, Player player1, Player player2){
        this.board = board;
        this.players = new ArrayList<>();
        this.players.add(player1);
        this.players.add(player2);
        this.time = time;
        this.gameMode = gameMode;
        player1Inventory = new ArrayList<>();
        player2Inventory = new ArrayList<>();
        turn = true;
        startTimer();
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean getTurn() {
        return turn;
    }


    public int getTime() {
        return time;
    }


    public void putSomething(int[] position, Something something){
        if(turn){
            players.get(0).putSomething(position, something);
            board.putSomething(position, something);
        }else{
            players.get(1).putSomething(position, something);
            board.putSomething(position, something);
        }
        turn = !turn;
    }


    public void deleteSomething(int[] position, Something something){
        if(turn){
            players.get(0).deleteSomething(position, something);
        }else{
            players.get(1).deleteSomething(position, something);
        }
    }






    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time++;
            }
        }, 1000, 1000);
    }


    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void resetTime() {
        time = 0;
    }

}
