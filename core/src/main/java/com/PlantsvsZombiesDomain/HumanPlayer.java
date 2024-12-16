package com.PlantsvsZombiesDomain;

/**
 * Class HumanPlayer that extends Player class, this class is for the human players
 */
public class HumanPlayer extends Player {

    /**
     * Constructor of HumanPlayer
     * @param name of the player
     * @param money its the initial money of the player
     * @param isPlant if the player is a plant (True) or a zombie (False)
     */
    public HumanPlayer(String name, int money, boolean isPlant) {
        super(name, money, isPlant);
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
