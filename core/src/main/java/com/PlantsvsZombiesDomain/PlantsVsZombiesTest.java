package com.PlantsvsZombiesDomain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class PlantsVsZombiesTest, this class is for testing the PlantsVsZombies class and the other classes of the domain
 */
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
    void testPutSomething() throws PlantsVsZombiesException {
        int[] position = new int[]{0, 1};
        SunFlower sunFlower1 = new SunFlower(position, player1);
        pvsz.putSomething(position, sunFlower1);
        assertEquals(sunFlower1, pvsz.getBoard().getMatrixBoard()[0][1]);
        assertEquals(sunFlower1, pvsz.getPlayers().get(0).getInventory().get(0));

        assertFalse(pvsz.getTurn());

        int[] position2 = new int[]{0, 9};
        NormalZombie normalZombie1 = new NormalZombie(position2, player2);
        pvsz.putSomething(position2, normalZombie1);
        assertEquals(normalZombie1, pvsz.getBoard().getMatrixBoard()[0][9]);
        assertEquals(normalZombie1, pvsz.getPlayers().get(1).getInventory().get(0));
        assertTrue(pvsz.getTurn());
    }


    @Test
    void testGenerateMoneyPlant() throws PlantsVsZombiesException {
        int[] position = new int[]{0, 1};
        pvsz.putSomething(position, new SunFlower(position, player1));
        int moneyAfterPurchase = player1.getMoney();
        try {
            Thread.sleep(20100);
        } catch (InterruptedException e) {
            e.printStackTrace();  // Captura la excepción y la maneja (puedes loguear o hacer lo que necesites)
        }

        int moneyAfterGeneration = player1.getMoney();
        assertEquals(moneyAfterPurchase + 50 + 50, moneyAfterGeneration);
    }

    @Test
    void testGenerateMoneyZombie() throws PlantsVsZombiesException {
        int[] position = new int[]{0, 1};
        pvsz.putSomething(position, new SunFlower(position, player1));
        int[] position2 = new int[]{0, 9};
        pvsz.putSomething(position2, new BrainsteinZombie(position2, player2));
        int moneyAfterPurchase = player2.getMoney();
        try {
            Thread.sleep(20100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int moneyAfterGeneration = player2.getMoney();
        assertEquals(moneyAfterPurchase + 50 + 100, moneyAfterGeneration);
    }

    @Test
    void testGenerateMoney10Secs(){
        int initialMoneyP1 = player1.getMoney();
        int initialMoneyP2 = player2.getMoney();
        try {
            Thread.sleep(10100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int finalMoneyP1 = player1.getMoney();
        int finalMoneyP2 = player2.getMoney();
        assertEquals(initialMoneyP1 + 25, finalMoneyP1);
        assertEquals(initialMoneyP2 + 50, finalMoneyP2);
    }


}
