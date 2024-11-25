package com.PlantsvsZombiesDomain;

public abstract class Something{
    private int[] position;

    public Something(int[] position) {
        this.position = position;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }
}
