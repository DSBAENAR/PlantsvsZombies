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




    @Test
    void testPutSomething() throws PlantsVsZombiesException {
        int[] position = new int[]{0, 1};
        SunFlower sunFlower1 = new SunFlower(position, player1, board);
        pvsz.putSomething(position, sunFlower1);
        assertEquals(sunFlower1, pvsz.getBoard().getMatrixBoard()[0][1]);
        assertEquals(sunFlower1, pvsz.getPlayer1().getInventory().get(0));

        assertFalse(pvsz.getTurn());

        int[] position2 = new int[]{0, 9};
        NormalZombie normalZombie1 = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie1);
        assertEquals(normalZombie1, pvsz.getBoard().getTrack(0).get(0));
        assertEquals(normalZombie1, pvsz.getPlayer2().getInventory().get(0));
        assertTrue(pvsz.getTurn());
    }


    @Test
    void testGenerateMoneyPlant() throws PlantsVsZombiesException {
        int[] position = new int[]{0, 1};
        pvsz.putSomething(position, new SunFlower(position, player1, board));
        int moneyAfterPurchase = player1.getMoney();
        try {
            Thread.sleep(20100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int moneyAfterGeneration = player1.getMoney();
        assertEquals(moneyAfterPurchase + 50 + 25, moneyAfterGeneration);
    }

    @Test
    void testGenerateMoneyZombie() throws PlantsVsZombiesException {
        int[] position = new int[]{0, 1};
        pvsz.putSomething(position, new SunFlower(position, player1, board));
        int[] position2 = new int[]{0, 9};
        pvsz.putSomething(position2, new BrainsteinZombie(position2, player2, board));
        int moneyAfterPurchase = player2.getMoney();
        try {
            Thread.sleep(20100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int moneyAfterGeneration = player2.getMoney();
        assertEquals(moneyAfterPurchase + 25 + 100, moneyAfterGeneration);
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

    @Test
    void testmoveZombie() throws PlantsVsZombiesException {
        int[] position = new int[]{0, 1};
        pvsz.putSomething(position, new SunFlower(position, player1, board));
        int[] position2 = new int[]{0, 9};
        pvsz.putSomething(position2, new NormalZombie(position2, player2, board));
        try {
            Thread.sleep(3100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(8, pvsz.getBoard().getTrack(0).get(0).getZombiePosition()[1]);
        try {
            Thread.sleep(3100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(7, pvsz.getBoard().getTrack(0).get(0).getZombiePosition()[1]);
    }


    @Test
    void testAttack() throws PlantsVsZombiesException {
        int[] position = new int[]{0, 1};
        pvsz.putSomething(position, new PeaShooter(position, player1, board));
        int[] position2 = new int[]{0, 9};
        pvsz.putSomething(position2, new NormalZombie(position2, player2, board));
        int zombieInitialHealth = pvsz.getBoard().getTrack(0).get(0).getHealth();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int zombieCurrentHealth = pvsz.getBoard().getTrack(0).get(0).getHealth();
        assertEquals(zombieInitialHealth - 20, zombieCurrentHealth);
    }

    @Test
    void testAttackWhithTwoPeaShooter() throws PlantsVsZombiesException {
        int[] position = new int[]{0, 1};
        pvsz.putSomething(position, new PeaShooter(position, player1, board));
        int[] position2 = new int[]{0, 9};
        pvsz.putSomething(position2, new NormalZombie(position2, player2, board));
        int[] positionAnotherPeaShooter = new int[]{0, 2};
        pvsz.putSomething(positionAnotherPeaShooter, new PeaShooter(positionAnotherPeaShooter, player1, board));
        int[] positionAnotherZombie = new int[]{1, 9};
        pvsz.putSomething(positionAnotherZombie, new NormalZombie(positionAnotherZombie, player2, board));
        int zombieInitialHealth = pvsz.getBoard().getTrack(0).get(0).getHealth();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int zombieCurrentHealth = pvsz.getBoard().getTrack(0).get(0).getHealth();
        assertEquals(zombieInitialHealth - 40, zombieCurrentHealth);
    }


    @Test
    void testZombieAttack() throws PlantsVsZombiesException {
        player1 = new HumanPlayer("Barbosa", 1000, true);
        player2 = new HumanPlayer("Baena", 1000, false);
        board = new Board(5, 10, player1, player2);
        pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);
        zombies = new ArrayList<>();
        plants = new ArrayList<>();

        int [] position = new int[]{0, 9};
        pvsz.putSomething(position, new WallNut(position, player1, board));
        int[] position2 = new int[]{0, 9};
        pvsz.putSomething(position2, new Buckethead(position2, player2, board));
        int plantInitialHealth = ((Plant) pvsz.getBoard().getMatrixBoard()[0][9]).getHealth();
        System.out.println(plantInitialHealth);
        try {
            Thread.sleep(510);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int plantCurrentHealth = ((Plant) pvsz.getBoard().getMatrixBoard()[0][9]).getHealth();
        assertEquals(plantInitialHealth - 200, plantCurrentHealth);
        try {
            Thread.sleep(510);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        plantCurrentHealth = ((Plant) pvsz.getBoard().getMatrixBoard()[0][9]).getHealth();
        assertEquals(plantInitialHealth - 300, plantCurrentHealth);
    }
}
