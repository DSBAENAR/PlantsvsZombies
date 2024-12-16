package com.PlantsvsZombiesDomain;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PlantsVsZoombies Exception class
 */
public class PlantsVsZombiesException extends Exception {

    private static final Logger LOGGER = Logger.getLogger(PlantsVsZombiesException.class.getName());
    public final static String SOMETHING_ALREADY_IN_POSITION = "There is already something in this position";
    public final static String NO_SOMETHING_IN_POSITION = "There is no something in this position";
    public final static String DONT_MATCH_OBJECTS = "The object at the position does not match the specified object.";
    public final static String NOT_ENOUGH_MONEY = "You don't have enough money";
    public final static String ARGUMENTS_NOT_VALID = "The arguments are not valid";
    public final static String NO_VALID_GAMEMODE = "No valid game mode";
    public final static String NO_VALID_STRATEGY = "No valid strategy for this mode";

    /**
     * Constructor of the PlantsVsZombiesException
     * @param message message
     */
    public PlantsVsZombiesException(String message) {
        super(message);
        LOGGER.log(Level.SEVERE, "PlantsVsZombiesException: {0}", message);
    }

}
