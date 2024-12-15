
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    private Texture buttonMenuTexture;
    private PlantsvsZombies game;
    private Table innerTable;
    private Table menuTable;
    private DragAndDrop dragAndDrop;
    private final int GRID_ROWS = 5; // Número de filas
    public final static int GRID_COLS = 9; // Número de columnas
    public final static float TILE_SIZE = 150; // Tamaño de cada tile
    public static float GRID_X_OFFSET; // Offset dinámico en X
    public static float GRID_Y_OFFSET; // Offset dinámico en Y
    private ShapeRenderer shapeRenderer;
    private Music music;
    private Window optionsMenu;
    private Array<Rectangle> gridCells; // Lista de rectángulos que representan las celdas
    private Rectangle highlightedCell = null;
    private static Label sunCounterLabel;
    private Array<Zombie> zombies; // Lista lógica de zombies
    private Board board;
	private Table plantsTable;
	private boolean isRemovalMode = false;
	private Table zombiesTable;
    public PVPScreen(PlantsvsZombies game) {
        this.game = game;
        zombies = new Array<>();
        new Array<>();
        this.board = new Board(GRID_ROWS, GRID_COLS, new HumanPlayer("Player 1",50,true), new HumanPlayer("Player 2",50,true)); // Crear con jugadores
        // Otros inicializadores
        

        // Inicializar el viewport
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Inicializar otros recursos
        batch = new SpriteBatch();
        stage  = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // Inicializar recursos
        backgroundTexture = new Texture("Background1.jpg");
        
     // Generar la fuente desde un archivo TTF
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ZOMBIE.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32; // Tamaño de la fuente
        parameter.color = Color.WHITE; // Color del texto
        parameter.borderWidth = 1; // Borde opcional
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
        
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
    	
        new Image(new Texture("SeedBank.png"));
        
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
        innerTable.top().left(); // Alinear hacia arriba y a la izquierda
        innerTable.setFillParent(false); // Ajustar dinámicamente al contenido
        // Agregar la tabla de sol y contador de soles
        innerTable.add(sunTable)
            .padBottom(10) // Espaciado debajo
            .expandX()
            .align(Align.center)
            .row(); // Mover a la siguiente fila
        
     // Agregar el contador de soles en la parte superior
        innerTable.add(sunCounterLabel)
            .padBottom(20) // Espaciado hacia abajo
            .expandX()
            .align(Align.center)
            .row(); // Mover a una nueva fila

        innerTable.row(); // Crear nueva fila para la pala
        innerTable.add(shovelButton)
            .size(80, 80)
            .padTop(10) // Espaciado hacia arriba
            .align(Align.left);
        
    
        
        
        // Combinar ambos en un Stack
        Stack buttonStack = new Stack();
        buttonStack.add(buttonImage);
        buttonStack.add(textLabelMenu);


  
        
        // Crear la tabla principal
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top().left(); // Alinear todo en la esquina superior izquierda
        mainTable.add(innerTable).padTop(10).padLeft(10); // Posicionar la tabla interna

        // Agregar la tabla principal al escenario
        stage.addActor(mainTable);

  
        
        
        menuTable = new Table();
        menuTable.top().right();
        menuTable.setFillParent(true);
        menuTable.add(buttonStack).size(200, 60); // Tamaño del botón
        

     // Crear la tabla para las plantas
        plantsTable = new Table();
        plantsTable.top().left(); // Alinear arriba a la izquierda
        plantsTable.setFillParent(true); // No ocupar toda la pantalla
        plantsTable.padLeft(10).padTop(250); // Ajustar margenes para separación
        
        
        zombiesTable = new Table();
        zombiesTable.top().right(); 
        zombiesTable.setFillParent(true); 
        zombiesTable.padLeft(10).padTop(250);

        // Añadir la tabla al stage
        stage.addActor(plantsTable);
        
        stage.addActor(menuTable);

        dragAndDrop = new DragAndDrop();

        

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
        
        
     // Listener para eliminar la planta seleccionada
        shovelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Lógica para eliminar la planta seleccionada
                System.out.println("Pala seleccionada: Listo para eliminar una planta.");

                // Configurar modo de eliminación
                enablePlantRemovalMode();
            }
        });
        optionsMenu.toFront();
        
        addCard("PeaShooterIcon.png", "PeaShooter", "Plant", 100);
        addCard("Sunflower.png", "Sunflower", "Plant", 50);
        addCard("WallNutIcon.png", "WallNut", "Plant", 200);
        addCard("ConeHeadIcon.png", "Conehead", "Zombie", 200);

    
       
    }
    
    
    private static class DragPayload {
        String objectType; // "Plant" o "Zombie"
        String type;       // Tipo específico ("Peashooter", "NormalZombie", etc.)

        DragPayload(String objectType, String type) {
            this.objectType = objectType;
            this.type = type;
        }
    }

    
    private void enablePlantRemovalMode() {
        isRemovalMode  = true; // Activa el modo de eliminación
    }



    private void addCard(String texturePath, String type, String objectType, int price) {
        // Crear el botón con la imagen de la carta
        TextureRegionDrawable cardDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(texturePath)));
        ImageButton cardButton = new ImageButton(cardDrawable);

        // Crear un Label para mostrar el precio
        Label.LabelStyle priceLabelStyle = new Label.LabelStyle();
        priceLabelStyle.font = font; // Usa la fuente
        priceLabelStyle.fontColor = Color.WHITE;
        Label priceLabel = new Label("$" + price, priceLabelStyle);

        // Configurar DragAndDrop para la carta
        dragAndDrop.addSource(new DragAndDrop.Source(cardButton) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setDragActor(new Image(cardDrawable)); // Imagen visible al arrastrar
                payload.setObject(new DragPayload(objectType, type)); // Incluye el tipo (planta o zombie) y el nombre específico
                return payload;
            }
        });

        // Crear la tabla para la carta y el precio
        Table cardPlantTable = new Table();
        cardPlantTable.add(cardButton).size(80, 80).padRight(10); // Imagen
        cardPlantTable.add(priceLabel).padLeft(10).center(); // Precio

        // Añadir la carta a la tabla de cartas (puede ser de plantas o zombies)
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
	            // Resaltar la celda donde se arrastra
	            highlightedCell = getCellForCoordinates(x, y);
	            return true; // Permitir el arrastre
	        }

	        @Override
	        public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
	            DragPayload dragPayload = (DragPayload) payload.getObject();
	            String objectType = dragPayload.objectType;
	            String type = dragPayload.type;

	            System.out.println("Soltando objeto: " + type + " (" + objectType + ") en (" + x + ", " + y + ")");

	            // Obtener la celda en la que se soltó
	            Rectangle cell = getCellForCoordinates(x, y);
	            if (cell != null) {
	                // Calcular el centro de la celda
	                float centerX = cell.x + cell.width / 2;
	                float centerY = cell.y + cell.height / 2;
	                highlightedCell = null;

	                // Crear la planta o el zombie según el tipo
	                if (objectType.equals("Plant")) {
	                    createPlant(type, centerX, centerY);
	                } else if (objectType.equals("Zombie")) {
	                    createZombie(type, centerX, centerY);
	                } else {
	                    System.out.println("Tipo de objeto desconocido: " + objectType);
	                }
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
    
    
    private void createZombie(String plantType, float x, float y) {
        try {
            ZombieCard zombie = ZombieFactory.createZombie(plantType, (int) x, (int) y, TILE_SIZE);
            if (zombie != null) {
                System.out.println("Planta creada correctamente: " + plantType);
                zombie.setPosition(x - zombie.getWidth() / 2, y - zombie.getHeight() / 2); // Centrar la planta
             // Añadir ClickListener para eliminar la planta
                zombie.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (isRemovalMode) {
                            System.out.println("Eliminando planta: " + plantType);
                            zombie.remove(); // Eliminar la planta del stage
                            isRemovalMode = false; // Salir del modo de eliminación
                        }
                    }
                });
                stage.addActor(zombie); // Añadir al escenario
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

        GRID_X_OFFSET = (Gdx.graphics.getWidth() - fieldWidth)-210;
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
        Gdx.gl.glEnable(GL20.GL_BLEND);
        
        // Dibujar contornos de todas las celdas en rojo
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED); // Color para las líneas de las celdas
        for (Rectangle cell : gridCells) {
            shapeRenderer.rect(cell.x, cell.y, cell.width, cell.height);
        }
        shapeRenderer.end();
        
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
                        System.out.println("Zombie eliminado.");
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
        updateSunCounterLabel(); // Actualizar la etiqueta visual
    }


    public static boolean spendSun(int amount) {
        boolean success = GameManager.getGameManager().spendSun(amount);
        if (success) {
            updateSunCounterLabel(); // Actualizar la etiqueta visual
        }
        return success;
    }

    
    private static void updateSunCounterLabel() {
        sunCounterLabel.setText(String.valueOf(GameManager.getGameManager().getSunCounter()));
    }
    
    private void initializeSunCounter() {
        GameManager.getGameManager().setOnSunCounterChange(() -> {
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




    private void spawnZombie(String zombieType, float x, float y) {

        int[] indices = convertCoordinatesToMatrixIndices(x, y);

        if (indices == null) {
            System.out.println("Coordenadas fuera del grid. No se pudo crear el zombie.");
            return;
        }

        int row = indices[0]; 
        int col = GRID_COLS - 1; 

        if (row < 0 || row >= GRID_ROWS) {
            System.out.println("Fila fuera del rango del grid: " + row);
            return;
        }

        float visualX = GRID_X_OFFSET + (col + 1) * TILE_SIZE;
        float visualY = GRID_Y_OFFSET + row * TILE_SIZE;

        try {
            // Crear el zombie utilizando la fábrica
            ZombieCard zombieCard = ZombieFactory.createZombie(zombieType, row, col, TILE_SIZE);

            if (zombieCard != null) {
                // Añadir el zombie al escenario
                zombieCard.setPosition(visualX, visualY);
                stage.addActor(zombieCard);

                System.out.println("Zombie creado: " + zombieType + " en fila " + row + " y fuera del borde derecho.");
            } else {
                System.out.println("Error: No se pudo crear el zombie.");
            }
        } catch (Exception e) {
            System.out.println("Excepción al generar el zombie: " + e.getMessage());
        }
    }
    
    



    @Override
    public void show() {
    	spawnZombie("NormalZombie", Gdx.graphics.getWidth() - 300, GRID_Y_OFFSET + 0 * TILE_SIZE);
    	spawnZombie("NormalZombie", Gdx.graphics.getWidth() - 100, GRID_Y_OFFSET + 1 * TILE_SIZE);
    	spawnZombie("NormalZombie", Gdx.graphics.getWidth() - 100, GRID_Y_OFFSET + 2 * TILE_SIZE);
    	spawnZombie("NormalZombie", Gdx.graphics.getWidth() - 100, GRID_Y_OFFSET + 3 * TILE_SIZE);
    	spawnZombie("NormalZombie", Gdx.graphics.getWidth() - 100, GRID_Y_OFFSET + 4 * TILE_SIZE);
    	spawnZombie("NormalZombie", Gdx.graphics.getWidth() - 100, GRID_Y_OFFSET + 5 * TILE_SIZE);
    	for (Actor actor : stage.getActors()) {
    	    System.out.println(actor.getClass().getSimpleName() + " en posición (" + actor.getX() + ", " + actor.getY() + ")");
    	}
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {
    	ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();
        batch.draw(backgroundTexture,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        renderGrid();
        checkAllPlantsAndZombies(); 
        checkCollisions();
        stage.act(delta);
        stage.draw();
        
     // Dibujar colliders
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (Actor actor : stage.getActors()) {
            if (actor instanceof PlantCard) {
                PlantCard plant = (PlantCard) actor;
                shapeRenderer.setColor(0, 0, 1, 1); // Azul para plantas
                Rectangle rect = plant.getBoundingRectangle();
                shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            } else if (actor instanceof ZombieCard) {
                ZombieCard zombie = (ZombieCard) actor;
                shapeRenderer.setColor(1, 0, 0, 1); // Rojo para zombies
                Rectangle rect = zombie.getBoundingRectangle();
                shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            }
            
            else if (actor instanceof LawnMowerActor) {
            	LawnMowerActor lawn = (LawnMowerActor) actor;
                shapeRenderer.setColor(1, 1, 0, 1); // Rojo para zombies
                Rectangle rect = lawn.getBoundingRectangle();
                shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            }
        }
        

        shapeRenderer.end();
        stage.act(delta);
        stage.draw();
        
    }
   
    public void restoreGameState(SaveData saveData) {
        for (PlantData plantData : saveData.plants) {
            float pixelX = GRID_X_OFFSET + plantData.x * TILE_SIZE + TILE_SIZE / 2;
            float pixelY = GRID_Y_OFFSET + plantData.y * TILE_SIZE + TILE_SIZE / 2;

            System.out.println("Restaurando planta: " + plantData.type + " en posición (" + pixelX + ", " + pixelY + ")");
            createPlant(plantData.type, pixelX, pixelY);
            }

        zombies.clear();

        sunCounterLabel.setText(String.valueOf(saveData.sun));
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
}
