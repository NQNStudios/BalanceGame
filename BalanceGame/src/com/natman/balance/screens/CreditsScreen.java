package com.natman.balance.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.natman.balance.BalanceGame;

public class CreditsScreen implements Screen, InputProcessor {

	private BalanceGame game;
	
	private SpriteBatch batch;
	private BitmapFont font;
	
	public CreditsScreen(BalanceGame game) {
		this.game = game;
		
		batch = game.getSpriteBatch();
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
	}
	
	@Override
	public void render(float delta) {
		String title = "CREDITS";
		String msg1 = "Game by Nathaniel Nelson";
		String msg2 = "Made in 48 hours for the Fight Magic Run competition";
		String msg3 = "Theme: Balance";
		String msg4 = "Sounds made in sfxr, Music made in Bosca Ceoil";
		
		String pressSpaceMsg = "Return to menu: SPACE";
		
		batch.begin();
		
		TextBounds bounds = font.getBounds(title);
		
		float x = Gdx.graphics.getWidth() / 2 - bounds.width / 2;
		float y = 2 * Gdx.graphics.getHeight() / 3 - bounds.height / 2;
		
		font.draw(batch, title, x, y);
		
		bounds = font.getBounds(msg1);
		x = Gdx.graphics.getWidth() / 2 - bounds.width / 2;
		y = Gdx.graphics.getHeight() / 2 - bounds.height / 2;
		
		font.draw(batch, msg1, x, y);
		
		bounds = font.getBounds(msg2);
		x = Gdx.graphics.getWidth() / 2 - bounds.width / 2;
		y -= font.getLineHeight();
		
		font.draw(batch, msg2, x, y);
		
		bounds = font.getBounds(msg3);
		x = Gdx.graphics.getWidth() / 2 - bounds.width / 2;
		y -= font.getLineHeight();
		font.draw(batch, msg3, x, y);
		
		bounds = font.getBounds(msg4);
		x = Gdx.graphics.getWidth() / 2 - bounds.width / 2;
		y -= font.getLineHeight();
		font.draw(batch, msg4, x, y);
		
		bounds = font.getBounds(pressSpaceMsg);
		x = Gdx.graphics.getWidth() / 2 - bounds.width / 2;
		y -= 3 * font.getLineHeight();
		font.draw(batch, pressSpaceMsg, x, y);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.SPACE) {
			game.setScreen(new MenuScreen(game));
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
