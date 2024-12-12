package com.PlantsvsZombiesDomain;

import java.util.ArrayList;

/**
 * Class Player that extends GameMoves, this class is for the players
 */
public abstract class Player implements GameMoves{

    private String name;
    private int money;
    private boolean isPlant;
    private ArrayList<Something> inventory;

    /**
     * Constructor of the playrt
     * @param name name of the player
     * @param money money of the player
     * @param isPlant if the player is a plant
     */
    public Player(String name, int money, boolean isPlant) {
        this.name = name;
        this.money = money;
        this.isPlant = isPlant;
        this.inventory = new ArrayList<>();
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
    public void putSomething(int[] position, Something something) throws PlantsVsZombiesException{
        if (isPlant){
            if(something instanceof Plant){
                if(money >= ((Plant) something).getPrice()){
                    money -= ((Plant) something).getPrice();
                    ((Plant) something).setOwner(this);
                    inventory.add(something);
                }else{
                    throw new PlantsVsZombiesException(PlantsVsZombiesException.NOT_ENOUGH_MONEY);
                }
            }
        } else {
            if(something instanceof Zombie){
                if(money >= ((Zombie) something).getPrice()){
                    money -= ((Zombie) something).getPrice();
                    ((Zombie) something).setOwner(this);
                    inventory.add(something);
                }else{
                    throw new PlantsVsZombiesException(PlantsVsZombiesException.NOT_ENOUGH_MONEY);
                }
            }
        }
    }

    /**
     * To delete something in the inventory (Plants)
     * @param position position of the something
     * @param something the something
     */
    @Override
    public void deleteSomething(int[] position, Something something) throws PlantsVsZombiesException{
        for(Something inventory : this.inventory){
            if(inventory.equals(something)){
                this.inventory.remove(inventory);
                break;
            }
        }
    }

}
