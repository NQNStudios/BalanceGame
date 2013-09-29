package com.natman.balance.gameplay;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.natman.balance.gameplay.entities.Pillar;
import com.natman.balance.gameplay.entities.Platform;
import com.natman.balance.gameplay.entities.Player;
import com.natman.balance.utils.Convert;
import com.natman.balance.utils.Random;
import com.natman.balance.utils.SpriteSheet;

public class GameWorld {
	
	//region Fields
	
	private PhysicsWorld world;
	
	private OrthographicCamera camera;
	private Box2DDebugRenderer worldRenderer;
	private Matrix4 worldMatrix;
	
	private BitmapFont font;
	private Random r = new Random();
	
	private SpriteSheet spriteSheet;
	
	private Player player;
	
	private boolean debugRender = true;
	
	//endregion
	
	//region Config
	
	public static final float floorX = 0;
	public static final float floorY = Convert.pixelsToMeters(-480);
	public static final float floorWidth = Convert.pixelsToMeters(800);
	public static final float floorHeight = Convert.pixelsToMeters(5);
	
	private static final float minPillarHeight = 15f;
	private static final float maxPillarHeight = 30f;
	
	private static final float minPlatformWidth = 10f;
	private static final float maxPlatformWidth = 20f;
	
	//endregion
	
	//region Constructor
	
	public GameWorld() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		
		world = new PhysicsWorld(new Vector2(0, -10));
		worldRenderer = new Box2DDebugRenderer();
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		spriteSheet = new SpriteSheet(new Texture(Gdx.files.internal("data/sprites.png")));
		
		initializeSpriteSheet();
		initializeWorld();
	}
	
	//endregion
	
	//region Entities
	
	private void initializeSpriteSheet() {
		spriteSheet.addRegion("Player", new Rectangle(0, 0, 16, 40));
		spriteSheet.addRegion("Platform", new Rectangle(16, 0, 16, 40));
	}

	private void initializeWorld() {
		createFloor();

		player = new Player(spriteSheet, world.getWorld());
		
		createTower(2);
	}

	private void createFloor() {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(floorX, floorY);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(floorWidth / 2, floorHeight / 2);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		
		Body body = world.getWorld().createBody(bd);
		body.createFixture(fd);
		
		shape.dispose();
	}
	
	private void createTower(float x) {
		float height = r.nextFloat(minPillarHeight, maxPillarHeight);
		
		createPillar(x, height);
		
		createPlatform(x, height, r.nextFloat(minPlatformWidth, maxPlatformWidth));
	}
	
	private void createPlatform(float x, float y, float width) {
		new Platform(spriteSheet, world.getWorld(), x, y, width);
	}

	private void createPillar(float x, float height) {
		new Pillar(spriteSheet, world.getWorld(), x, height);
	}
	
	//endregion
	
	//region Game Loop
	
	public void render(float delta, SpriteBatch batch) {
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
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		//font.draw(batch, "" + Gdx.graphics.getFramesPerSecond(), camera.position.x, camera.position.y); //Performance info
		Iterator<Body> it = world.getWorld().getBodies();
		while (it.hasNext()) {
			Body body = it.next();
			
			Entity e = (Entity) body.getUserData();
			
			if (e != null) e.draw(batch);
		}
		
		batch.end();
		
		if (Gdx.input.isKeyPressed(Keys.F1)) {
			debugRender = !debugRender;
		}
		
		if (debugRender) {
			worldMatrix = new Matrix4(camera.combined);
			worldMatrix.scale(Convert.getPixelMeterRatio(), Convert.getPixelMeterRatio(), Convert.getPixelMeterRatio());
			
			worldRenderer.render(world.getWorld(), worldMatrix);
		}
		
		world.process(delta);
	}
	
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		camera.position.set(0, 0, 0);
	}
	
	public void dispose() {
		world.dispose();
	}
	
	//endregion
	
}