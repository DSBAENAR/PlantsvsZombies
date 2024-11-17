package com.PlantsvsZombiesGUI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PlantsvsZombies extends Game {
    SpriteBatch batch;
    

    @Override
    public void create() {
        setBatch(new SpriteBatch());
        this.setScreen(new mainMenu(this));
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
