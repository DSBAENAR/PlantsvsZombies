package com.PlantsvsZombiesDomain;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.PlantsvsZombiesGUI.GameScreen;

/**
 * Class Zombie that extends Something class, this class is for the zombies
 */
public abstract class Zombie extends Something implements Attack{

    protected int[] initalPosition;
    protected int column;
    protected int track;
    protected int health;
    protected int price;
    protected int damage;
    protected long attackSpeed;
    protected Board board;
    protected Player owner;
    protected Timer timerAlive;
    private int timerTicks = 0;
    protected boolean itsAttacking = false;

    /**
     * Constructor for Zombie
     *
     * @param initalPosition initial position
     * @param health         health of the zombie
     * @param price          price of the zombie
     * @param owner          owner
     * @param board
     */
    public Zombie(int[] initalPosition, int health, int price, int damage, long attackSpeed, Player owner, Board board) throws PlantsVsZombiesException {
        super(initalPosition, board);
        this.column = initalPosition[1];
        this.track = initalPosition[0];
        this.health = health;
        this.price = price;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.board = board;
        this.itsAlive = true;
        this.owner = owner;
        startTimer();
    }

    /**
     * get the owner
     * @return owner of the zombie
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * set the owner
     * @param owner owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * get the health
     * @return health
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * get the damage
     * @return health
     */
    public int getDamage() {
        return damage;
    }

    /**
     * set the health
     * @param health health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * get the price
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * get if the zombie is alive
     * @return itsAlive
     */
    public boolean getItsAlive() {
        return itsAlive;
    }

    public int[] getZombiePosition() {
        int[] zombiePosition = new int[]{track, column};
        return zombiePosition;
    }

    /**
     * set if the zombie is alive
     * @param itsAlive
     */
    public void setItsAlive(boolean itsAlive) {
        this.itsAlive = itsAlive;
    }

    /**
     * validate the position, if the column is not 9 throw an exception
     * @param position initial position of the zombie
     * @throws PlantsVsZombiesException if the column is not 9
     */
    protected static int[] validatePosition(float x, float y) throws PlantsVsZombiesException {
        // Convierte las coordenadas de píxeles en índices del grid lógico
        int[] position = convertCoordinatesToMatrixIndices(x, y);

        if (position == null) {
            throw new PlantsVsZombiesException("Las coordenadas están fuera del rango del grid: x=" + x + ", y=" + y);
        }

        return validatePosition(position); // Valida la posición lógica resultante
    }
    
    protected static int[] validatePosition(int[] position) throws PlantsVsZombiesException {
        if (position == null || position.length != 2) {
            throw new PlantsVsZombiesException("La posición proporcionada no es válida: " + (position == null ? "null" : "tamaño incorrecto"));
        }

        int row = position[0];
        int col = position[1];

        // Validar que las coordenadas estén dentro del rango del grid
        if (row < 0 || row >= GameScreen.GRID_ROWS || col < 0 || col >= GameScreen.GRID_COLS) {
            throw new PlantsVsZombiesException("La posición está fuera del rango del grid: row=" + row + ", col=" + col);
        }

        // Validar que el zombie esté en la última columna
        if (col != GameScreen.GRID_COLS - 1) {
            throw new PlantsVsZombiesException("El zombie debe comenzar en la última columna del grid: row=" + row + ", col=" + col);
        }

        return position; // Si todo es válido, retorna la posición lógica
    }

    
    private static int[] convertCoordinatesToMatrixIndices(float x, float y) {
        int col = (int) ((x - GameScreen.GRID_X_OFFSET) / GameScreen.TILE_SIZE);
        int row = (int) ((y - GameScreen.GRID_Y_OFFSET) / GameScreen.TILE_SIZE);

        // Validar que las coordenadas estén dentro del rango
        if (row >= 0 && row < GameScreen.GRID_ROWS && col >= 0 && col < GameScreen.GRID_COLS) {
            return new int[]{row, col};
        } else {
            System.out.println("Error: Coordenadas fuera del grid. Row: " + row + ", Col: " + col);
            return null;
        }
    }


    /**
     * start time
     */
    protected void startTimer() {
        timerAlive = new Timer();
        timerAlive.scheduleAtFixedRate(new TimerTask() {

            private long elapsedTime = 0;

            @Override
            public void run() {
                if (!getItsAlive()) {
                    stopTimer();
                    return;
                }

                if (elapsedTime > 0 && elapsedTime % 3000 == 0) {
                    if(!itsAttacking){
                        moveZombie();
                    }
                }
                if (elapsedTime > 0 && attackSpeed != 0) {
                    if (elapsedTime % attackSpeed  == 0 ){
                        attack();
                        itsAttacking = false;
                    }
                }
                elapsedTime += 500;
            }
        }, 0, 500);
    }

    /**
     * stop timer
     */
    public void stopTimer() {
        if (timerAlive != null) {
            timerAlive.cancel();
            timerAlive = null;
        }
    }

    public void moveZombie() {
        if (this.column == 0) {
            stopTimer();
        } else {
            this.column = (this.column - 1);
            position[1] = this.column;
            setColumn(this.column);
        }
    }

    /**
     * This method is for the attack of each zombie, it can be overridden for different attacks
     */
    public void attack() {
        itsAttacking = true;
        Something[][] matrixBoard = board.getMatrixBoard();
        if(matrixBoard[track][column] != null && matrixBoard[track][column] instanceof Plant){
            Plant targetPlant = (Plant) matrixBoard[track][column];
            Player owner = targetPlant.getOwner();
            int actualHealth = targetPlant.getHealth();
            if ((actualHealth - damage) <= 0){
                targetPlant.setItsAlive(false);
                matrixBoard[track][column] = null;
                board.setMatrixBoard(matrixBoard);
                ArrayList<Something> inventory = owner.getInventory();
                inventory.remove(targetPlant);
                owner.setInventory(inventory);
            } else {
                targetPlant.setHealth(actualHealth - damage);
            }
        }
    }

    /**
     * stop the attack
     */
    public  void stopAttack(){
        if (timerAlive != null) {
            timerAlive.cancel();
            timerAlive = null;
        }
    }
}
