package com.natman.balance;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.natman.balance.screens.GameScreen;

public class BalanceGame extends Game {
	private SpriteBatch batch;
	
	public SpriteBatch getSpriteBatch() { return batch; }
	
	@Override
	public void create() {
		Convert.init(16);
		
		Gdx.graphics.setDisplayMode(800, 480, false);
		
		batch = new SpriteBatch();
		
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
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
}
