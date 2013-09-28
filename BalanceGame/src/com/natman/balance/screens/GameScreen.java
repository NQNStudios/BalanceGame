package com.natman.balance.screens;

import com.badlogic.gdx.Screen;
import com.natman.balance.BalanceGame;
import com.natman.balance.gameplay.GameWorld;

public class GameScreen implements Screen {

	private BalanceGame game;
	
	private GameWorld world;
	
	public GameScreen(BalanceGame game) {
		this.game = game;
		
		world = new GameWorld();
	}
	
	@Override
	public void render(float delta) {
		world.render(delta, game.getSpriteBatch());
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

}
