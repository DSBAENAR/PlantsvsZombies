
package com.PlantsvsZombiesGUI;

import com.PlantsvsZombiesDomain.Board;
import com.PlantsvsZombiesDomain.HumanPlayer;
import com.PlantsvsZombiesDomain.Zombie;
import com.PlantsvsZombiesGUI.GameStateManager.GameState;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;


public class GameScreen implements Screen {
	private SpriteBatch batch;
    private BitmapFont font;
    private FitViewport viewport;
    private Stage stage;
    private Texture backgroundTexture;    
    private Texture buttonMenuTexture;
    private PlantsvsZombies game;
    private Table innerTable;
    private Table menuTable;
    private DragAndDrop dragAndDrop;
    public final static int GRID_ROWS = 5; 
    public final static int GRID_COLS = 9; 
    public final static float TILE_SIZE = 150; // Tamaño de cada tile
    public static float GRID_X_OFFSET; // Offset dinámico en X
    public static float GRID_Y_OFFSET; // Offset dinámico en Y
    private ShapeRenderer shapeRenderer;
    private Music music;
    private Window optionsMenu;
    private Array<Rectangle> gridCells;
    private Rectangle highlightedCell = null;
    private static Label sunCounterLabel;
    private Array<Zombie> zombies;
    private Board board;
	private Table plantsTable;
	private boolean isRemovalMode = false;
	private boolean isGameOver = false;
	private Image gameOverImage;
	private Window gameOverMenu;
	
	
	public GameScreen(PlantsvsZombies game) {
        this.game = game;
        zombies = new Array<>();
        new Array<>();
        this.board = new Board(GRID_ROWS, GRID_COLS, new HumanPlayer("Player 1",50,true), new HumanPlayer("Player 2",50,true)); // Crear con jugadores
        
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        stage  = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        GameManager.getGameManager();
		GameManager.setSunCounter(5000);

        backgroundTexture = new Texture("lawn.png");
        
     // Generar la fuente 
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ZOMBIE.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32; 
        parameter.color = Color.WHITE; 
        parameter.borderWidth = 1; 
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
        
        music = Gdx.audio.newMusic(Gdx.files.internal("inGame.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
        shapeRenderer = new ShapeRenderer();
        
        createUI();
        createGrid();
        
    }
    
    
    
     private void updateGridOffsets() {
        float fieldWidth = GRID_COLS * TILE_SIZE;
        float fieldHeight = GRID_ROWS * TILE_SIZE;

        GRID_X_OFFSET = (Gdx.graphics.getWidth() - fieldWidth) / 2;
        GRID_Y_OFFSET = (Gdx.graphics.getHeight() - fieldHeight) / 2;
    }
    

    private void createUI() {
    	createGameOverMenu();

    	
    	Texture gameOverTexture = new Texture("PC Computer - Plants vs Zombies - Game Over Screen.png");
    	gameOverImage = new Image(gameOverTexture);
    	gameOverImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Ajustar al tamaño de la pantalla
    	gameOverImage.setVisible(false); // Ocultar inicialmente
    	stage.addActor(gameOverImage);
        
        buttonMenuTexture = new Texture("ButtonMenu.png");
        
        Image buttonImage = new Image(buttonMenuTexture);
        
        Label.LabelStyle labelStyle = new Label.LabelStyle();//Estilo Base del texto (No hoover)
        labelStyle.font = font; 
        Label textLabelMenu = new Label("Menu", labelStyle);
        textLabelMenu.setAlignment(Align.center);
     
        Label optionsLabel = new Label("Options", labelStyle);
        Label mainMenuInGameLabel = new Label("main menu", labelStyle);
        Label saveLabel = new Label("Save", labelStyle);
        optionsLabel.setAlignment(Align.center); // Alinear el texto al centro
        saveLabel.setAlignment(Align.center); // Alinear el texto al centro
        sunCounterLabel = new Label(String.valueOf(GameManager.getGameManager().getSunCounter()), labelStyle);
        sunCounterLabel.setAlignment(Align.center); // Alinear texto
        initializeSunCounter();
        
        
        Texture sunTexture = new Texture("Sun.png");
        Image sunImage = new Image(new TextureRegionDrawable(new TextureRegion(sunTexture)));

        // Crear una tabla para el sol y el contador de soles
        Table sunTable = new Table();
        sunTable.add(sunImage).size(60, 60).padRight(10); // Imagen del sol con un poco de espacio a la derecha
        sunTable.add(sunCounterLabel).align(Align.left); // Contador de soles
        
        
        // Agregar la pala debajo de las cartas
        Texture shovelTexture = new Texture("Shovel.png");
        ImageButton shovelButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(shovelTexture)));
        innerTable = new Table();
        innerTable.top().left(); 
        innerTable.setFillParent(false); // Ajustar dinámicamente al contenido

        innerTable.add(sunTable)
            .padBottom(10) 
            .expandX()
            .align(Align.center)
            .row(); 
        

        innerTable.add(sunCounterLabel)
            .padBottom(20) 
            .expandX()
            .align(Align.center)
            .row(); 

        innerTable.row(); 
        innerTable.add(shovelButton)
            .size(80, 80)
            .padTop(10) 
            .align(Align.left);
        
    
        
        
        // Combinar ambos en un Stack
        Stack buttonStack = new Stack();
        buttonStack.add(buttonImage);
        buttonStack.add(textLabelMenu);


  
        
        // Crear la tabla principal
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top().left(); 
        mainTable.add(innerTable).padTop(10).padLeft(10); 

        stage.addActor(mainTable);

  
        
        
        menuTable = new Table();
        menuTable.top().right();
        menuTable.setFillParent(true);
        menuTable.add(buttonStack).size(200, 60); 
        

    
        plantsTable = new Table();
        plantsTable.top().left(); 
        
        plantsTable.setFillParent(true); 
        plantsTable.padLeft(10).padTop(250); 
        
       

        stage.addActor(plantsTable);
        stage.addActor(menuTable);
        

        dragAndDrop = new DragAndDrop();

        addPlantDropTarget();     

        
             Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font; 
        windowStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture("menuGame.png"))); 

        optionsMenu = new Window("", windowStyle);
        optionsMenu.setSize(450, 600);
        optionsMenu.setPosition(Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f - 200);
        optionsMenu.setVisible(false);

        
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
       

        optionsLabel.addListener(new InputListener() {
            Color hoverColor = new Color(1 / 255f, 233 / 255f, 1 / 255f, 1);

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                optionsLabel.setColor(hoverColor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                optionsLabel.setColor(Color.WHITE);
            }

        });


        mainMenuInGameLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setScreen(new mainMenu(game));
            	dispose();
            }
            
            Color color = new Color(1 / 255f, 233 / 255f, 1 / 255f, 1);
        	@Override
        	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        	   
        		mainMenuInGameLabel.setColor(color); 
             
        	}

        	@Override
        	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        	    
        		mainMenuInGameLabel.setColor(Color.WHITE); 
        	}
        });
        
        
        
        
        saveLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	saveGame();
            	
            }
            
            Color color = new Color(1 / 255f, 233 / 255f, 1 / 255f, 1);
        	@Override
        	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

        		saveLabel.setColor(color); // Cambiar color del texto al pasar el cursor
             
        	}

        	@Override
        	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        		saveLabel.setColor(Color.WHITE); // Restaurar el color original al salir
        	}
        });
        
        optionsMenu.add(optionsLabel).pad(10).row();
        optionsMenu.add(mainMenuInGameLabel).pad(10).row();
        optionsMenu.add(saveLabel).pad(10).row();

        stage.addActor(optionsMenu);
        
     
        buttonStack.addListener(new InputListener() {
        	Color color = new Color(1 / 255f, 233 / 255f, 1 / 255f, 1);
        	@Override
        	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        		textLabelMenu.setColor(color); // Cambiar color del texto al pasar el cursor
             
        	}

        	@Override
        	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        		textLabelMenu.setColor(Color.WHITE); // Restaurar el color original al salir
        	}
        	
        	@Override
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        	    if (GameStateManager.isPaused()) {
        	        GameStateManager.setGameState(GameStateManager.GameState.RUNNING);
        	        music.play();
        	        optionsMenu.setVisible(false);
        	    } else {
        	        GameStateManager.setGameState(GameStateManager.GameState.PAUSED);
        	        music.pause();
        	        optionsMenu.setVisible(true);
        	        optionsMenu.toFront(); 
        	    }
        	    return true;
        	}

        });
        
        
        shovelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Pala seleccionada: Listo para eliminar una planta.");

                enablePlantRemovalMode();
            }
        });
        optionsMenu.toFront();
        
        addCard("PeaShooterIcon.png", "PeaShooter",100);
        addCard("sunflower.png", "Sunflower",50);
        addCard("WallNutIcon.png", "WallNut", 200);
        addCard("PotatoIcon.png", "PotatoMine", 25);
        addCard("EciPlant.png", "ECIPlant", 75);

    
       
    }
    

   



	private void createGameOverMenu() {
        // Estilo de la ventana
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font; 
        windowStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture("menuGame.png"))); // Fondo del menú (usa tu imagen de fondo)

        gameOverMenu = new Window("", windowStyle);
        gameOverMenu.setSize(450, 600); 
        gameOverMenu.setPosition(
            (Gdx.graphics.getWidth() - gameOverMenu.getWidth()) / 2f, 
            (Gdx.graphics.getHeight() - gameOverMenu.getHeight()) / 2f 
        );
        gameOverMenu.setVisible(false); 


        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        TextButton restartButton = new TextButton("Restart", buttonStyle);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restartGame();
            }
        });

        // Botón para volver al menú principal
        TextButton mainMenuButton = new TextButton("Main Menu", buttonStyle);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToMainMenu(); // Implementa esta lógica para volver al menú principal
            }
        });

        // Añadir botones a la ventana
        gameOverMenu.add(restartButton).size(200, 50).pad(10); // Ajusta el tamaño y espaciado
        gameOverMenu.row(); // Nueva fila
        gameOverMenu.add(mainMenuButton).size(200, 50).pad(10);

        // Añadir la ventana al stage
        stage.addActor(gameOverMenu);
    }

       private void showGameOverMenu() {
        gameOverMenu.setVisible(true);
        gameOverMenu.toFront();
    }

    // Método para reiniciar el juego
    private void restartGame() {
        game.setScreen(new GameScreen(game));
      spawnZombieWithDelay("NormalZombie",1,10f); // Aparece después de 1 segundo
   	 spawnZombieWithDelay("NormalZombie",2,10f); // Aparece después de 1 segundo
   	 spawnZombieWithDelay("NormalZombie",3,10f); // Aparece después de 1 segundo
   	 spawnZombieWithDelay("NormalZombie",4,10f); // Aparece después de 1 segundo
   	 spawnZombieWithDelay("NormalZombie",0,10f); // Aparece después de 1 segundo
    }

    // Método para ir al menú principal
    private void goToMainMenu() {
    	dispose();
        game.setScreen(new mainMenu(game)); // Cambia a la pantalla del menú principal
    }


    
    private void enablePlantRemovalMode() {
        isRemovalMode  = true; // Activa el modo de eliminación
    }

    private void checkForGameOver() {
        if (isGameOver) return;

        for (Actor actor : stage.getActors()) {
            if (actor instanceof ZombieCard) {
                ZombieCard zombie = (ZombieCard) actor;
                if (zombie.getX() <= GameScreen.GRID_X_OFFSET) {
                    triggerGameOver();
                    break;
                }
            }
        }
    }

    private void triggerGameOver() {
        if (!GameStateManager.isGameOver()) {
            GameStateManager.setGameState(GameState.GAME_OVER);

            // Detener la música
            music.stop();

            // Limpiar el escenario y mostrar la imagen de Game Over
            stage.clear();

            // Mostrar la imagen de Game Over si no está ya a
            gameOverImage.setVisible(true);
            stage.addActor(gameOverImage);
            stage.addActor(gameOverMenu);

            // Mostrar el menú de Game Over
            showGameOverMenu();
        }
    }



	



	private void addCard(String texturePath, String plantType, int price) {
        TextureRegionDrawable cardDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(texturePath)));
        ImageButton cardButton = new ImageButton(cardDrawable);

        // Crear un Label para el precio
        Label.LabelStyle priceLabelStyle = new Label.LabelStyle();
        priceLabelStyle.font = font; // Usa la misma fuente
        priceLabelStyle.fontColor = Color.WHITE;
        Label priceLabel = new Label("$" + price, priceLabelStyle);
        

        // Configurar arrastrar y soltar para la carta
        dragAndDrop.addSource(new DragAndDrop.Source(cardButton) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setDragActor(new Image(cardDrawable));
                payload.setObject(plantType);
                return payload;
            }
        
        });

        // Crear una tabla para la carta y el precio en una fila
        Table cardPlantTable = new Table();
        cardPlantTable.add(cardButton).size(80, 80).padRight(10); // Imagen de la carta
        cardPlantTable.add(priceLabel).padLeft(10).center(); // Precio al lado derecho

        // Añadir la tabla de la carta a la tabla vertical
        plantsTable.add(cardPlantTable).padBottom(10).left();
        plantsTable.row();
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

    void createPlant(String plantType, float x, float y) {
        try {
            PlantCard plant = PlantFactory.createPlant(plantType, (int) x, (int) y, board);
            if (plant != null) {
                System.out.println("Planta creada correctamente: " + plantType);
                plant.setPosition(x - plant.getWidth() / 2, y - plant.getHeight() / 2); // Centrar la planta
             // Añadir ClickListener para eliminar la planta
                plant.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (isRemovalMode) {
                            System.out.println("Eliminando planta: " + plantType);
                            plant.remove(); // Eliminar la planta del stage
                            isRemovalMode = false; // Salir del modo de eliminación
                        }
                    }
                });
                stage.addActor(plant); // Añadir al escenario
            } else {
                System.out.println("Error: No se pudo crear la planta.");
            }
        } catch (Exception e) {
            System.out.println("Excepción al crear la planta: " + e.getMessage());
        }
    }
    
    
    void createZombie(String zombieType, float x, float y) {
        try {
            int[] gridPosition = convertCoordinatesToMatrixIndices(x, y);
            if (gridPosition == null) {
                System.out.println("Error: Coordenadas fuera del grid.");
                return;
            }

            int row = gridPosition[0];
            int col = gridPosition[1];

            ZombieCard zombie = ZombieFactory.createZombie(zombieType, row, col, board);
            if (zombie != null) {
                float posX = PVPScreen.GRID_X_OFFSET + col * PVPScreen.TILE_SIZE + PVPScreen.TILE_SIZE / 2 ;
                float posY = PVPScreen.GRID_Y_OFFSET + row * PVPScreen.TILE_SIZE + PVPScreen.TILE_SIZE / 2;

                zombie.setPosition(posX, posY);
                stage.addActor(zombie);
                System.out.println("Zombie creado correctamente: Row=" + row + ", Col=" + col);
            }
        } catch (Exception e) {
            System.out.println("Error al crear zombie: " + e.getMessage());
        }
    }



    
    private void createGrid() {
        gridCells = new Array<>();
        float fieldWidth = GRID_COLS * TILE_SIZE;
        float fieldHeight = GRID_ROWS * TILE_SIZE;

        GRID_X_OFFSET = (Gdx.graphics.getWidth() - fieldWidth)+15;
        GRID_Y_OFFSET = (Gdx.graphics.getHeight() - fieldHeight)-125;

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
    

    public int[] convertCoordinatesToMatrixIndices(float x, float y) {
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

    
    private void initializeLawnMowerActors() {
        for (int i = 0; i < GRID_ROWS; i++) {
            // Crear un LawnMowerActor para cada fila
            LawnMowerActor mowerActor = new LawnMowerActor(i);

            // Agregar el LawnMowerActor al Stage
            stage.addActor(mowerActor);
        }
    }



    
    
    private void renderGrid() {
        Gdx.gl.glEnable(GL20.GL_BLEND);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Dibujar el fondo de la celda resaltada
        if (highlightedCell != null) {
            shapeRenderer.setColor(new Color(1, 1, 0, 0.5f)); // Color amarillo semitransparente
            shapeRenderer.rect(highlightedCell.x, highlightedCell.y, highlightedCell.width, highlightedCell.height);
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
    
    
    public void checkAllPlantsAndZombies() {
        Array<ZombieCard> zombies = new Array<>();
        Array<PlantCard> plants = new Array<>();

        // Clasifica los actores en zombies y plantas
        for (Actor actor : stage.getActors()) {
            if (actor instanceof ZombieCard) {
                zombies.add((ZombieCard) actor);
            } else if (actor instanceof PlantCard) {
                plants.add((PlantCard) actor);
            }
        }

        // Verifica cada planta contra todos los zombies
        for (PlantCard plant : plants) {
            plant.checkForZombies(zombies);
        }
     // Verifica cada zombie contra todas las plantas
        for (ZombieCard zombie : zombies) {
            zombie.checkForPlants(plants);
        }
    }


    
    private void checkCollisions() {
        Array<Actor> actors = stage.getActors();

        // Crear listas separadas para diferentes tipos de actores
        Array<ProjectileActor> projectiles = new Array<>();
        Array<ZombieCard> zombies = new Array<>();
        Array<LawnMowerActor> lawnMowers = new Array<>();

        // Clasificar los actores
        for (Actor actor : actors) {
            if (actor instanceof ProjectileActor) {
                projectiles.add((ProjectileActor) actor);
            } else if (actor instanceof ZombieCard) {
                zombies.add((ZombieCard) actor);
            } else if (actor instanceof LawnMowerActor) {
                lawnMowers.add((LawnMowerActor) actor);
            }
        }

        // Detectar colisiones entre proyectiles y zombies
        for (ProjectileActor projectile : projectiles) {
            Rectangle projectileRect = projectile.getBoundingRectangle();
            for (ZombieCard zombie : zombies) {
                Rectangle zombieRect = zombie.getBoundingRectangle();

                if (projectileRect.overlaps(zombieRect)) {
                    zombie.reduceHealth(20); // Reducir salud del zombie
                    projectile.remove(); // Eliminar el proyectil
                    System.out.println("Proyectil impactó al zombie. Salud restante: " + zombie.getHealth());

                    // Restablecer el estado de la planta
                    PlantCard parentPlant = projectile.getParentPlant();
                    if (parentPlant != null) {
                        parentPlant.setHasActiveProjectile(false);
                    }

                    // Eliminar zombie si está muerto
                    if (!zombie.isAlive()) {
                        zombie.remove();
                    }
                    break; // Un proyectil solo afecta a un zombie
                }
            }
        }

        // Detectar colisiones entre cortadoras y zombies
        for (LawnMowerActor lawnMower : lawnMowers) {
            if (!lawnMower.getItsAlive()) continue; // Ignorar cortadoras inactivas

            Rectangle mowerRect = lawnMower.getBoundingRectangle();
            for (ZombieCard zombie : zombies) {
                Rectangle zombieRect = zombie.getBoundingRectangle();

                if (mowerRect.overlaps(zombieRect)) {
                    System.out.println("Colisión detectada entre cortadora y zombie en fila: " + lawnMower.getRow());
                    lawnMower.activateLawnMower(); // Activar cortadora
                    zombie.remove(); // Eliminar zombie
                    System.out.println("Zombie eliminado por cortadora de césped.");
                    break; // Una cortadora se activa por el primer zombie que encuentra
                }
            }
        }
    }







    public static void incrementSunCounter(int amount) {
        GameManager.getGameManager();
		GameManager.incrementSunCounter(amount);
        updateSunCounterLabel();
    }


    public static boolean spendSun(int amount) {
        boolean success = GameManager.getGameManager().spendSun(amount);
        if (success) {
            updateSunCounterLabel();
        }
        return success;
    }

    
    private static void updateSunCounterLabel() {
        sunCounterLabel.setText(String.valueOf(GameManager.getGameManager().getSunCounter()));
    }
    
    private void initializeSunCounter() {
        GameManager.getGameManager().setOnSunCounterChangeListener(() -> {
            if (sunCounterLabel != null) {
                sunCounterLabel.setText(String.valueOf(GameManager.getGameManager().getSunCounter()));
            }
        });
    }

    

    
    private void saveGame() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        FileChooser fileChooser = FileChooser.createSaveDialog("Save game", skin, Gdx.files.local("saves/"));

        fileChooser.setFileNameEnabled(true);

        fileChooser.setResultListener(new FileChooser.ResultListener() {
            @Override
            public boolean result(boolean success, FileHandle result) {
                if (success) {
                    try {
                    	
                        String filePath = result.path();
                        if (!filePath.endsWith(".json")) {
                            filePath += ".json";
                        }
                        FileHandle saveFile = Gdx.files.local(filePath);
                        SaveData saveData = new SaveData();

                        saveData.plants = new Array<>();
                        for (Actor actor : stage.getActors()) {
                            if (actor instanceof PlantCard) {
                                PlantCard plant = (PlantCard) actor;
                                PlantData plantData = new PlantData();
                                plantData.type = plant.getType();
                                plantData.x = (int) plant.getX();
                                plantData.y = (int) plant.getY();
                                saveData.plants.add(plantData);
                            }
                        }

                        saveData.zombies = new Array<>();
                        for (Zombie zombie : zombies) {
                            ZombieData zombieData = new ZombieData();
                            zombieData.type = zombie.getClass().getSimpleName();
                            zombieData.x = zombie.getPosition()[0];
                            zombieData.y = zombie.getPosition()[1];
                            zombieData.health = zombie.getHealth();
                            saveData.zombies.add(zombieData);
                        }

                        saveData.sun = Integer.parseInt(sunCounterLabel.getText().toString());

                        Json json = new Json();
                        saveFile.writeString(json.toJson(saveData), false);

                        System.out.println("Juego guardado en: " + result.path());
                    } catch (Exception e) {
                        System.out.println("Error al guardar el juego: " + e.getMessage());
                    }
                } else {
                    System.out.println("Guardado cancelado por el usuario.");
                }
                return true;
            }
        });

        fileChooser.setOkButtonText("Save");

        fileChooser.show(stage);
    }




    private void spawnZombieWithDelay(String zombieType, int row, float delaySeconds) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (row < 0 || row >= GRID_ROWS) {
                    System.out.println("Fila fuera del rango del grid: " + row);
                    return;
                }

                int col = GRID_COLS - 1; // Última columna válida
                float visualX = GRID_X_OFFSET + (GRID_COLS + 1) * TILE_SIZE; // +1 o +2 los coloca fuera del grid
                float visualY = GRID_Y_OFFSET + row * TILE_SIZE; // Fila específica

                try {
                    // Crear el zombie
                    ZombieCard zombieCard = ZombieFactory.createZombie(zombieType, row, col, board);

                    if (zombieCard != null) {
                        zombieCard.setPosition(visualX, visualY);
                        stage.addActor(zombieCard);

                        System.out.println("Zombie creado con delay: " + zombieType + " en fila " + row);
                    } else {
                        System.out.println("Error: No se pudo crear el zombie.");
                    }
                } catch (Exception e) {
                    System.out.println("Excepción al generar el zombie: " + e.getMessage());
                }
            }
        }, delaySeconds);
    }



    



    @Override
    public void show() {
     spawnZombieWithDelay("NormalZombie",1,10f); // Aparece después de 1 segundo
   	 spawnZombieWithDelay("NormalZombie",2,10f); // Aparece después de 1 segundo
   	 spawnZombieWithDelay("NormalZombie",3,10f); // Aparece después de 1 segundo
   	 spawnZombieWithDelay("NormalZombie",4,10f); // Aparece después de 1 segundo
   	 spawnZombieWithDelay("NormalZombie",0,10f); // Aparece después de 1 segundo
   	 spawnZombieWithDelay("Buckethead",1,3f); // Aparece después de 1 segundo
  	 spawnZombieWithDelay("Conehead",2,3f); // Aparece después de 1 segundo


    	for (Actor actor : stage.getActors()) {
    	    System.out.println(actor.getClass().getSimpleName() + " en posición (" + actor.getX() + ", " + actor.getY() + ")");
    	}
    	initializeLawnMowerActors();
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {

    	if (!GameStateManager.isGameOver()) {
	        // Dibujar el fondo y otros elementos
	        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
	        batch.begin();
	        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        batch.end();
	        renderGrid();
	        checkForGameOver(); 
	        checkAllPlantsAndZombies();
	        checkCollisions();
	        stage.act(delta);
	        stage.draw();
	    } else {
	        triggerGameOver();
	        stage.draw();
	    }
	 
	 
	 if (GameStateManager.isPaused()) {
	        // Dibuja la escena actual sin actualizar lógica ni animaciones
	        stage.draw();
	        return;
	    }
        
     

        
    }
   
    



	@Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        updateGridOffsets();
    }

    @Override
    public void pause() {
    	 music.pause(); 
    }

    @Override
    public void resume() {
    	 music.play();
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
    	
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }


        if (buttonMenuTexture != null) {
            buttonMenuTexture.dispose();
        }

        if (font != null) {
            font.dispose();
        }

        if (stage != null) {
            stage.dispose();
        }

        if (batch != null) {
            batch.dispose();
        }

        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }

        if (music != null) {
            music.stop();
            music.dispose();
        }
    }



    public void restoreGameState(SaveData saveData) {
        // Restaurar plantas
    	
        for (PlantData plantData : saveData.plants) {
            float pixelX = GRID_X_OFFSET + plantData.x * TILE_SIZE + TILE_SIZE / 2;
            float pixelY = GRID_Y_OFFSET + plantData.y * TILE_SIZE + TILE_SIZE / 2;

            System.out.println("Restaurando planta: " + plantData.type + " en posición (" + pixelX + ", " + pixelY + ")");
            createPlant(plantData.type, pixelX, pixelY);
        }


        GRID_X_OFFSET = (Gdx.graphics.getWidth() - GRID_COLS * TILE_SIZE) / 2;
        GRID_Y_OFFSET = (Gdx.graphics.getHeight() - GRID_ROWS * TILE_SIZE) / 2;

        for (ZombieData zombieData : saveData.zombies) {
            int row = zombieData.y; // fila
            int col = zombieData.x; // columna

            float posX = GRID_X_OFFSET + col * TILE_SIZE; // Esquina izquierda de la celda
            float posY = GRID_Y_OFFSET + row * TILE_SIZE; // Esquina inferior de la celda

            // Ajusta el centro del zombie (si es necesario)
            posX += TILE_SIZE / 4; // Desplazar ligeramente si está desfasado
            posY += TILE_SIZE / 4;


            System.out.println("Restaurando zombie: " + zombieData.type + " en posición (" + posX + ", " + posY + ")");
            
            // Crear la instancia del zombie usando ZombieFactory
            ZombieCard zombie = ZombieFactory.createZombie(zombieData.type, row, col, board);
            if (zombie != null) {
                zombie.setPosition(posX, posY);
                stage.addActor(zombie); // Añadir el zombie al escenario
            } else {
                System.out.println("Error: No se pudo crear el zombie: " + zombieData.type);
            }
        }

        // Restaurar soles
        GameManager.getGameManager().setSunCounter(saveData.sun);
        sunCounterLabel.setText(String.valueOf(saveData.sun));
    }


}
