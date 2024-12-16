package com.PlantsvsZombiesGUI;

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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;



public class PvPMenu implements Screen {

	Music backgroundMusic;
    final PlantsvsZombies game;
    private Texture background;
    private Texture btnSaveAndExit;
    private Texture btnPlay;
    private Texture btnLoadGame;
    private Texture btnpvp; //Player vs Player
    private Stage stage;
    private Texture titleTexture;



    public PvPMenu(final PlantsvsZombies game) {
        this.game = game;

        // Cargar texturas
        background = new Texture("PvPMenu.png");
        btnSaveAndExit = new Texture("button_exit.png");
        btnPlay = new Texture("button_play.png");
        btnLoadGame = new Texture("button_load.png");
        btnpvp = new Texture("button_pvp.png");
        new Texture("button_mvm.png");
        titleTexture = new Texture("TextPvP.png");
        
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
    	// Crear un ImageButton con el drawable
        ImageButton btnSaveAndExitActor = new ImageButton(btnSaveAndExitDrawabable);
        ImageButton btnplayActor = new ImageButton(btnplayDrawabable);
        ImageButton btnLoadGameActor = new ImageButton(btnLoadGameDrawabable);
        //Agregar un listener para manejar el clic en el botón
        btnSaveAndExitActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Gdx.app.exit(); // Ejemplo de transición a la pantalla del menú principal
            }
        });
        
        
      //Agregar un listener para manejar el clic en el botón
        btnLoadGameActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	loadGame(); // Ejemplo de transición a la pantalla del menú principal
            }
          
				
			
        });
        
        
        /// Listener para el botón "Play"
        btnplayActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openGameSettingsDialog();
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
        tableOptions.add(btnSaveAndExitActor).fillX().uniformX();
        
        
        Table titleTable = new Table();
        titleTable.setFillParent(true); 
        titleTable.top(); 
        titleTable.add(new Image(new TextureRegionDrawable(new TextureRegion(titleTexture)))).center().padTop(20);
        stage.addActor(titleTable);

        
       
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
    
    
    private void openGameSettingsDialog() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json")); // Usa tu skin de UI
        GameSettingsDialog dialog = new GameSettingsDialog("Game Setting", skin);

        // Mostrar el diálogo
        dialog.show(stage);

        // Configurar el listener para los resultados
        dialog.setResultListener((time, brains, suns) -> {
            if (time != null && brains != null && suns != null) {
                System.out.println("Final Configuration : Time=" + time + ", Brains=" + brains + ", Suns=" + suns);
                startGame(time, brains, suns);
            } else {
                System.out.println("Configuración inválida. No se inicia la partida.");
            }
        });
    }

    private void startGame(int time, int brains, int suns) {
        int totalTime = time * 60; // Convertir minutos a segundos
        System.out.println("Iniciando partida: Tiempo total (segundos) = " + totalTime);

        // Configurar valores iniciales en GameManager
        GameManager gameManager = GameManager.getGameManager();
        gameManager.setBrainCounter(brains);
        gameManager.setSunCounter(suns);
        gameManager.setTotalGameTime(totalTime);

        // Cambiar a la pantalla PvP
        game.setScreen(new PVPScreen(game,time)); // Asegúrate de que los valores ya están establecidos antes de esta línea
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