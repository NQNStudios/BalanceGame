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
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.natman.balance.BalanceGame;
import com.natman.balance.Convert;
import com.natman.balance.PhysicsWorld;
import com.natman.balance.Random;

public class GameScreen implements Screen {

	private BalanceGame game;
	
	private OrthographicCamera camera;
	
	private PhysicsWorld world;
	private Box2DDebugRenderer worldRenderer;
	private Matrix4 worldMatrix;
	
	private BitmapFont font;
	
	private Random r = new Random();
	
	//region Config
	
	private static final float floorX = 0;
	private static final float floorY = Convert.pixelsToMeters(-240);
	private static final float floorWidth = Convert.pixelsToMeters(800);
	private static final float floorHeight = Convert.pixelsToMeters(5);
	
	private static final float playerWidth = 1;
	private static final float playerHeight = 2.5f;
	
	private static final float pillarWidth = 0.5f;
	private static final float minPillarHeight = 8f;
	private static final float maxPillarHeight = 15f;
	
	private static final float platformHeight = 0.5f;
	private static final float minPlatformWidth = 10f;
	private static final float maxPlatformWidth = 20f;
	
	//endregion
	
	public GameScreen(BalanceGame game) {
		this.game = game;
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		
		world = new PhysicsWorld(new Vector2(0, -10));
		worldRenderer = new Box2DDebugRenderer();
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		
		initializeWorld();
	}
	
	private void initializeWorld() {
		createFloor();
		createPlayer();
		
		createTower(2);
	}

	private void createFloor() {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(floorX, floorY);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(floorWidth, floorHeight);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		
		Body body = world.getWorld().createBody(bd);
		body.createFixture(fd);
		
		shape.dispose();
	}
	
	private void createPlayer() {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.fixedRotation = false;
		bd.position.set(-10, 30);
		bd.bullet = true;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(playerWidth, playerHeight);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = 25;
		
		
		Body body = world.getWorld().createBody(bd);
		body.createFixture(fd);
		
		shape.dispose();
	}
	
	private void createTower(float x) {
		float height = r.nextFloat(minPillarHeight, maxPillarHeight);
		
		createPillar(x, height);
		
		createPlatform(x, height + height);
	}
	
	private void createPlatform(float x, float y) {
		
		float width = r.nextFloat(minPlatformWidth, maxPlatformWidth);
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(x, floorY + floorHeight + y);
		bd.fixedRotation = false;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, platformHeight);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.friction = 0.5f;
		fd.density = 25;
		
		Body body = world.getWorld().createBody(bd);
		body.createFixture(fd);
		
		shape.dispose();
		
	}

	private void createPillar(float x, float height) {
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(x, floorY + floorHeight + height);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(pillarWidth, height);
		
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
		font.draw(batch, "" + Gdx.graphics.getFramesPerSecond(), camera.position.x, camera.position.y);
		
		batch.end();
		
		worldMatrix = new Matrix4(camera.combined);
		worldMatrix.scale(Convert.getPixelMeterRatio(), Convert.getPixelMeterRatio(), Convert.getPixelMeterRatio());
		
		worldRenderer.render(world.getWorld(), worldMatrix);
		
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
