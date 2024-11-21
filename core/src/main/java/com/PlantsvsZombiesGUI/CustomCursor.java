package com.PlantsvsZombiesGUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;

public class CustomCursor {
    public static void setCustomCursor() {
        // Cargar la imagen original del cursor
        Pixmap originalPixmap = new Pixmap(Gdx.files.internal("Cursor PVZ BFN.png"));

        // Crear un Pixmap con dimensiones ajustadas (64x64)
        int newWidth = 64; // Potencia de 2 más cercana
        int newHeight = 64; // Potencia de 2 más cercana
        Pixmap resizedPixmap = new Pixmap(newWidth, newHeight, originalPixmap.getFormat());

        // Dibujar la imagen original redimensionada en el nuevo Pixmap
        resizedPixmap.drawPixmap(
            originalPixmap,
            0, 0, originalPixmap.getWidth(), originalPixmap.getHeight(), // Fuente
            0, 0, newWidth, newHeight // Destino
        );

        // Crear el cursor personalizado con la imagen redimensionada
        Cursor cursor = Gdx.graphics.newCursor(resizedPixmap, 0, 0);
        Gdx.graphics.setCursor(cursor);

        // Liberar recursos
        originalPixmap.dispose();
        resizedPixmap.dispose();
    }
}
