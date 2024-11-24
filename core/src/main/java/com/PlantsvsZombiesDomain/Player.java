package com.PlantsvsZombiesDomain;

import java.util.ArrayList;

public abstract class Player implements GameMoves{

    private String name;
    private int money;
    private boolean isPlant;
    private ArrayList<Something> inventory;

    public Player(String name, int money, boolean isPlant) {
        this.name = name;
        this.money = money;
        this.isPlant = isPlant;
        this.inventory = new ArrayList<>();
    }

    public ArrayList<Something> getInventory() {
        return inventory;
    }

    @Override
    public void putSomething(int[] position, Something something){
        if (isPlant){
            if(something instanceof Plant){
                if(money >= ((Plant) something).getPrice()){
                    money -= ((Plant) something).getPrice();
                    inventory.add(something);
                }else{
                    System.out.println("No tienes suficiente dinero... Falta clase de excepciones");
                }
            }
        } else {
            if(something instanceof Zombie){
                if(money >= ((Zombie) something).getPrice()){
                    money -= ((Zombie) something).getPrice();
                    inventory.add(something);
                }else{
                    System.out.println("No tienes suficiente dinero... Falta clase de excepciones");
                }
            }
        }
    }

    @Override
    public void deleteSomething(int[] position, Something something){

    }
}