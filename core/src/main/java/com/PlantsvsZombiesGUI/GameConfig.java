package com.PlantsvsZombiesGUI;

public class GameConfig {
    public static final int GRID_ROWS = 5;
    public static final int GRID_COLS = 9;
    public static final int TILE_SIZE = 150; // Tamaño de cada tile

    private static float gridXOffset;
    private static float gridYOffset;

    public static void setGridOffsets(float xOffset, float yOffset) {
        gridXOffset = xOffset;
        gridYOffset = yOffset;
    }

    public static float getGridXOffset() {
        return gridXOffset;
    }

    public static float getGridYOffset() {
        return gridYOffset;
    }
}
