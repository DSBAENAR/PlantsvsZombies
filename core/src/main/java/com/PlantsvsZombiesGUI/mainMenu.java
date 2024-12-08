package com.PlantsvsZombiesGUI;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;



public class mainMenu implements Screen {

	Music backgroundMusic;
    final PlantsvsZombies game;
    private Texture background;
    private Texture btnSaveAndExit;
    private Texture btnPlay;
    private Texture btnLoadGame;
    private Texture btnpvp; //Player vs Player
    private Texture btnmvm; //Machine vs Machine
    
    private Stage stage;


    public mainMenu(final PlantsvsZombies game) {
        this.game = game;

        // Cargar texturas
        background = new Texture("start_resized.png");
        btnSaveAndExit = new Texture("button_exit.png");
        btnPlay = new Texture("button_play.png");
        btnLoadGame = new Texture("button_load.png");
        btnpvp = new Texture("button_pvp.png");
        btnmvm = new Texture("button_mvm.png");
        
        
     // Crear un Stage para gestionar los elementos de UI
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        createUI();
        
    }
    
    
    private void createUI() {
        // Crear un drawable para el botón de retroceso usando la textura
    	TextureRegionDrawable btnSaveAndExitDrawabable = new TextureRegionDrawable(new TextureRegion(btnSaveAndExit));
    	TextureRegionDrawable btnplayDrawabable = new TextureRegionDrawable(new TextureRegion(btnPlay));
    	TextureRegionDrawable btnLoadGameDrawabable = new TextureRegionDrawable(new TextureRegion(btnLoadGame));
    	TextureRegionDrawable btnpvpDrawabable = new TextureRegionDrawable(new TextureRegion(btnpvp));
    	TextureRegionDrawable btnmvmDrawabable = new TextureRegionDrawable(new TextureRegion(btnmvm));
    	
    	
        // Crear un ImageButton con el drawable
        ImageButton btnSaveAndExitActor = new ImageButton(btnSaveAndExitDrawabable);
        ImageButton btnplayActor = new ImageButton(btnplayDrawabable);
        ImageButton btnLoadGameActor = new ImageButton(btnLoadGameDrawabable);
        ImageButton btnpvpGameActor = new ImageButton(btnpvpDrawabable);
        ImageButton btnmvmGameActor = new ImageButton(btnmvmDrawabable);
        
         //Agregar un listener para manejar el clic en el botón
        btnSaveAndExitActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Gdx.app.exit(); // Ejemplo de transición a la pantalla del menú principal
            }
        });
        
        //Agregar un listener para manejar el clic en el botón
        btnplayActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.dispose();
                game.setScreen(new LevelMenu(game)); // Ejemplo de transición a la pantalla del menú principal
            }
        });
        
      //Agregar un listener para manejar el clic en el botón
        btnLoadGameActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	loadGame(); // Ejemplo de transición a la pantalla del menú principal
            }

				
			
        });
        
        Table tableOptions = new Table();
        tableOptions.setFillParent(true); // Hace que la tabla ocupe toda la pantalla
        tableOptions.center();
        tableOptions.row().pad(10, 0, 10, 0);
        tableOptions.add(btnplayActor).fillX().uniformX();
        tableOptions.row().pad(10, 0, 10, 0);
        tableOptions.add(btnLoadGameActor).fillX().uniformX();
        tableOptions.row().pad(10, 0, 10, 0);
        tableOptions.add(btnpvpGameActor).fillX().uniformX();
        tableOptions.row().pad(10, 0, 10, 0);
        tableOptions.add(btnmvmGameActor).fillX().uniformX();
        tableOptions.row().pad(10, 0, 10, 0);
        tableOptions.add(btnSaveAndExitActor).fillX().uniformX();
        
       
        // Agregar la tabla al stage
        stage.addActor(tableOptions);
    
    }
    
    

    private String selectFile(boolean save) {
        final String[] selectedFile = {null}; // Usar un arreglo para almacenar el resultado
        Thread thread = new Thread(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(save ? "Guardar archivo" : "Cargar archivo");

            int result = save ? fileChooser.showSaveDialog(null) : fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile[0] = fileChooser.getSelectedFile().getAbsolutePath();
            }
        });

        thread.start(); // Ejecutar el selector de archivos en un hilo separado

        try {
            thread.join(); // Esperar a que el usuario cierre el diálogo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return selectedFile[0];
    }



    
    private void loadGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JFileChooser chooser = new JFileChooser();
                JFrame frame = new JFrame();
                
                // Configurar el JFrame auxiliar
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
                frame.toFront();
                frame.setVisible(false); // No mostrar realmente la ventana
                int result = chooser.showOpenDialog(frame);
                frame.dispose(); // Cerrar el JFrame auxiliar
                
                if (result == JFileChooser.APPROVE_OPTION) {
                    FileHandle file = Gdx.files.absolute(chooser.getSelectedFile().getAbsolutePath());
                    if (!file.exists()) {
                        System.out.println("El archivo no existe: " + file.path());
                        return;
                    }

                    try {
                        // Leer y cargar los datos guardados
                        Json json = new Json();
                        SaveData saveData = json.fromJson(SaveData.class, file.readString());
                        
                        // Restaurar el estado del juego
                        GameScreen gameScreen = new GameScreen(game);
                        gameScreen.restoreGameState(saveData);
                        Gdx.app.postRunnable(() -> game.setScreen(gameScreen)); // Cambiar de pantalla en el hilo principal

                        System.out.println("Juego cargado con éxito desde: " + file.path());
                    } catch (Exception e) {
                        System.out.println("Error al cargar el archivo: " + e.getMessage());
                    }
                } else {
                    System.out.println("No se seleccionó un archivo.");
                }
            }
        }).start();
    }


    
    
    @Override
    public void render(float delta) {
        // Limpiar pantalla
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Dibujar el fondo y el botón de "Salir"
        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().end();
        
     // Dibujar el Stage (y por lo tanto los botones)
        stage.act(delta);
        stage.draw();
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
        btnPlay.dispose();
    }
}

class SaveData {
    public Array<PlantData> plants;
    public Array<ZombieData> zombies;
    public int sun;
}

class PlantData {
    public String type;
    public int x;
    public int y;
}

class ZombieData {
    public String type;
    public int x;
    public int y;
    public int health;
}

