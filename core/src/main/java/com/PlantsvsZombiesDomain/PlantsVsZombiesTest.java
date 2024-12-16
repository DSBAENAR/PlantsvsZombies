package com.PlantsvsZombiesDomain;

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
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1,1 , player1, player2);

        int[] position = new int[]{0, 1};
        SunFlower sunFlower1 = new SunFlower(position, player1, board);
        pvsz.putSomething(position, sunFlower1);
        assertEquals(sunFlower1, pvsz.getBoard().getMatrixBoard()[0][1]);
        assertEquals(sunFlower1, pvsz.getPlayer1().getInventory().getFirst());

        assertFalse(pvsz.getTurn());

        int[] position2 = new int[]{0, 9};
        NormalZombie normalZombie1 = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie1);
        assertEquals(normalZombie1, pvsz.getBoard().getTrack(0).getFirst());
        assertEquals(normalZombie1, pvsz.getPlayer2().getInventory().getFirst());
        assertTrue(pvsz.getTurn());
    }


    @Test
    void testGenerateMoneyPlant() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);

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
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);
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
    void testGenerateMoney10Secs() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);
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
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);
        int[] position = new int[]{0, 1};
        pvsz.putSomething(position, new SunFlower(position, player1, board));
        int[] position2 = new int[]{0, 9};
        pvsz.putSomething(position2, new NormalZombie(position2, player2, board));
        try {
            Thread.sleep(3100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(8, pvsz.getBoard().getTrack(0).getFirst().getZombiePosition()[1]);
        try {
            Thread.sleep(3100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(7, pvsz.getBoard().getTrack(0).getFirst().getZombiePosition()[1]);
    }


    @Test
    void testAttack() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);
        int[] position = new int[]{0, 1};
        pvsz.putSomething(position, new PeaShooter(position, player1, board));
        int[] position2 = new int[]{0, 9};
        pvsz.putSomething(position2, new NormalZombie(position2, player2, board));
        int zombieInitialHealth = pvsz.getBoard().getTrack(0).getFirst().getHealth();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int zombieCurrentHealth = pvsz.getBoard().getTrack(0).getFirst().getHealth();
        assertEquals(zombieInitialHealth - 20, zombieCurrentHealth);
    }

    @Test
    void testAttackWhithTwoPeaShooter() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);
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
        int zombieInitialHealth = pvsz.getBoard().getTrack(0).getFirst().getHealth();

        try {
            Thread.sleep(2300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int zombieCurrentHealth = pvsz.getBoard().getTrack(0).getFirst().getHealth();
        assertEquals(zombieInitialHealth - 40, zombieCurrentHealth);
    }


    @Test
    void testZombieAttack() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);
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
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);
        int[] position = new int[]{0, 9};
        SunFlower sunFlower = new SunFlower(position, player1, board);
        pvsz.putSomething(position, sunFlower);
        int[] position2 = new int[]{0, 9};
        NormalZombie normalZombie = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie);
        assertTrue(sunFlower.getItsAlive());
        assertEquals(sunFlower, pvsz.getBoard().getMatrixBoard()[0][9]);
        assertTrue(player1.getInventory().contains(sunFlower));
        try {
            Thread.sleep(1700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(sunFlower.getItsAlive());
        assertNull(pvsz.getBoard().getMatrixBoard()[0][9]);
        assertFalse(player1.getInventory().contains(sunFlower));
    }

    @Test
    void testZombieDeath() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);

        int[] position = new int[]{0, 1};
        PeaShooter peaShooter = new PeaShooter(position, player1, board);
        pvsz.putSomething(position, peaShooter);
        int[] position2 = new int[]{0, 9};
        NormalZombie normalZombie = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie);

        assertTrue(normalZombie.getItsAlive());
        assertEquals(normalZombie, pvsz.getBoard().getTrack(0).getFirst());
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
        assertFalse(normalZombie.getItsAlive());
        assertFalse(player2.getInventory().contains(normalZombie));
    }


    @Test
    void testZombieDistanceAttack() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);

        int[] position = new int[]{0, 1};
        WallNut wallNut = new WallNut(position, player1, board);
        pvsz.putSomething(position, wallNut);
        int[] position2 = new int[]{0, 9};
        ECIZombie eciZombie = new ECIZombie(position2, player2, board);
        assertEquals(50, eciZombie.getDamage());
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
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);

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

    @Test
    void testDontMoveWhileAttacking() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);

        int[] position = new int[]{0, 9};
        WallNut wallNut = new WallNut(position, player1, board);
        pvsz.putSomething(position, wallNut);
        int[] position2 = new int[]{0, 9};
        Conehead conehead = new Conehead(position2, player2, board);
        pvsz.putSomething(position2, conehead);
        int zombieInitialPosition = pvsz.getBoard().getTrack(0).getFirst().getPosition()[0];
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(zombieInitialPosition , pvsz.getBoard().getTrack(0).getFirst().getPosition()[0]);
    }


    @Test
    void testPotatoMine() throws PlantsVsZombiesException {

        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);

        int[] position = new int[]{0, 9};
        PotatoMine potatoMine = new PotatoMine(position, player1, board);
        pvsz.putSomething(position, potatoMine);
        assertFalse(potatoMine.getItsActive());
        try {
            Thread.sleep(14100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(potatoMine.getItsActive());
        int[] position2 = new int[]{0, 9};
        NormalZombie normalZombie = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(normalZombie.getItsAlive());
        assertFalse(potatoMine.getItsAlive());
    }


    @Test
    void testPotatoMineWhileItsNotActive() throws PlantsVsZombiesException {
        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);

        int[] position = new int[]{0, 9};
        int[] position2 = new int[]{0, 9};

        PotatoMine potatoMine2 = new PotatoMine(position, player1, board);
        pvsz.putSomething(position, potatoMine2);
        assertFalse(potatoMine2.getItsActive());
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(potatoMine2.getItsActive());

        NormalZombie normalZombie2 = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie2);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(normalZombie2.getItsAlive());
        assertFalse(potatoMine2.getItsAlive());

    }

    @Test
    void testTwoZombiesDeath() throws PlantsVsZombiesException {
        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);

        int[] position1 = new int[]{1, 8};
        int[] position2 = new int[]{1, 9};
        int[] position3 = new int[]{1, 3};

        WallNut wallNut = new WallNut(position1, player1, board);
        pvsz.putSomething(position1, wallNut);

        NormalZombie normalZombie = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie);


        PeaShooter peaShooter = new PeaShooter(position3, player1, board);
        pvsz.putSomething(position3, peaShooter);

        NormalZombie normalZombie2 = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie2);
        try {
            Thread.sleep(7800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(normalZombie.getItsAlive());
        assertFalse(player2.getInventory().contains(normalZombie));
        assertTrue(player2.getInventory().contains(normalZombie2));
        assertTrue(normalZombie2.getItsAlive());
        try {
            Thread.sleep(7800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(normalZombie2.getItsAlive());
        assertFalse(player2.getInventory().contains(normalZombie2));
    }


    @Test
    void testFillBoard() throws PlantsVsZombiesException {
        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);

        Something[][] matrixBoard = board.getMatrixBoard();

        for (int i = 0; i <= 4; i++) {
            assertTrue(
                matrixBoard[i][0] != null && matrixBoard[i][0] instanceof LawnMower,
                "Expected a LawnMower at row " + i + ", column 0"
            );
        }
    }


    @Test
    void testLawnMower() throws PlantsVsZombiesException {
        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);

        int[] position1 = new int[]{2, 1};
        int[] position3 = new int[]{2, 2};
        int[] position2 = new int[]{1, 9};

        SunFlower sunFlower = new SunFlower(position1, player1, board);
        pvsz.putSomething(position1, sunFlower);

        NormalZombie normalZombie = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie);

        SunFlower sunFlower2 = new SunFlower(position3, player1, board);
        pvsz.putSomething(position3, sunFlower2);

        NormalZombie normalZombie2 = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie2);

        try {
            Thread.sleep(28000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertFalse(normalZombie.getItsAlive());
        assertFalse(normalZombie2.getItsAlive());
        assertEquals(0, board.getTrack(1).size());
        assertFalse(((LawnMower) board.getMatrixBoard()[1][0]).getItsAlive());
    }

    @Test
    void testDeleteSomething() throws PlantsVsZombiesException {
        Player player1 = new HumanPlayer("Barbosa", 1000, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);

        int[] position1 = new int[]{2, 1};
        int[] position2 = new int[]{1, 9};

        SunFlower sunFlower = new SunFlower(position1, player1, board);
        pvsz.putSomething(position1, sunFlower);

        NormalZombie normalZombie = new NormalZombie(position2, player2, board);
        pvsz.putSomething(position2, normalZombie);

        pvsz.deleteSomething(position1, sunFlower);

        assertFalse(sunFlower.getItsAlive());
        assertNull(pvsz.getBoard().getMatrixBoard()[2][1]);
    }


    @Test
    void testOriginalStrategyForPlants() throws PlantsVsZombiesException {
        MachinePlayer player1 = new MachinePlayer("TestPlayer", 1500, true);
        Player player2 = new HumanPlayer("Baena", 1000, false);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);
        player1.originalStrategy();
        Something[][] boardMatrix = player1.getBoard().getMatrixBoard();
        boolean plantPlaced = false;
        if (boardMatrix[0][1] instanceof SunFlower) {
            plantPlaced = true;
        }
        assertTrue(plantPlaced);
        player1.originalStrategy();
        player1.originalStrategy();
        player1.originalStrategy();
        player1.originalStrategy();
        player1.originalStrategy();
        if (boardMatrix[0][2] instanceof PeaShooter) {
            plantPlaced = true;
        }
    }

    @Test
    void testOriginalStrategyForZombies() throws PlantsVsZombiesException {
        MachinePlayer player2 = new MachinePlayer("TestPlayer", 1500, false);
        Player player1 = new HumanPlayer("Baena", 1000, true);
        Board board = new Board(5, 10, player1, player2);
        PlantsVsZombies pvsz = new PlantsVsZombies(board, 10, 1, 1, player1, player2);
        int[] position = new int[]{0, 1};
        SunFlower sunFlower1 = new SunFlower(position, player1, board);
        pvsz.putSomething(position, sunFlower1);

        int[] position2 = new int[]{0, 9};
        player2.putSomething(position, new Conehead(position2, player2, board));
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Zombie> track0 = pvsz.getBoard().getTrack(0);
        ArrayList<Zombie> track1 = pvsz.getBoard().getTrack(1);
        ArrayList<Zombie> track2 = pvsz.getBoard().getTrack(2);
        ArrayList<Zombie> track3 = pvsz.getBoard().getTrack(3);
        ArrayList<Zombie> track4 = pvsz.getBoard().getTrack(4);

        if (!(track0.getFirst() instanceof NormalZombie)) {
            fail();
        }
        if (!(track1.getFirst() instanceof Conehead)) {
            fail();
        }
        if (!(track2.getFirst() instanceof NormalZombie)) {
            fail();
        }
        if (!(track3.getFirst() instanceof NormalZombie)) {
            fail();
        }
        if (!(track4.getFirst() instanceof Buckethead)) {
            fail();
        }
    }

}
