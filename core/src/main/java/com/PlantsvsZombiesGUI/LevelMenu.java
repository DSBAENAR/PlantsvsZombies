package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LevelMenu implements Screen {
    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;
    private Stage stage;
    private Texture img;
    //private Texture backButton;
    private Texture Level_1;
    private Texture Level_2;
    private Texture Level_3;
    private Texture backButton;
    PlantsvsZombies game;

    public LevelMenu(PlantsvsZombies game) {
        this.game = game;

        // Inicializar fondo
        img = new Texture("menu3-01.png");

        // Crear un Stage para gestionar los elementos de UI
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        backButton = new Texture("ButtonBackArrowpng.png");
        // Crear un Stage para gestionar los elementos de UI
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        //backButton = new Texture("ButtonBackArrowpng.png");
        Level_1 = new Texture("L1.png");
        Level_2 = new Texture("L2.png");
        Level_3 = new Texture("L3.png");
        // Crear la interfaz de usuario
        createUI();
    }

    private void createUI() {
        // Crear un drawable para el botón de retroceso usando la textura
    	TextureRegionDrawable backDrawable = new TextureRegionDrawable(new TextureRegion(backButton));
    	TextureRegionDrawable L1Drawabable = new TextureRegionDrawable(new TextureRegion(Level_1));
    	TextureRegionDrawable L2Drawabable = new TextureRegionDrawable(new TextureRegion(Level_2));
    	TextureRegionDrawable L3Drawabable = new TextureRegionDrawable(new TextureRegion(Level_3));
        // Crear un ImageButton con el drawable
        ImageButton backButtonActor = new ImageButton(backDrawable);
        ImageButton level_1_ButtonActor = new ImageButton(L1Drawabable);
        ImageButton level_2_ButtonActor = new ImageButton(L2Drawabable);
        ImageButton level_3_ButtonActor = new ImageButton(L3Drawabable);
         //Agregar un listener para manejar el clic en el botón
        backButtonActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new mainMenu(game)); // Ejemplo de transición a la pantalla del menú principal
            }
        });
        
        level_1_ButtonActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game)); // Ejemplo de transición a la pantalla del menú principal
            }
        });
        // Crear una tabla para organizar los elementos de la interfaz de usuario
        Table table = new Table();
        table.setFillParent(true); // Hace que la tabla ocupe toda la pantalla
        table.top().center(); // Posiciona la tabla en la esquina superior izquierda
        table.add(level_1_ButtonActor);
        table.add(level_2_ButtonActor);
        table.add(level_3_ButtonActor);
        // Agregar la tabla al stage
        stage.addActor(table);
        
        
        
        // Crear una tabla separada para el botón de retroceso
        Table backTable = new Table();
        backTable.setFillParent(true); // La tabla también ocupa toda la pantalla
        backTable.top().left(); // Posicionar la tabla en la esquina superior izquierda
        backTable.add(backButtonActor).size(50, 50).pad(10); // Ajustar tamaño y márgenes

        // Agregar la tabla de retroceso al stage
        stage.addActor(backTable);
        
    }

    
    

    @Override
    public void show() {
    	 InputMultiplexer multiplexer = new InputMultiplexer();

    	    // Añadir el InputAdapter primero para manejar ESC
    	    multiplexer.addProcessor(new InputAdapter() {
    	        @Override
    	        public boolean keyDown(int keycode) {
    	            if (keycode == Input.Keys.ESCAPE) {
    	                game.setScreen(new mainMenu(game)); // Cambiar al menú principal
    	                return true; // Evento manejado
    	            }
    	            return false; // Evento no manejado
    	        }
    	    });

    	    // Añadir el Stage como el segundo procesador
    	    multiplexer.addProcessor(stage);

    	    // Configurar el InputMultiplexer como el procesador de entrada
    	    Gdx.input.setInputProcessor(multiplexer);
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