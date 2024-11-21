package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.Gdx;
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

public class LevelScreen implements Screen {
    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;
    private Stage stage;
    private Texture img;
    private Texture backButton;
    PlantsvsZombies game;

    public LevelScreen(PlantsvsZombies game) {
        this.game = game;

        // Inicializar fondo
        img = new Texture("levelmenu_resized.png");

        // Crear un Stage para gestionar los elementos de UI
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        backButton = new Texture("ButtonBackArrowpng.png");

        // Crear la interfaz de usuario
        createUI();
    }

    private void createUI() {
        // Crear un drawable para el botón de retroceso usando la textura
       
        TextureRegionDrawable backDrawable = new TextureRegionDrawable(new TextureRegion(backButton));

        // Crear un ImageButton con el drawable
        ImageButton backButtonActor = new ImageButton(backDrawable);

        // Agregar un listener para manejar el clic en el botón
        backButtonActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new mainMenu(game)); // Ejemplo de transición a la pantalla del menú principal
            }
        });

        // Crear una tabla para organizar los elementos de la interfaz de usuario
        Table table = new Table();
        table.setFillParent(true); // Hace que la tabla ocupe toda la pantalla
        table.top().left(); // Posiciona la tabla en la esquina superior izquierda

        // Agregar el botón de retroceso a la tabla con tamaño y margen
        table.add(backButtonActor).size(50, 50).pad(10);

        // Agregar la tabla al stage
        stage.addActor(table);
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