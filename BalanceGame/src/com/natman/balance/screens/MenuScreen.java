package com.natman.balance.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.natman.balance.BalanceGame;

public class MenuScreen implements Screen, InputProcessor {

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
		
		font.draw(batch, "furthest traveled: " + (int) highScore, 0, Gdx.graphics.getHeight());
		
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

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.SPACE) {
			game.setScreen(new GameScreen(game));
		} else if (keycode == Keys.I) {
			game.setScreen(new InfoScreen(game));
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
