
package com.PlantsvsZombiesGUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	                // Muestra visualmente si es posible soltar aquí
	                return true; // Permitir siempre soltar
	            }

	            @Override
	            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
	                // Crear una nueva planta en la posición donde se suelta la carta
	                String plantType = (String) payload.getObject();
	                createPlant(plantType, x, y);
	            }
	        });
	    }
	    
	 // Método para crear una nueva planta
	    private void createPlant(String plantType, float x, float y) {
	        // Aquí puedes agregar lógica para diferentes tipos de plantas
	        System.out.println("Nueva planta creada: " + plantType + " en posición (" + x + ", " + y + ")");

	        // Crear la planta como una imagen en la posición especificada
	        Image plant = new Image(new Texture(plantType + ".png")); // Asegúrate de que las texturas de las plantas existan
	        plant.setPosition(x - plant.getWidth() / 2, y - plant.getHeight() / 2); // Centrar la planta en el punto de soltar
	        stage.addActor(plant);
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
