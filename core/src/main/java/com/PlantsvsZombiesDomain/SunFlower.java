package com.PlantsvsZombiesDomain;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class SunFlower that extends UtilityPlant class, this class is for the sunflower
 */
public class SunFlower extends UtilityPlant{


    /**
     * Constructor SunFlower
     * @param position position of the sunflower
     * @param owner owner of the sunflower
     */
    public SunFlower(int[] position, Player owner) throws PlantsVsZombiesException {
        super(position, 300, 50, 25, owner);
    }

}
