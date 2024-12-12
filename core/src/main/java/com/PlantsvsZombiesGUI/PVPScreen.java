package com.PlantsvsZombiesGUI;

import com.PlantsvsZombiesDomain.Board;
import com.PlantsvsZombiesDomain.HumanPlayer;
import com.PlantsvsZombiesDomain.Zombie;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;


public class PVPScreen implements Screen {
	private SpriteBatch batch;
    private BitmapFont font;
    private FitViewport viewport;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture secondaryBackgroundTexture;
    private Texture grassTileTexture;
    private Texture buttonMenuTexture;
    private PlantsvsZombies game;
    private Table innerTable;
    private Table menuTable;
    private DragAndDrop dragAndDrop;
    private final int GRID_ROWS = 6; // Número de filas
    public final static int GRID_COLS = 9; // Número de columnas
    public final static float TILE_SIZE = 82; // Tamaño de cada tile
    public static float GRID_X_OFFSET; // Offset dinámico en X
    public static float GRID_Y_OFFSET; // Offset dinámico en Y
    private ShapeRenderer shapeRenderer;
    private Music music;
    private Window optionsMenu;
    private Array<Rectangle> gridCells; // Lista de rectángulos que representan las celdas
    private Rectangle highlightedCell = null;
    private static Label sunCounterLabel;
    private static int sunCounter = 1050;
    private Array<Zombie> zombies; // Lista lógica de zombies
    private Board board;
    
    public PVPScreen(PlantsvsZombies game) {
        this.game = game;
        zombies = new Array<>();
        this.board = new Board(GRID_ROWS, GRID_COLS, new HumanPlayer("Player 1", 50, true), new HumanPlayer("Player 2", 50, true)); // Crear con jugadores

        // Otros inicializadores
        batch = new SpriteBatch();
        stage = new Stage(); // Inicializar el Stage sin un Viewport
        Gdx.input.setInputProcessor(stage);

        // Inicializar recursos
        backgroundTexture = new Texture("Background1.jpg");
        secondaryBackgroundTexture = new Texture("BackgroundPvP.jpg");


        // Generar la fuente desde un archivo TTF
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ZOMBIE.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32; // Tamaño de la fuente
        parameter.color = Color.WHITE; // Color del texto
        parameter.borderWidth = 1; // Borde opcional
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();

        music = Gdx.audio.newMusic(Gdx.files.internal("inGame.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play(); // Inicia la música al comenzar el juego

        shapeRenderer = new ShapeRenderer();

        createUI();
        createGrid();
    }

    
    
    
 // Calcular los offsets para centrar el campo de juego
    private void updateGridOffsets() {
        float fieldWidth = GRID_COLS * TILE_SIZE;
        float fieldHeight = GRID_ROWS * TILE_SIZE;

        GRID_X_OFFSET = (Gdx.graphics.getWidth() - fieldWidth) / 2;
        GRID_Y_OFFSET = (Gdx.graphics.getHeight() - fieldHeight) / 2;
    }
    

    private void createUI() {
        
        // Crear la barra SeedBank como una imagen
        Image seedBankImage = new Image(new Texture("SeedBank.png"));
        
        buttonMenuTexture = new Texture("ButtonMenu.png");
        
        // Crear el ImageButton con imagen de fondo
        Image buttonImage = new Image(buttonMenuTexture);
        
        Label.LabelStyle labelStyle = new Label.LabelStyle();//Estilo Base del texto (No hoover)
        labelStyle.font = font; // Usa la fuente predeterminada
        Label textLabelMenu = new Label("Menu", labelStyle);
        textLabelMenu.setAlignment(Align.center);
     // Crear el Label como botón de opciones
        Label optionsLabel = new Label("Options", labelStyle);
        Label mainMenuInGameLabel = new Label("main menu", labelStyle);
        Label saveLabel = new Label("Save", labelStyle);
        optionsLabel.setAlignment(Align.center); // Alinear el texto al centro
        saveLabel.setAlignment(Align.center); // Alinear el texto al centro
        CharSequence suncounter = String.valueOf(sunCounter);
		sunCounterLabel = new Label(suncounter, labelStyle);
        sunCounterLabel.setAlignment(Align.center); // Alinear texto

     // Configurar la tabla interna
        innerTable = new Table();
        innerTable.top().left();
     // Añadir el contador de soles con tamaño dinámico
        innerTable.add(sunCounterLabel)
            .expandX() // Permitir que la celda ocupe el espacio necesario
            .align(Align.left) // Alinear el contador a la izquierda
            .padRight(20); // Agregar algo de espacio para separarlo de las cartas
        
        
        // Combinar ambos en un Stack
        Stack buttonStack = new Stack();
        buttonStack.add(buttonImage);
        buttonStack.add(textLabelMenu);


        new Table();
        innerTable.top().center();
        innerTable.add(sunCounterLabel).padTop(65).padLeft(-140);
        

        // Crear una tabla principal que contiene la barra y la tabla interna
        Table seedbankTable = new Table();
        seedbankTable.setFillParent(true);
        seedbankTable.top().left();
        seedbankTable.add(seedBankImage).width(400).height(100).row();
        seedbankTable.add(innerTable).padTop(-100).padLeft(10);
        // Agregar la tabla principal al stage
        
        
        menuTable = new Table();
        menuTable.top().right();
        menuTable.setFillParent(true);
        menuTable.add(buttonStack).size(200, 60); // Tamaño del botón
        


        
        stage.addActor(seedbankTable);
        stage.addActor(menuTable);

        dragAndDrop = new DragAndDrop();

        
//        addCard("WallNut.png", "WallNut");

        // Configurar drop target
        addPlantDropTarget();
        
     // Crear un estilo para la ventana
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font; // Usa la misma fuente que ya definiste
        windowStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture("menuGame.png"))); // Fondo del menú

        // Crear la ventana para el menú de opciones
        optionsMenu = new Window("", windowStyle);
        optionsMenu.setSize(450, 600); // Tamaño del menú
        optionsMenu.setPosition(Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f - 200); // Centrar el menú
        optionsMenu.setVisible(false); // Ocultarlo inicialmente

        // Añadir botones al menú de opciones
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font; // Usa la misma fuente para los botones
       

        optionsLabel.addListener(new InputListener() {
            Color hoverColor = new Color(1 / 255f, 233 / 255f, 1 / 255f, 1);

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                // Cambiar color al pasar el cursor
                optionsLabel.setColor(hoverColor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                // Restaurar el color al salir del cursor
                optionsLabel.setColor(Color.WHITE);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Opciones seleccionadas");
                pause();
                // Aquí puedes implementar lo que quieras que haga este botón
                return true;
            }
        });

        // Botón de "Main Menu"
        mainMenuInGameLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	dispose();
            	game.setScreen(new mainMenu(game)); // Cambiar al menú principal
            }
            
            Color color = new Color(1 / 255f, 233 / 255f, 1 / 255f, 1);
        	@Override
        	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        	    // Implementación al entrar
        		mainMenuInGameLabel.setColor(color); // Cambiar color del texto al pasar el cursor
             
        	}

        	@Override
        	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        	    // Implementación al salir
        		mainMenuInGameLabel.setColor(Color.WHITE); // Restaurar el color original al salir
        	}
        });
        
        
     // Botón de "Guardar"
        saveLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	saveGame();
            	dispose();
            	
            }
            
            Color color = new Color(1 / 255f, 233 / 255f, 1 / 255f, 1);
        	@Override
        	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        	    // Implementación al entrar
        		saveLabel.setColor(color); // Cambiar color del texto al pasar el cursor
             
        	}

        	@Override
        	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        	    // Implementación al salir
        		saveLabel.setColor(Color.WHITE); // Restaurar el color original al salir
        	}
        });
        
        // Añadir botones a la ventana
        optionsMenu.add(optionsLabel).pad(10).row();
        optionsMenu.add(mainMenuInGameLabel).pad(10).row();
        optionsMenu.add(saveLabel).pad(10).row();

        // Añadir el menú de opciones al escenario
        stage.addActor(optionsMenu);
        
     
     // Añadir un InputListener para manejar el hover
        buttonStack.addListener(new InputListener() {
        	Color color = new Color(1 / 255f, 233 / 255f, 1 / 255f, 1);
        	private boolean isMenuVisible = false;
        	@Override
        	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        	    // Implementación al entrar
        		textLabelMenu.setColor(color); // Cambiar color del texto al pasar el cursor
             
        	}

        	@Override
        	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        	    // Implementación al salir
        		textLabelMenu.setColor(Color.WHITE); // Restaurar el color original al salir
        	}
        	
        	 @Override
        	    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        	        // Alternar visibilidad del menú al hacer clic
        	        isMenuVisible = !isMenuVisible;
        	        optionsMenu.setVisible(isMenuVisible);
        	        optionsMenu.toFront();
        	        return true; // Indica que el evento fue manejado
        	    }
        });
        optionsMenu.toFront();
        
     // Agregar cartas de ejemplo
        addCard("PeaShooterIcon.png", "PeaShooter", 100);
        addCard("sunflower.png", "Sunflower", 50);
        addCard("WallNutIcon.png", "WallNut", 50);
       
    }

    
    private void addCard(String texturePath, String plantType, int plantPrice) {
        TextureRegionDrawable cardDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(texturePath)));
        ImageButton cardButton = new ImageButton(cardDrawable);

        // Crear el texto del precio
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font; // Usa la misma fuente que el contador de soles
        Label priceLabel = new Label(String.valueOf(plantPrice), labelStyle);
        priceLabel.setAlignment(Align.center);

        // Combinar el botón y el texto en una tabla
        Table cardTable = new Table();
        cardTable.add(cardButton).size(80, 80).row(); // Tamaño fijo para el botón
        cardTable.add(priceLabel).padTop(-15).align(Align.center);// Texto debajo del botón

        // Añadir funcionalidad de arrastrar y soltar para la carta
        dragAndDrop.addSource(new DragAndDrop.Source(cardButton) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setDragActor(new Image(cardDrawable));
                payload.setObject(plantType);
                return payload;
            }
        });

        // Añadir la tabla de la carta al panel
        innerTable.add(cardTable).padRight(10).expandX().align(Align.top);
    }

    private void addPlantDropTarget() {
        // Crear un área de destino
        Table targetArea = new Table();
        targetArea.setFillParent(true); // Hacer que llene toda la pantalla
        stage.addActor(targetArea);    // Añadir el área de destino al escenario

        // Añadir el DragAndDrop Target al área de destino
        dragAndDrop.addTarget(new DragAndDrop.Target(targetArea) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            	highlightedCell = getCellForCoordinates(x, y); // Resaltar la celda
            	return true; // Permitir el arrastre
            }
            	@Override
            	public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            	    String plantType = (String) payload.getObject();
            	    System.out.println("Soltando planta: " + plantType + " en (" + x + ", " + y + ")");

            	    // Obtener la celda en la que se soltó la planta
            	    Rectangle cell = getCellForCoordinates(x, y);
            	    if (cell != null) {
            	        // Calcular el centro de la celda
            	        float centerX = cell.x + cell.width / 2;
            	        float centerY = cell.y + cell.height / 2;
            	        highlightedCell = null;
            	        // Crear la planta y posicionarla en el centro
            	        createPlant(plantType, centerX, centerY);
            	    } else {
            	        System.out.println("No se soltó en una celda válida.");
            	    }
            	}

            });
    }

    private void createPlant(String plantType, float x, float y) {
        try {
            PlantCard plant = PlantFactory.createPlant(plantType, (int) x, (int) y, board);
            if (plant != null) {
                System.out.println("Planta creada correctamente: " + plantType);
                plant.setPosition(x - plant.getWidth() / 2, y - plant.getHeight() / 2); // Centrar la planta
                stage.addActor(plant); // Añadir al escenario
            } else {
                System.out.println("Error: No se pudo crear la planta.");
            }
        } catch (Exception e) {
            System.out.println("Excepción al crear la planta: " + e.getMessage());
        }
    }




    
    private void createGrid() {
        gridCells = new Array<>();
        float fieldWidth = GRID_COLS * TILE_SIZE;
        float fieldHeight = GRID_ROWS * TILE_SIZE;

        GRID_X_OFFSET = (Gdx.graphics.getWidth() - fieldWidth)-670;
        GRID_Y_OFFSET = (Gdx.graphics.getHeight() - fieldHeight)-300;

        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                float x = GRID_X_OFFSET + col * TILE_SIZE;
                float y = GRID_Y_OFFSET + row * TILE_SIZE;

                // Crear un rectángulo para cada celda
                Rectangle cell = new Rectangle(x, y, TILE_SIZE, TILE_SIZE);
                gridCells.add(cell);
            }
        }
    }
    

    private int[] convertCoordinatesToMatrixIndices(float x, float y) {
        int col = (int) ((x - GRID_X_OFFSET) / TILE_SIZE);
        int row = (int) ((y - GRID_Y_OFFSET) / TILE_SIZE);

        // Validar los índices
        if (row >= 0 && row < GRID_ROWS && col >= 0 && col < GRID_COLS) {
            return new int[]{row, col};
        } else {
            System.out.println("Error: Coordenadas fuera del grid. Row: " + row + ", Col: " + col);
            return null; // Coordenadas no válidas
        }
    }

    
    
    
    private void renderGrid() {
        // Habilitar blending para manejar la transparencia
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Dibujar el fondo de la celda resaltada
        if (highlightedCell != null) {
            shapeRenderer.setColor(new Color(1, 1, 0, 0.5f)); // Color amarillo semitransparente
            shapeRenderer.rect(highlightedCell.x, highlightedCell.y, highlightedCell.width, highlightedCell.height);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Dibujar las líneas de la grilla
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED); // Color de las líneas de la grilla

        for (Rectangle cell : gridCells) {
            shapeRenderer.rect(cell.x, cell.y, cell.width, cell.height);
        }

        shapeRenderer.end();
    }

    
    private Rectangle getCellForCoordinates(float x, float y) {
        for (Rectangle cell : gridCells) {
            if (cell.contains(x, y)) {
                return cell; // Retorna la celda que contiene las coordenadas
            }
        }
        return null; // No hay celda en esas coordenadas
    }
    
//    private void spawnZombie(String zombieType, float x, float y) {
//        // Usar la fábrica para crear el zombie
//        AnimatedActor zombie = ZombieFactory.createZombie(zombieType, x, y);
//
//        // Añadir el zombie al escenario
//        stage.addActor(zombie);
//    }
    
   

    public static void incrementSunCounter(int amount) {
        sunCounter += amount;
        sunCounterLabel.setText(String.valueOf(sunCounter)); // Actualizar visualmente
    }

    public static boolean spendSun(int amount) {
        if (sunCounter >= amount) {
            sunCounter -= amount;
            sunCounterLabel.setText(String.valueOf(sunCounter)); // Actualizar visualmente
            return true;
        }
        return false;
    }

    

    
    private void saveGame() {
        // Ruta fija para guardar el archivo
        FileHandle saveFile = Gdx.files.local("saves/gameState.json");

        try {
            // Crear un objeto de datos de guardado
            SaveData saveData = new SaveData();

            // Guardar las plantas
            saveData.plants = new Array<>();
            for (Actor actor : stage.getActors()) {
                if (actor instanceof PlantCard) {
                    PlantCard plant = (PlantCard) actor;
                    PlantData plantData = new PlantData();
//                    plantData.type = plant.getType();
                    plantData.x = (int) plant.getX();
                    plantData.y = (int) plant.getX();
                    saveData.plants.add(plantData);
                }
            }

            // Guardar los zombies
            saveData.zombies = new Array<>();
            for (Zombie zombie : zombies) {
                ZombieData zombieData = new ZombieData();
                zombieData.type = zombie.getClass().getSimpleName();
                zombieData.x = zombie.getPosition()[0];
                zombieData.y = zombie.getPosition()[1];
                zombieData.health = zombie.getHealth();
                saveData.zombies.add(zombieData);
            }

            // Guardar el contador de sol
            saveData.sun = Integer.parseInt(sunCounterLabel.getText().toString());

            // Escribir los datos en el archivo
            Json json = new Json();
            saveFile.writeString(json.toJson(saveData), false);

            System.out.println("Juego guardado automáticamente en: " + saveFile.path());
        } catch (Exception e) {
            System.out.println("Error al guardar el juego: " + e.getMessage());
        }
    }



    @Override
    public void show() {
    }


    @Override
    public void render(float delta) {
    	ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
    	
    	float x = (Gdx.graphics.getWidth() - backgroundTexture.getWidth()) / 2f;
    	float y = (Gdx.graphics.getHeight() - backgroundTexture.getHeight()) / 2f;
    	
        // Dibujar el fondo
        batch.begin();
        batch.draw(secondaryBackgroundTexture, 0, 0);
        batch.draw(backgroundTexture, x, y);

        batch.end();
        renderGrid();
        stage.act(delta);
        stage.draw();
        
    }
    
    public void restoreGameState(SaveData saveData) {
        // Restaurar las plantas
        for (PlantData plantData : saveData.plants) {
        	 // Calcular la posición en píxeles del centro de la celda
            float pixelX = GRID_X_OFFSET + plantData.x * TILE_SIZE + TILE_SIZE / 2;
            float pixelY = GRID_Y_OFFSET + plantData.y * TILE_SIZE + TILE_SIZE / 2;

            System.out.println("Restaurando planta: " + plantData.type + " en posición (" + pixelX + ", " + pixelY + ")");
            createPlant(plantData.type, pixelX, pixelY);
            }

        // Restaurar los zombies
        zombies.clear(); // Limpiar lista actual

        // Restaurar el contador de sol
        sunCounterLabel.setText(String.valueOf(saveData.sun));
    }



	@Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        updateGridOffsets(); // Recalcular los offsets al cambiar el tamaño de la ventana
    }

    @Override
    public void pause() {
    	 music.pause(); // Pausar la música del juego
    }

    @Override
    public void resume() {
    	 music.play(); // Reanudar música
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
    	// Liberar la textura de fondo
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }

        // Liberar texturas adicionales
        if (grassTileTexture != null) {
            grassTileTexture.dispose();
        }

        if (buttonMenuTexture != null) {
            buttonMenuTexture.dispose();
        }

        // Liberar la fuente generada
        if (font != null) {
            font.dispose();
        }

        // Liberar el stage
        if (stage != null) {
            stage.dispose();
        }

        // Liberar el batch de renderizado
        if (batch != null) {
            batch.dispose();
        }

        // Liberar el shapeRenderer
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }

        // Detener y liberar la música
        if (music != null) {
            music.stop();
            music.dispose();
        }
    }
}