package com.natman.balance.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.natman.balance.BalanceGame;
import com.natman.balance.gameplay.GameWorld;

public class GameScreen implements Screen, InputProcessor {

	private BalanceGame game;
	
	private GameWorld world;
	
	public GameScreen(BalanceGame game) {
		this.game = game;
		
		world = new GameWorld();
	}
	
	@Override
	public void render(float delta) {
		world.render(delta, game.getSpriteBatch());
		
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
