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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;



public class mainMenu implements Screen {

	Music backgroundMusic;
    final PlantsvsZombies game;
    private Texture background;
    private Texture btnSaveAndExit;
    private Texture btnPlay;
    private Texture btnLoadGame;
    private Texture btnpvp; //Player vs Player
    private Texture btnmvm; //Machine vs Machine
    private Skin skin;
    
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
//        skin = new Skin(Gdx.files.internal("uiskin.json"));
        
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
        
//        FileSelector fileSelector = new FileSelector(stage, skin, new FileSelector.FileSelectorCallback() {
//            @Override
//            public void onFileSelected(FileHandle file) {
//                System.out.println("Archivo seleccionado: " + file.path());
//                // Aquí puedes guardar o cargar datos desde el archivo
//            }
//
//            @Override
//            public void onCancel() {
//                System.out.println("Selección de archivo cancelada.");
//            }
//
//            @Override
//            public void onError(String error) {
//                System.out.println("Error: " + error);
//            }
//        });
//
//        // Mostrar el selector
//        fileSelector.show();
//    
    }
    
    

    private void loadGame() {
        // Ruta fija para cargar el archivo
        FileHandle saveFile = Gdx.files.local("saves/gameState.json");

        if (saveFile.exists()) {
            try {
                // Leer los datos guardados
                Json json = new Json();
                SaveData saveData = json.fromJson(SaveData.class, saveFile.readString());

                // Cambiar a GameScreen y restaurar el estado
                GameScreen gameScreen = new GameScreen(game);
                gameScreen.restoreGameState(saveData);
                game.setScreen(gameScreen);

                System.out.println("Juego cargado automáticamente desde: " + saveFile.path());
            } catch (Exception e) {
                System.out.println("Error al cargar el juego: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró un archivo de guardado en: " + saveFile.path());
        }
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
    public float x;
    public float y;
}

class ZombieData {
    public String type;
    public int x;
    public int y;
    public int health;
}

