package com.PlantsvsZombiesDomain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class PlantsVsZombiesTest {

    private Board board;
    private ArrayList<Zombie> zombies;
    private ArrayList<Plant> plants;
    private Player player1;
    private Player player2;
    private PlantsVsZombies pvsz;

    @BeforeEach
    void setUp() {
        board = new Board(5, 10);
        player1 = new HumanPlayer("Barbosa", 1000, true);
        player2 = new HumanPlayer("Baena", 1000, false);
        pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);
        zombies = new ArrayList<>();
        plants = new ArrayList<>();
    }

    @Test
    void testPutSomething() {
        int[] position = new int[]{0, 1};
        SunFlower sunFlower1 = new SunFlower(position);
        pvsz.putSomething(position, sunFlower1);
        assertEquals(sunFlower1, pvsz.getBoard().getMatrixBoard()[0][1]);
        assertEquals(sunFlower1, pvsz.getPlayers().get(0).getInventory().get(0));
    }

}
