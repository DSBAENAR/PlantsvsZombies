package com.PlantsvsZombiesGUI;

import java.io.File;
import java.io.FileFilter;
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
    private Stage stage;
    private Stage loadStage; // Nuevo campo para el Stage del selector de archivos
    private boolean isLoadStageActive = false; // Indica si el selector está activo
    private Skin skin;

    public mainMenu(final PlantsvsZombies game) {
        this.game = game;

        // Cargar texturas
        background = new Texture("start_resized.png");
        btnSaveAndExit = new Texture("button_exit.png");
        btnPlay = new Texture("button_play.png");
        btnLoadGame = new Texture("button_load.png");
        btnpvp = new Texture("button_pvp.png");
        btnmvm = new Texture("button_mvm.png");

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
        
        btnSaveAndExitActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Gdx.app.exit();
            }
        });
        
        btnplayActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.dispose();
                game.setScreen(new LevelMenu(game));            }
        });
        
        btnLoadGameActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	loadGame();
            }
          
				
			
        });
        
        
        btnpvpGameActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.dispose();
                game.setScreen(new PvPMenu(game));
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
        
       
        stage.addActor(tableOptions);
       
    }
    
    

    
    private void loadGame() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Crea el FileChooser para seleccionar archivos guardados
        FileChooser fileChooser = FileChooser.createPickDialog("Select a file to load", skin, Gdx.files.local("saves/"));

        // Configura el filtro para mostrar solo archivos con extensión .json
        fileChooser.setFilter(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".json");
            }
        });

        fileChooser.setResultListener(new FileChooser.ResultListener() {
            @Override
            public boolean result(boolean success, FileHandle result) {
                if (success) {
                    System.out.println("Archivo seleccionado: " + result.path());
                    loadGameFromFile(result); // Llama al método para cargar los datos del archivo
                } else {
                    System.out.println("Selección de archivo cancelada.");
                }
                return true;
            }
        });
        fileChooser.show(stage);
    }






    // Carga los datos desde un archivo seleccionado
    private void loadGameFromFile(FileHandle file) {
        try {
            Json json = new Json();
            SaveData saveData = json.fromJson(SaveData.class, file);

            System.out.println("Estado del juego cargado: " + file.path());

            // Aplicar los datos cargados al juego
            applyGameState(saveData);

        } catch (Exception e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    
    
    
    private void applyGameState(SaveData saveData) {
        GameScreen gameScreen = new GameScreen(game);
        game.setScreen(gameScreen);

        for (PlantData plantData : saveData.plants) {
            System.out.println("Creando planta: " + plantData.type + " en posición (" + plantData.x + ", " + plantData.y + ")");
            gameScreen.createPlant(plantData.type, plantData.x, plantData.y); // Método en GameScreen
        }

        for (ZombieData zombieData : saveData.zombies) {
            System.out.println("Creando zombi: " + zombieData.type + " en posición (" + zombieData.x + ", " + zombieData.y + ")");
            gameScreen.createZombie(zombieData.type, zombieData.x, zombieData.y); // Método en GameScreen
        }

        GameManager.getGameManager().setSunCounter(saveData.sun);
        GameStateManager.setGameState(GameStateManager.GameState.RUNNING);

        System.out.println("Soles cargados: " + saveData.sun);
    }




    
    
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().end();
        
        if (isLoadStageActive && loadStage != null) {
            loadStage.act(delta);
            loadStage.draw();
        } else {
            stage.act(delta);
            stage.draw();
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

