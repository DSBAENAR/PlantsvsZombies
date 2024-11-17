package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LevelScreen implements Screen {
    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;
    private Stage stage;
    private Texture img;
    PlantsvsZombies game;

    public LevelScreen(PlantsvsZombies game) {
        this.game = game;

        // Inicializar fondo
        img = new Texture("levelmenu_resized.png");

        // Crear un Stage para gestionar los elementos de UI
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        // Cargar una Skin (puedes usar un skin estándar o crear el tuyo)
        //skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Crear la interfaz de usuario
        createUI();
    }

    private void createUI() {
        // Crear botones de nivel
        //TextButton level1Button = new TextButton("Level 1", skin);
        //TextButton level2Button = new TextButton("Level 2", skin);
        //TextButton level3Button = new TextButton("Level 3", skin);
        //TextButton level4Button = new TextButton("Level 4", skin);

        // Crear una tabla para organizar los botones
        Table table = new Table();
        table.setFillParent(true); // Hace que la tabla ocupe toda la pantalla
        table.bottom().center(); // Centrar en la parte inferior de la pantalla

        // Agregar los botones en una fila
        //table.add(level1Button).pad(10); // Agrega el botón con un margen
        //table.add(level2Button).pad(10);
        //table.add(level3Button).pad(10);
        //table.add(level4Button).pad(10);

        // Agregar la tabla al Stage
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