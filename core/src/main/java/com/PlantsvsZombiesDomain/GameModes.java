package com.PlantsvsZombiesDomain;

public enum GameModes {
    PvsM("Player vs Machine", "El jugador controla las plantas, los zombies son manejados por la máquina."),
    MvsM("Machine vs Machine", "Tanto las plantas como los zombies son controlados por la máquina."),
    PvsP("Player vs Player", "Dos jugadores compiten: uno controla las plantas y el otro los zombies.");

    private final String displayName;
    private final String description;


    GameModes(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }


    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}
