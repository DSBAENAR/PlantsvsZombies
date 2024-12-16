package com.PlantsvsZombiesDomain;

import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class MachinePlayer extends Player {

    // Tracks for the Original Strategy
    private ArrayList<Zombie> track0Set = new ArrayList<>();
    private ArrayList<Zombie> track1Set = new ArrayList<>();
    private ArrayList<Zombie> track2Set = new ArrayList<>();
    private ArrayList<Zombie> track3Set = new ArrayList<>();
    private ArrayList<Zombie> track4Set = new ArrayList<>();
    private int rowCompleted = 0;
    private int columnCompleted = 1;
    private Timer zombieTimer;

    /**
     * Constructor of HumanPlayer
     *
     * @param name    of the player
     * @param money   its the initial money of the player
     * @param isPlant if the player is a plant (True) or a zombie (False)
     */
    public MachinePlayer(String name, int money, boolean isPlant) throws PlantsVsZombiesException {
        super(name, money, isPlant);
        fillTracksWithZombies(board, this);
    }


    @Override
    public void putSomething(int[] position, Something something) throws PlantsVsZombiesException {
        if(strategy == 1){
            originalStrategy();
        } else if (strategy == 2){
            randomStrategy();
        }
    }

    @Override
    public void deleteSomething(int[] position, Something something) throws PlantsVsZombiesException {

    }

    public void randomStrategy() throws PlantsVsZombiesException {
        Random random = new Random();
        Something[][] matrixBoard = board.getMatrixBoard();
        int boardRows = matrixBoard.length;
        int boardColumns = matrixBoard[0].length;
        int randomColumn = random.nextInt(10);
        int randomRow = random.nextInt(5);
        int randomNumber = random.nextInt(5);
        if (isPlant) {
            if (matrixBoard[randomRow][randomColumn] == null) {
                switch (randomNumber) {
                    case 0:
                        if (money >= 50) {
                            money -= 50;
                            Plant sunFlower = new SunFlower(new int[]{randomRow, randomColumn}, this, board);
                            matrixBoard[randomRow][randomColumn] = sunFlower;
                            inventory.add(sunFlower);
                        } else {
                            throw new PlantsVsZombiesException(PlantsVsZombiesException.NOT_ENOUGH_MONEY);
                        }
                        break;
                    case 1:
                        if (money >= 100) {
                            money -= 100;
                            Plant peaShooter = new PeaShooter(new int[]{randomRow, randomColumn}, this, board);
                            matrixBoard[randomRow][randomColumn] = peaShooter;
                            inventory.add(peaShooter);
                        } else {
                            throw new PlantsVsZombiesException(PlantsVsZombiesException.NOT_ENOUGH_MONEY);
                        }
                        break;
                    case 2:
                        if (money >= 50) {
                            money -= 50;
                            Plant wallNut = new WallNut(new int[]{randomRow, randomColumn}, this, board);
                            matrixBoard[randomRow][randomColumn] = wallNut;
                            inventory.add(wallNut);
                        } else {
                            throw new PlantsVsZombiesException(PlantsVsZombiesException.NOT_ENOUGH_MONEY);
                        }
                        break;
                    case 3:
                        if (money >= 25) {
                            money -= 25;
                            Plant potatoMine = new PotatoMine(new int[]{randomRow, randomColumn}, this, board);
                            matrixBoard[randomRow][randomColumn] = potatoMine;
                            inventory.add(potatoMine);
                        } else {
                            throw new PlantsVsZombiesException(PlantsVsZombiesException.NOT_ENOUGH_MONEY);
                        }
                        break;
                    case 4:
                        if (money >= 75) {
                            money -= 75;
                            Plant eciPlant = new ECIPlant(new int[]{randomRow, randomColumn}, this, board);
                            matrixBoard[randomRow][randomColumn] = eciPlant;
                            inventory.add(eciPlant);
                        } else {
                            throw new PlantsVsZombiesException(PlantsVsZombiesException.NOT_ENOUGH_MONEY);
                        }
                        break;
                }
            }
        } else {
            switch (randomNumber) {
                case 0:
                    NormalZombie normalZombie = new NormalZombie(new int[]{randomRow, randomColumn}, this, board);
                    ArrayList<Zombie> track0 = board.getTrack(randomRow);
                    track0.add(normalZombie);
                    board.setTrack(track0, randomRow);
                    inventory.add(normalZombie);
                    normalZombie.setOwner(this);

                    break;
                case 1:
                    Conehead conehead = new Conehead(new int[]{randomRow, randomColumn}, this, board);
                    ArrayList<Zombie> track1 = board.getTrack(randomRow);
                    track1.add(conehead);
                    board.setTrack(track1, randomRow);
                    inventory.add(conehead);
                    conehead.setOwner(this);
                    break;
                case 2:
                    Buckethead buckethead = new Buckethead(new int[]{randomRow, randomColumn}, this, board);
                    ArrayList<Zombie> track2 = board.getTrack(randomRow);
                    track2.add(buckethead);
                    board.setTrack(track2, randomRow);
                    inventory.add(buckethead);
                    buckethead.setOwner(this);
                    break;
                case 3:
                    BrainsteinZombie brainsteinZombie = new BrainsteinZombie(new int[]{randomRow, randomColumn}, this, board);
                    ArrayList<Zombie> track3 = board.getTrack(randomRow);
                    track3.add(brainsteinZombie);
                    board.setTrack(track3, randomRow);
                    inventory.add(brainsteinZombie);
                    break;
                case 4:
                    ECIZombie eciZombie = new ECIZombie(new int[]{randomRow, randomColumn}, this, board);
                    ArrayList<Zombie> track4 = board.getTrack(randomRow);
                    track4.add(eciZombie);
                    board.setTrack(track4, randomRow);
                    inventory.add(eciZombie);
                    eciZombie.setOwner(this);
                    break;
            }
        }
    }

    /**
     * Original Strategy
     * @throws PlantsVsZombiesException
     */
    public void originalStrategy() throws PlantsVsZombiesException {

        if (isPlant) {
            Something[][] matrixBoard = board.getMatrixBoard();
            int boardRows = matrixBoard.length;
            int boardColumns = matrixBoard[0].length;

            if (rowCompleted >= boardRows) {
                rowCompleted = 0;
                columnCompleted++;
            }

            if (columnCompleted >= boardColumns) {
                throw new PlantsVsZombiesException("No more space on the board for the Original Strategy.");
            }

            if (matrixBoard[rowCompleted][columnCompleted] == null) {
                if (columnCompleted == 1) {
                    matrixBoard[rowCompleted][columnCompleted] = new SunFlower(new int[]{rowCompleted, columnCompleted}, this, board);
                } else if (columnCompleted == 2) {
                    matrixBoard[rowCompleted][columnCompleted] = new PeaShooter(new int[]{rowCompleted, columnCompleted}, this, board);
                } else if (columnCompleted >= 3 && columnCompleted < 5) {
                    matrixBoard[rowCompleted][columnCompleted] = new WallNut(new int[]{rowCompleted, columnCompleted}, this, board);
                } else if (columnCompleted >= 5) {
                    matrixBoard[rowCompleted][columnCompleted] = new PeaShooter(new int[]{rowCompleted, columnCompleted}, this, board);
                }
            }
            rowCompleted++;
            board.setMatrixBoard(matrixBoard);
        } else {
            startZombieReleaseTimer();
        }
    }

    public void startZombieReleaseTimer() {
        if (zombieTimer != null) {
            zombieTimer.cancel();
        }
        zombieTimer = new Timer();
        zombieTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                releaseZombiesFromTracks();
            }
        }, 0, 1000);
    }

    /**
     * Releases the zombies from the tracks
     */
    public void releaseZombiesFromTracks() {
        if (!track0Set.isEmpty()) {
            Zombie zombie = track0Set.removeFirst();
            zombie.setOwner(this);
            inventory.add(zombie);
            ArrayList<Zombie> tempTrack = board.getTrack(0);
            tempTrack.add(zombie);
            board.setTrack(tempTrack, 0);
        }

        if (!track1Set.isEmpty()) {
            Zombie zombie = track1Set.removeFirst();
            zombie.setOwner(this);
            inventory.add(zombie);
            ArrayList<Zombie> tempTrack = board.getTrack(1);
            tempTrack.add(zombie);
            board.setTrack(tempTrack, 1);
        }

        if (!track2Set.isEmpty()) {
            Zombie zombie = track2Set.removeFirst();
            zombie.setOwner(this);
            inventory.add(zombie);
            ArrayList<Zombie> tempTrack = board.getTrack(2);
            tempTrack.add(zombie);
            board.setTrack(tempTrack, 2);
        }

        if (!track3Set.isEmpty()) {
            Zombie zombie = track3Set.removeFirst();
            zombie.setOwner(this);
            inventory.add(zombie);
            ArrayList<Zombie> tempTrack = board.getTrack(3);
            tempTrack.add(zombie);
            board.setTrack(tempTrack, 3);
        }

        if (!track4Set.isEmpty()) {
            Zombie zombie = track4Set.removeFirst();
            zombie.setOwner(this);
            inventory.add(zombie);
            ArrayList<Zombie> tempTrack = board.getTrack(4);
            tempTrack.add(zombie);
            board.setTrack(tempTrack, 4);
        }
    }


    /**
     * Fills the tracks with zombies
     */
    public void fillTracksWithZombies(Board board, Player player) throws PlantsVsZombiesException {
        int[] initialPosition;

        for (int i = 0; i < 5; i++) {
            initialPosition = new int[]{0, 9};
            track0Set.add(new NormalZombie(initialPosition, this, board));
        }

        for (int i = 0; i < 5; i++) {
            initialPosition = new int[]{1, 9};
            track1Set.add(new Conehead(initialPosition, this, board));
        }

        for (int i = 0; i < 5; i++) {
            initialPosition = new int[]{2, 9};
            track2Set.add(new NormalZombie(initialPosition, this, board));
        }

        for (int i = 0; i < 5; i++) {
            initialPosition = new int[]{3, 9};
            track3Set.add(new NormalZombie(initialPosition, this, board));
        }

        for (int i = 0; i < 5; i++) {
            initialPosition = new int[]{4, 9};
            track4Set.add(new Buckethead(initialPosition, this, board));
        }
    }

}




