package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PlantsvsZombies extends Game {
    SpriteBatch batch;
    public Music backgroundMusic;
    

    @Override
    public void create() {
        setBatch(new SpriteBatch());
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundsound.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play(); // Inicia la música al comenzar el juego
        this.setScreen(new mainMenu(this));
        CustomCursor.setCustomCursor();
    }

    @Override
    public void render() {
        super.render();
    }

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}
}
