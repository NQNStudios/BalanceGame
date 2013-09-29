package com.natman.balance.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.natman.balance.BalanceGame;

public class GameOverScreen implements Screen, InputProcessor {

	private BalanceGame game;
	
	private SpriteBatch batch;
	private BitmapFont font;
	
	private float score;
	private int jumps;
	private int bonks;
	private boolean highScore;
	
	public GameOverScreen(BalanceGame game, float score, boolean highScore, int jumps, int bonks) {
		this.game = game;
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		batch = game.getSpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		this.score = score;
		this.highScore = highScore;
		this.jumps = jumps;
		this.bonks = bonks;
	}
	
	@Override
	public void render(float delta) {
		String title = "GAME OVER";
		String highScoreMsg = "HIGH SCORE! ";
		
		String pressSpaceMsg = "Return to menu: SPACE";
		
		String scoreMsg = "";
		if (highScore) scoreMsg += highScoreMsg;
		scoreMsg += "You traveled " + (int) score + " meters,";
		
		String jumpsMsg = "jumped " + jumps + " times,";
		
		String plural = (bonks != 1) ? " rocks." : " rock.";
		
		String bonksMsg = "and got hit by " + bonks + plural;
		
		batch.begin();
		
		TextBounds bounds = font.getBounds(title);
		
		float x = Gdx.graphics.getWidth() / 2 - bounds.width / 2;
		float y = 2 * Gdx.graphics.getHeight() / 3 - bounds.height / 2;
		
		font.draw(batch, title, x, y);
		
		bounds = font.getBounds(scoreMsg);
		x = Gdx.graphics.getWidth() / 2 - bounds.width / 2;
		y = Gdx.graphics.getHeight() / 2 - bounds.height / 2;
		
		font.draw(batch, scoreMsg, x, y);
		
		bounds = font.getBounds(jumpsMsg);
		x = Gdx.graphics.getWidth() / 2 - bounds.width / 2;
		y -= font.getLineHeight();
		
		font.draw(batch, jumpsMsg, x, y);
		
		bounds = font.getBounds(bonksMsg);
		x = Gdx.graphics.getWidth() / 2 - bounds.width / 2;
		y -= font.getLineHeight();
		font.draw(batch, bonksMsg, x, y);
		
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
