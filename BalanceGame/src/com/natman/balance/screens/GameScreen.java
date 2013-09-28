package com.natman.balance.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.natman.balance.BalanceGame;
import com.natman.balance.Convert;
import com.natman.balance.PhysicsWorld;

public class GameScreen implements Screen {

	private BalanceGame game;
	
	private OrthographicCamera camera;
	
	private PhysicsWorld world;
	private Box2DDebugRenderer worldRenderer;
	
	private BitmapFont font;
	
	public GameScreen(BalanceGame game) {
		this.game = game;
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		
		Convert.init(16);
		
		world = new PhysicsWorld(new Vector2(0, -10));
		worldRenderer = new Box2DDebugRenderer();
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		
		initializeWorld();
	}
	
	private void initializeWorld() {
		createFloor();
		createPlayer();
	}

	private void createFloor() {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(Convert.pixelsToMeters(-400), Convert.pixelsToMeters(-220));
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Convert.pixelsToMeters(800), 1);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		
		Body body = world.getWorld().createBody(bd);
		body.createFixture(fd);
		
		shape.dispose();
	}
	
	private void createPlayer() {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.fixedRotation = true;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1, 3f);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		
		Body body = world.getWorld().createBody(bd);
		body.createFixture(fd);
		
		shape.dispose();
	}

	@Override
	public void render(float delta) {
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			camera.position.add(new Vector3(-10, 0, 0));
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			camera.position.add(new Vector3(10, 0, 0));
		}
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			camera.position.add(new Vector3(0, 10, 0));
		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			camera.position.add(new Vector3(0, -10, 0));
		}
		
		camera.update();
		
		SpriteBatch batch = game.getSpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		//Draw the game objects here
//		font.draw(batch, "Camera Pos: " + camera.position.x + ", " + camera.position.y, 0, 0);
//		font.draw(batch, "Camera Pos: " + camera.position.x + ", " + camera.position.y, camera.position.x, camera.position.y);
		
		batch.end();
		
		Matrix4 debugMatrix = new Matrix4(camera.combined);
		debugMatrix.scale(Convert.getPixelMeterRatio(), Convert.getPixelMeterRatio(), Convert.getPixelMeterRatio());
		
		worldRenderer.render(world.getWorld(), debugMatrix);
		
		world.process(delta);
		
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		camera.position.set(0, 0, 0);
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
