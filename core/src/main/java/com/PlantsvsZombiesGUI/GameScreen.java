package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	private Peashooter peashooter;
    private SpriteBatch batch;
    private BitmapFont font;
    private FitViewport viewport;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture grassTileTexture;
    private Texture houseTexture;
    private PlantsvsZombies game;
    private Table innerTable;
    private DragAndDrop dragAndDrop;
    private final int GRID_ROWS = 5; // Número de filas
    private final int GRID_COLS = 9; // Número de columnas
    private final float TILE_SIZE = 100; // Tamaño de cada tile
    private float GRID_X_OFFSET; // Offset dinámico en X
    private float GRID_Y_OFFSET; // Offset dinámico en Y
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private TextureRegion[] gridCells;
    
    public GameScreen(PlantsvsZombies game) {
        this.game = game;
        // Inicializar el viewport
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Inicializar otros recursos
        batch = new SpriteBatch();
        stage  = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    	// Configurar la cámara
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // Inicializar recursos
        backgroundTexture = new Texture("lawn.png");
        
        createUI();;
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

        // Crear la tabla interna para organizar elementos
        innerTable = new Table();
        innerTable.top().left();

        // Crear una tabla principal que contiene la barra y la tabla interna
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top().left();
        mainTable.add(seedBankImage).width(400).height(100).row();
        mainTable.add(innerTable).padTop(-100).padLeft(10);

        // Agregar la tabla principal al stage
        stage.addActor(mainTable);

        dragAndDrop = new DragAndDrop();

        // Agregar cartas de ejemplo
        addCard("PeaShooterIcon.png", "Peashooter");
        addCard("sunflower.png", "Sunflower");
//        addCard("WallNut.png", "WallNut");

        // Configurar drop target
        addPlantDropTarget();
    }

    
    private void addCard(String texturePath, String plantType) {
        TextureRegionDrawable cardDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(texturePath)));
        ImageButton cardButton = new ImageButton(cardDrawable);

        // Agregar funcionalidad de arrastrar y soltar para la carta
        dragAndDrop.addSource(new DragAndDrop.Source(cardButton) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setDragActor(new Image(cardDrawable)); // Imagen arrastrada
                payload.setObject(plantType); // Tipo de planta
                return payload;
            }
        });

        // Añadir el botón de la carta al panel
        innerTable.add(cardButton).size(80, 80).padRight(10);
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
            	return true; // Permitir el arrastre
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                String plantType = (String) payload.getObject();
                System.out.println("Soltando planta: " + plantType + " en (" + x + ", " + y + ")");
                createPlant(plantType, x, y); // Crear la planta
            }
        });
    }

    private void createPlant(String plantType, float x, float y) {
        try {
            Plant plant = PlantFactory.createPlant(plantType, x, y, null);
            if (plant != null) {
                System.out.println("Planta creada correctamente: " + plantType);
                stage.addActor(plant); // Añadir al escenario
            } else {
                System.out.println("Error: No se pudo crear la planta.");
            }
        } catch (Exception e) {
            System.out.println("Excepción al crear la planta: " + e.getMessage());
        }
    }
    
//    public void drawGrid() {
//        shapeRenderer.setColor(1, 1, 1, 1); // Color blanco para las líneas
//
//        // Dibujar líneas horizontales
//        for (int row = 0; row <= GRID_ROWS; row++) {
//            float y = GRID_Y_OFFSET + row * CELL_HEIGHT;
//            shapeRenderer.line(GRID_X_OFFSET, y, GRID_X_OFFSET + GRID_COLS * CELL_WIDTH, y);
//        }
//
//        // Dibujar líneas verticales
//        for (int col = 0; col <= GRID_COLS; col++) {
//            float x = GRID_X_OFFSET + col * CELL_WIDTH;
//            shapeRenderer.line(x, GRID_Y_OFFSET, x, GRID_Y_OFFSET + GRID_ROWS * CELL_HEIGHT);
//        }
//    }
    
    
    private void drawTileGrid(SpriteBatch batch) {
		// TODO Auto-generated method stub
    	for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                float x = GRID_X_OFFSET + col * TILE_SIZE;
                float y = GRID_Y_OFFSET + row * TILE_SIZE;
                batch.draw(grassTileTexture, x, y, TILE_SIZE, TILE_SIZE);
            }
        }
	}


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {
    	ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Dibujar el fondo
        batch.begin();
        batch.draw(backgroundTexture,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Dibujar el escenario
        stage.act(delta);
        stage.draw();
        
        
    }




	@Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        updateGridOffsets(); // Recalcular los offsets al cambiar el tamaño de la ventana
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        stage.dispose();
        batch.dispose();
    }
}