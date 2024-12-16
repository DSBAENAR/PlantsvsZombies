package com.PlantsvsZombiesDomain;


/**
 * Enum GameModes
 * Represents the available game modes in the Plants vs Zombies game.
 * Each game mode has a display name and a description.
 */
public enum GameModes {
    PvsM("Player vs Machine", "El jugador controla las plantas, los zombies son manejados por la máquina."),
    MvsM("Machine vs Machine", "Tanto las plantas como los zombies son controlados por la máquina."),
    PvsP("Player vs Player", "Dos jugadores compiten: uno controla las plantas y el otro los zombies."),
    Original("Original", "Original Strategy"),
    Random("Random", "Random Strategy");

    private final String displayName;
    private final String description;

    /**
     * Constructor for the GameModes enum.
     *
     * @param displayName The display name of the game mode.
     * @param description The description of the game mode.
     */
    GameModes(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    /**
     * Gets the display name of the game mode.
     *
     * @return The display name of the game mode.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the description of the game mode.
     *
     * @return The description of the game mode.
     */
    public String getDescription() {
        return description;
    }
}
