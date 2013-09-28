package com.natman.balance.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.natman.balance.BalanceGame;
import com.natman.balance.PhysicsWorld;

public class GameScreen implements Screen {

	private BalanceGame game;
	
	private OrthographicCamera camera;
	
	private PhysicsWorld world;
	private Box2DDebugRenderer worldRenderer;
	
	public GameScreen(BalanceGame game) {
		this.game = game;
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		
		world = new PhysicsWorld(new Vector2(0, -10));
		worldRenderer = new Box2DDebugRenderer();
	}
	
	@Override
	public void render(float delta) {
		
		camera.update();
		
		SpriteBatch batch = game.getSpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		//Draw the game objects here
		
		batch.end();
		
		worldRenderer.render(world.getWorld(), camera.combined);
		
		world.process(delta);
		
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
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
