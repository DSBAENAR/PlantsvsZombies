
package com.PlantsvsZombiesGUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.Actor;



public class GameScreen implements Screen {
	    public SpriteBatch batch;
	    public BitmapFont font;
	    public FitViewport viewport;
	    private Stage stage;
	    private Texture img;
	    private Texture seedBank;
	    private Texture peaShooterTexture;
	    private Texture sunflower;
	    PlantsvsZombies game;
	    private Table innerTable; // Tabla interna para organizar elementos dinámicamente
	    private DragAndDrop dragAndDrop;
	    private final int GRID_ROWS = 5; // Número de filas
	    private final int GRID_COLS = 9; // Número de columnas
	    private final float CELL_WIDTH = 100; // Ancho de cada celda
	    private final float CELL_HEIGHT = 100; // Altura de cada celda
	    private final float GRID_X_OFFSET = 200; // Posición inicial del grid en X
	    private final float GRID_Y_OFFSET = 50; // Posición inicial del grid en Y

	    
	    public GameScreen(PlantsvsZombies game) {
	        this.game = game;

	        // Inicializar fondo
	        img = new Texture("Background1.jpg");

	        // Crear un Stage para gestionar los elementos de UI
	        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
	        Gdx.input.setInputProcessor(stage);
	        // Crear la interfaz de usuario
	        seedBank = new Texture("SeedBank.png");
	        peaShooterTexture = new Texture("PeaShooter.png");
	        sunflower = new Texture("sunflower.png");
	        createUI();
	    }

//	    private void createUI() {
//	    	 // Crear un drawable para el botón de retroceso usando la textura
//	    	TextureRegionDrawable seedBankDrawable = new TextureRegionDrawable(new TextureRegion(seedBank));
//	    	TextureRegionDrawable peaShooterCardDrawable = new TextureRegionDrawable(new TextureRegion(peaShooterTexture));
//	    	TextureRegionDrawable sunflowerCardDrawable = new TextureRegionDrawable(new TextureRegion(sunflower));
//	    	
//	    	
//	    	ImageButton seedBank = new ImageButton(seedBankDrawable);
//	        ImageButton peaShooterCard = new ImageButton(peaShooterCardDrawable);
//	        ImageButton sunflowerCard = new ImageButton(sunflowerCardDrawable);
//	        
//	        Table innerTable = new Table();
//	        innerTable.top().left(); // Alinear contenido de la tabla en la esquina superior izquierda
//	        // Añadir los elementos a la tabla interna
//	        //innerTable.add(sunflowerCard).size(50, 50).padLeft(10).padRight(10); // Añadir el sol con espaciado
//	        innerTable.add(peaShooterCard).size(80, 80).padLeft(20); // Añadir la carta Peashooter
//	        
//	        
//	        // Crear una tabla principal que contiene la barra y los elementos internos
//	        Table mainTable = new Table();
//	        mainTable.setFillParent(true);
//	        mainTable.top().left(); // Posicionar la barra en la parte superior izquierda
//
//	     // Agregar la barra SeedBank como fondo de la tabla principal
//	        mainTable.add(seedBank).width(400).height(100).row();
//
//	        // Agregar la tabla interna dentro de la barra
//	        mainTable.add(innerTable).padTop(-100).padLeft(10); // Ajustar el espaciado vertical y horizontal
//
//	        // Agregar la tabla principal al stage
//	        stage.addActor(mainTable);
//	    }

	    
	    
	    
	    

	    private void createUI() {
	        // Crear la barra SeedBank como una imagen
	        Image seedBankImage = new Image(new Texture("SeedBank.png"));

	        // Crear la tabla interna para organizar elementos
	        innerTable = new Table();
	        innerTable.top().left(); // Alinear elementos en la parte superior izquierda

	        // Crear una tabla principal que contiene la barra y la tabla interna
	        Table mainTable = new Table();
	        mainTable.setFillParent(true);
	        mainTable.top().left(); // Posicionar la barra en la parte superior izquierda
	        mainTable.add(seedBankImage).width(400).height(100).row(); // Barra como fondo
	        mainTable.add(innerTable).padTop(-100).padLeft(10); // Alinear la tabla interna dentro de la barra

	        // Agregar la tabla principal al stage
	        stage.addActor(mainTable);
	        
	        dragAndDrop = new DragAndDrop();

	        // Agregar una carta inicial (Peashooter) como ejemplo
	        addCard("PeaShooter.png", () -> {
	            System.out.println("¡Carta Peashooter seleccionada!");
	        });
	        
	        addCard("sunflower.png", () -> {
	            System.out.println("¡Carta sunflower seleccionada!");
	        });
	    }

	    private void addCard(String texturePath, Runnable action) {
	        // Cargar la textura de la carta
	        TextureRegionDrawable cardDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(texturePath)));

	        // Crear un ImageButton para la carta
	        ImageButton cardButton = new ImageButton(cardDrawable);

	        // Configurar el ClickListener para manejar acciones
	        cardButton.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	                action.run(); // Ejecutar la acción asociada a la carta
	            }
	        });
	        
	        
	        // Agregar el arrastre a la carta
	        dragAndDrop.addSource(new DragAndDrop.Source(cardButton) {
	            @Override
	            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
	                DragAndDrop.Payload payload = new DragAndDrop.Payload();
	                payload.setDragActor(new Image(cardDrawable)); // Muestra la carta mientras se arrastra
	                payload.setObject("Peashooter"); // Identificar el tipo de planta
	                return payload;
	            }
	        });


	        // Agregar la carta (botón) a la tabla interna
	        innerTable.add(cardButton).size(80, 80).padRight(10);
	        
	    }
	    
	 // Crear un destino para las plantas
	    private void addPlantDropTarget() {
	        dragAndDrop.addTarget(new DragAndDrop.Target(innerTable) {
	            @Override
	            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
	            	// Solo permitir soltar si está dentro del área del campo verde
	                return x >= GRID_X_OFFSET && x <= GRID_X_OFFSET + GRID_COLS * CELL_WIDTH &&
	                       y >= GRID_Y_OFFSET && y <= GRID_Y_OFFSET + GRID_ROWS * CELL_HEIGHT;
	            }

	            @Override
	            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
	            	// Crear una planta en la posición correspondiente
	                String plantType = (String) payload.getObject();
	                createPlant(plantType, x, y);
	            }
	        });
	    }
	    
	 // Método para crear una nueva planta
	    private void createPlant(String plantType, float x, float y) {
	    	 // Determinar la celda del grid donde se soltó
	        int col = (int) ((x - GRID_X_OFFSET) / CELL_WIDTH);
	        int row = (int) ((y - GRID_Y_OFFSET) / CELL_HEIGHT);

	        // Verificar si la celda está dentro del grid
	        if (col >= 0 && col < GRID_COLS && row >= 0 && row < GRID_ROWS) {
	            float plantX = GRID_X_OFFSET + col * CELL_WIDTH;
	            float plantY = GRID_Y_OFFSET + row * CELL_HEIGHT;

	            System.out.println("Planta " + plantType + " creada en celda (" + row + ", " + col + ")");

	            // Crear una animación para la planta
	            Animation<TextureRegion> animation = createPlantAnimation(plantType);
	            AnimatedActor plant = new AnimatedActor(animation, plantX, plantY, CELL_WIDTH, CELL_HEIGHT);

	            // Agregar la planta al stage
	            stage.addActor(plant);
	        }
	    }
	    
	    
	    private Animation<TextureRegion> createPlantAnimation(String plantName) {
	        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(plantName + ".atlas")); // Reemplaza con el archivo correcto
	        return new Animation<>(0.1f, atlas.findRegions("frame"), Animation.PlayMode.LOOP);
	    }



	    @Override
	    public void show() {
	        Gdx.input.setInputProcessor(stage);
	    }

	    @Override
	    public void render(float delta) {
	        // Limpiar pantalla
	        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

	        // Dibujar la imagen de fondo
	        game.getBatch().begin();
	        game.getBatch().draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        game.getBatch().end();

	        // Dibujar el Stage (y por lo tanto los botones)
	        stage.act(delta);
	        stage.draw();
	         
	    }

	    @Override
	    public void resize(int width, int height) {
	        stage.getViewport().update(width, height, true);
	    }

	    @Override
	    public void pause() {}

	    @Override
	    public void resume() {}

	    @Override
	    public void hide() {}

	    @Override
	    public void dispose() {
	        img.dispose();
	        stage.dispose();
	        //skin.dispose();
	    }
	}
