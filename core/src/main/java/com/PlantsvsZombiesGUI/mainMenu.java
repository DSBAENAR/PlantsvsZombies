package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class mainMenu implements Screen {
	Music backgroundMusic;
    final PlantsvsZombies game;
    private Texture background;
    private Texture btnSaveAndExit;
    private Texture btnplay;
    private float btnplayX, btnplayY; // Posición del botón "Salir"
    private float btnplayWidth, btnplayHeight; // Tamaño del botón "Salir"
    private float btnSaveAndExitX, btnSaveAndExitY; // Posición del botón "Salir"
    private float btnSaveAndExitWidth, btnSaveAndExitHeight; // Tamaño del botón "Salir"


    public mainMenu(final PlantsvsZombies game) {
        this.game = game;

        // Cargar texturas
        background = new Texture("start_resized.png");
        btnSaveAndExit = new Texture("exit.png");
        btnplay = new Texture("startgame.png");

        
        btnplayWidth = btnplay.getWidth();
        btnplayHeight = btnplay.getHeight();
        btnplayX = Gdx.graphics.getWidth() / 2f - btnplayWidth / 2f; // Centrar el botón horizontalmente
        btnplayY = 700; // Ajusta la posición Y según lo necesario
        
        // Configurar la posición y tamaño del botón "Salir"
        btnSaveAndExitWidth = btnSaveAndExit.getWidth();
        btnSaveAndExitHeight = btnSaveAndExit.getHeight();
        btnSaveAndExitX = Gdx.graphics.getWidth() / 2f - btnSaveAndExitWidth / 2f; // Centrar el botón horizontalmente
        btnSaveAndExitY = 200; // Ajusta la posición Y según lo necesario
      
        
    }
    
    @Override
    public void render(float delta) {
        // Limpiar pantalla
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Dibujar el fondo y el botón de "Salir"
        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0);
        game.getBatch().draw(btnplay, btnplayX, btnplayY, btnplayWidth, btnplayHeight);
        game.getBatch().draw(btnSaveAndExit, btnSaveAndExitX, btnSaveAndExitY, btnSaveAndExitWidth, btnSaveAndExitHeight);
        game.getBatch().end();
       
        
     // Detectar clic en el botón de "Play"
        if (Gdx.input.isTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Convertir coordenadas Y

            // Verificar si el clic está dentro de los límites del botón de "Salir"
            if (mouseX >= btnplayX && mouseX <= btnplayX + btnplayWidth &&
                mouseY >= btnplayY && mouseY <= btnplayY + btnplayHeight) {
                this.dispose(); // Cerrar el juego
                game.setScreen(new LevelMenu(game));
            }
        } 
   
        
        // Detectar clic en el botón de "Salir"
        if (Gdx.input.isTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Convertir coordenadas Y

            // Verificar si el clic está dentro de los límites del botón de "Salir"
            if (mouseX >= btnSaveAndExitX && mouseX <= btnSaveAndExitX + btnSaveAndExitWidth &&
                mouseY >= btnSaveAndExitY && mouseY <= btnSaveAndExitY + btnSaveAndExitHeight) {
                Gdx.app.exit(); // Cerrar el juego
            }
        }
    }
    

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {
    	if (!game.backgroundMusic.isPlaying()) {
            game.backgroundMusic.play();
        }
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        background.dispose();
        btnSaveAndExit.dispose();
    }
}
