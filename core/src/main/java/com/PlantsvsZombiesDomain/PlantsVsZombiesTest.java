package com.PlantsvsZombiesDomain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class PlantsVsZombiesTest, this class is for testing the PlantsVsZombies class and the other classes of the domain
 */
public class PlantsVsZombiesTest {

    @Test
    void testPutSomething() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);

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

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);

        int[] position = new int[]{0, 1};
        SunFlower sunFlower = new SunFlower(position, player1, board);
        pvsz.putSomething(position, sunFlower);
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

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);

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

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);

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

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);

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

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);

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

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);

        int[] position = new int[]{0, 1};
        PeaShooter peaShooter1 = new PeaShooter(position, player1, board);
        pvsz.putSomething(position, peaShooter1);
        int[] position2 = new int[]{0, 9};
        pvsz.putSomething(position2, new NormalZombie(position2, player2, board));
        int[] positionAnotherPeaShooter = new int[]{0, 2};
        PeaShooter peaShooter2 = new PeaShooter(position, player1, board);
        pvsz.putSomething(positionAnotherPeaShooter, peaShooter2);
        int[] positionAnotherZombie = new int[]{1, 9};
        pvsz.putSomething(positionAnotherZombie, new NormalZombie(positionAnotherZombie, player2, board));
        int zombieInitialHealth = pvsz.getBoard().getTrack(0).get(0).getHealth();
        try {
            Thread.sleep(2300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int zombieCurrentHealth = pvsz.getBoard().getTrack(0).get(0).getHealth();
        assertEquals(zombieInitialHealth - 40, zombieCurrentHealth);
    }


    @Test
    void testZombieAttack() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);

        int [] position = new int[]{0, 9};
        WallNut wallNut = new WallNut(position, player1, board);
        pvsz.putSomething(position, wallNut);
        int plantInitialHealth = ((Plant) pvsz.getBoard().getMatrixBoard()[0][9]).getHealth();
        int[] position2 = new int[]{0, 9};
        Buckethead buckethead = new Buckethead(position2, player2, board);
        pvsz.putSomething(position2, buckethead);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int plantCurrentHealth = ((Plant) pvsz.getBoard().getMatrixBoard()[0][9]).getHealth();
        assertEquals(plantInitialHealth - buckethead.getDamage(), plantCurrentHealth);

        try {
            Thread.sleep(560);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        plantCurrentHealth = ((Plant) pvsz.getBoard().getMatrixBoard()[0][9]).getHealth();
        assertEquals(plantInitialHealth - 2*buckethead.getDamage(), plantCurrentHealth);
    }


    @Test
    void testPlantDeath() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);

        int[] position = new int[]{0, 9};
        SunFlower sunFlower = new SunFlower(position, player1, board);
        pvsz.putSomething(position, sunFlower);
        int[] position2 = new int[]{0, 9};
        NormalZombie normalZombie = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie);
        assertEquals(true, sunFlower.getItsAlive());
        assertEquals(sunFlower, pvsz.getBoard().getMatrixBoard()[0][9]);
        assertTrue(player1.getInventory().contains(sunFlower));
        try {
            Thread.sleep(1700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(false, sunFlower.getItsAlive());
        assertNull(pvsz.getBoard().getMatrixBoard()[0][9]);
        assertFalse(player1.getInventory().contains(sunFlower));
    }

    @Test
    void testZombieDeath() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);

        int[] position = new int[]{0, 1};
        PeaShooter peaShooter = new PeaShooter(position, player1, board);
        pvsz.putSomething(position, peaShooter);
        int[] position2 = new int[]{0, 9};
        NormalZombie normalZombie = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie);

        assertEquals(true, normalZombie.getItsAlive());
        assertEquals(normalZombie, pvsz.getBoard().getTrack(0).get(0));
        assertTrue(player2.getInventory().contains(normalZombie));

        NormalZombie normalZombie2 = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie2);

        int[] wallNutPosition = new int[]{0, 2};
        WallNut wallNut = new WallNut(wallNutPosition, player1, board);
        pvsz.putSomething(wallNutPosition, wallNut);




        try {
            Thread.sleep(7800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(false, normalZombie.getItsAlive());
        assertFalse(player2.getInventory().contains(normalZombie));
    }


    @Test
    void testZombieDistanceAttack() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);

        int[] position = new int[]{0, 1};
        WallNut wallNut = new WallNut(position, player1, board);
        pvsz.putSomething(position, wallNut);
        int[] position2 = new int[]{0, 9};
        ECIZombie eciZombie = new ECIZombie(position2, player2, board);
        pvsz.putSomething(position2, eciZombie);
        int plantInitialHealth = ((Plant) pvsz.getBoard().getMatrixBoard()[0][1]).getHealth();
        try {
            Thread.sleep(3200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int plantCurrentHealth = ((Plant) pvsz.getBoard().getMatrixBoard()[0][1]).getHealth();
        assertEquals(plantInitialHealth - 50, plantCurrentHealth);
    }


    @Test
    void testECIPlant() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, "Normal", player1, player2);

        int[] position = new int[]{0, 1};
        ECIPlant eciPlant = new ECIPlant(position, player1, board);
        pvsz.putSomething(position, eciPlant);
        int[] position2 = new int[]{0, 9};
        NormalZombie normalZombie = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie);
        int moneyAfterPurchase = player1.getMoney();
        try {
            Thread.sleep(20100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int moneyAfterGeneration = player1.getMoney();
        assertEquals(moneyAfterPurchase + 50 + 50, moneyAfterGeneration);
    }
}
