package com.natman.balance;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.natman.balance.screens.MenuScreen;
import com.natman.balance.utils.Convert;

public class BalanceGame extends Game {
	private SpriteBatch batch;
	
	public SpriteBatch getSpriteBatch() { return batch; }
	
	@Override
	public void create() {
		Convert.init(16);
		
		Gdx.graphics.setDisplayMode(800, 480, false);
		
		batch = new SpriteBatch();
		
		setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0.2f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void setScreen(Screen screen) {
		Screen oldScreen = getScreen();
		super.setScreen(screen);
		Gdx.input.setInputProcessor((InputProcessor) screen);
		if (oldScreen != null) oldScreen.dispose();
	}
	
	
	
}
