package com.natman.balance.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.natman.balance.BalanceGame;

public class MenuScreen implements Screen {

	private BalanceGame game;
	private float highScore = 0f;
	
	private SpriteBatch batch;
	
	private BitmapFont font;
	
	public MenuScreen(BalanceGame game) {
		this.game = game;
		
		Preferences prefs = Gdx.app.getPreferences("CrashingDownData");
		highScore = prefs.getFloat("HighScore");
		
		batch = game.getSpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
	}
	
	@Override
	public void render(float delta) {
		batch.begin();
		
		font.draw(batch, "furthest traveled: " + highScore, 0, Gdx.graphics.getHeight());
		
		TextBounds bounds = font.getBounds("CRASHING DOWN");
		
		font.draw(batch, "CRASHING DOWN", Gdx.graphics.getWidth() / 2 - bounds.width / 2, 2 * Gdx.graphics.getHeight() / 3 + bounds.height / 2);
		
		bounds = font.getBounds("Instructions: I");
		
		float x = Gdx.graphics.getWidth() / 2 - bounds.width / 2;
		float y = Gdx.graphics.getHeight() / 2 + bounds.height / 2;
		
		font.draw(batch, "Instructions: I", x, y);
		
		bounds = font.getBounds("Play Game: SPACE");
		
		x = Gdx.graphics.getWidth() / 2 - bounds.width / 2; 
		y += font.getLineHeight();
		
		font.draw(batch, "Play Game: SPACE", x, y);
		
		batch.end();
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			game.setScreen(new GameScreen(game));
		} else if (Gdx.input.isKeyPressed(Keys.I)) {
			game.setScreen(new InfoScreen(game));
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}
