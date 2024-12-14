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

    Skin skin;

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
        
        
        //Agregar un listener para manejar el clic en el botón
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
    
    

//    private void loadGame() {
//        // Crea la fuente
//        BitmapFont font = new BitmapFont(); // Fuente predeterminada incluida en LibGDX
//        font.getData().setScale(2f); // Escala para que el texto sea más visible
//
//        // Crea el estilo del Label
//        Label.LabelStyle labelStyle = new Label.LabelStyle();
//        labelStyle.font = font; // Asigna la fuente al estilo
//        labelStyle.fontColor = Color.WHITE; // Color del texto
//
//        // Crea el estilo del TextButton
//        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
//        buttonStyle.font = font; // Asigna la fuente al estilo
//        buttonStyle.fontColor = Color.WHITE; // Color del texto
//
//        // Directorio de archivos guardados
//        FileHandle saveDirectory = Gdx.files.local("saves/");
//
//        // Filtra archivos con extensión .json
//        FileHandle[] saveFiles = saveDirectory.list();
//        Array<FileHandle> filteredFiles = new Array<>();
//        for (FileHandle file : saveFiles) {
//            if (file.name().endsWith(".json")) {
//                filteredFiles.add(file);
//            }
//        }
//
//        if (filteredFiles.size == 0) {
//            System.out.println("No hay archivos de guardado disponibles.");
//            return;
//        }
//
//        // Crear el Stage y la tabla para mostrar los archivos
//        loadStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
//        Table table = new Table();
//        table.setFillParent(true);
//        table.center();
//
//        Label title = new Label("Selecciona un archivo para cargar", labelStyle); // Usa el estilo creado
//        table.add(title).pad(10);
//        table.row();
//
//        for (FileHandle file : filteredFiles) {
//            TextButton fileButton = new TextButton(file.name(), buttonStyle); // Usa el estilo creado
//            fileButton.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    loadGameFromFile(file); // Carga el archivo seleccionado
//                    Gdx.input.setInputProcessor(stage); // Regresa al menú principal
//                    loadStage = null; // Libera el Stage del selector
//                    isLoadStageActive = false; // Desactiva el selector
//                }
//            });
//            table.add(fileButton).pad(5).fillX();
//            table.row();
//        }
//
//        TextButton cancelButton = new TextButton("Cancelar", buttonStyle); // Usa el estilo creado
//        cancelButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                Gdx.input.setInputProcessor(stage); // Regresa al menú principal
//                loadStage = null; // Libera el Stage del selector
//                isLoadStageActive = false; // Desactiva el selector
//            }
//        });
//
//        table.add(cancelButton).pad(10);
//        loadStage.addActor(table);
//        Gdx.input.setInputProcessor(loadStage); // Cambia el InputProcessor al nuevo Stage
//        isLoadStageActive = true; // Activa el nuevo Stage
//    }
    
    private void loadGame() {
        // Inicializa el Skin para el diálogo
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Crea el FileChooser para seleccionar archivos guardados
        FileChooser fileChooser = FileChooser.createPickDialog("Select a file to load", skin, Gdx.files.local("saves/"));

        // Configura el filtro para mostrar solo archivos con extensión .json
        fileChooser.setFilter(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".json");
            }
        });

        // Configura el listener de resultado
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

        // Muestra el diálogo en el stage actual
        fileChooser.show(stage);
    }






    // Carga los datos desde un archivo seleccionado
    private void loadGameFromFile(FileHandle file) {
        try {
            Json json = new Json();
            SaveData saveData = json.fromJson(SaveData.class, file);

            // Cargar las plantas
            for (PlantData plant : saveData.plants) {
                System.out.println("Planta: " + plant.type + " en (" + plant.x + ", " + plant.y + ")");
                // Aquí puedes añadir lógica para recrear las plantas en tu juego
            }

            // Cargar los zombis
            for (ZombieData zombie : saveData.zombies) {
                System.out.println("Zombi: " + zombie.type + " en (" + zombie.x + ", " + zombie.y + ") con vida: " + zombie.health);
                // Aquí puedes añadir lógica para recrear los zombis en tu juego
            }

            // Actualizar el sol
            System.out.println("Cantidad de sol: " + saveData.sun);
            // Aplica el estado del sol al juego actual

        } catch (Exception e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
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
        
     // Renderizar el Stage principal o el selector de archivos
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

