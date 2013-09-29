package com.natman.balance.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.natman.balance.BalanceGame;
import com.natman.balance.gameplay.GameWorld;
import com.natman.balance.utils.SoundManager;

public class GameScreen implements Screen, InputProcessor {

	private BalanceGame game;
	
	private GameWorld world;
	
	private SpriteBatch batch;
	private BitmapFont font;
	
	public GameScreen(BalanceGame game) {
		this.game = game;
		
		batch = game.getSpriteBatch();
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		world = new GameWorld();
	}
	
	@Override
	public void render(float delta) {
		world.render(delta, game.getSpriteBatch());
		
		TextBounds bounds = font.getBounds("Toggle Sound: M");
		
		OrthographicCamera camera = world.getCamera();
		float x = camera.position.x + camera.viewportWidth / 2 - bounds.width - 5;
		float y = camera.position.y - camera.viewportHeight / 2 + bounds.height + 5;
		
		batch.begin();
		
		font.draw(batch, "Toggle Sound: M", x, y);
		
		batch.end();
		
		if (world.gameOver) {
			game.setScreen(new GameOverScreen(game, world.furthestX, world.furthestX == world.highScore, world.jumps, world.bonks));
		}
	}

	@Override
	public void resize(int width, int height) {
		world.resize(width, height);
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
		world.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		world.keyDown(keycode);
		
		if (keycode == Keys.M) {
			//mute the SoundManager
			if (SoundManager.getSoundVolume() == 1) {
				SoundManager.setSoundVolume(0);
			} else {
				SoundManager.setSoundVolume(1);
			}
			
			if (SoundManager.isMusicPlaying()) {
				SoundManager.pauseMusic();
			} else {
				SoundManager.resumeMusic();
			}
			
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		world.keyUp(keycode);
		
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
