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
	
	private Body floor;
	private Player player;
	
	private boolean debugRender = true;
	
	private float furthestX = 0;
	
	private float lastX = 0;
	private float lastWidth = maxPlatformWidth;
	private float lastHeight = firstPillarHeight;
	
	//endregion
	
	//region Config
	
	public static final float floorX = 0;
	public static final float floorY = Convert.pixelsToMeters(-550);
	public static final float floorWidth = 8000;
	public static final float floorHeight = Convert.pixelsToMeters(5);
	
	private static final float firstPillarHeight = 27f;
	private static final float minPillarHeight = 23f;
	private static final float maxPillarHeight = 34f;
	private static final float upMax = 4f;
	
	private static final float minPlatformWidth = 10f;
	private static final float maxPlatformWidth = 20f;
	
	private static final float maxDistance = 6f;
	private static final float creationDistance = 20f;
	
	private static final float removalDistance = Convert.pixelsToMeters(500);
	
	//endregion
	
	//region Constructor
	
	public GameWorld() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		
		world = new PhysicsWorld(new Vector2(0, -10));
		world.getWorld().setContactListener(new ContactManager());
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
		
		createStartingTower();
		
		for (int i = 17; i < 30; i += lastWidth) {
			createTower(i);
		}
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
		
		floor = body;
		
		shape.dispose();
	}
	
	private void createStartingTower() {
		float height = firstPillarHeight;
		
		createPillar(0, height);
		createPlatform(0, height, maxPlatformWidth);
	}
	
	private void createTower(float x) {
		float height = r.nextFloat(minPillarHeight, Math.min(maxPillarHeight, lastHeight + upMax));
		
		createPillar(x, height);
		
		createPlatform(x, height, r.nextFloat(minPlatformWidth, maxPlatformWidth));
		
		lastHeight = height;
		lastX = x;
	}
	
	private void createPlatform(float x, float y, float width) {
		new Platform(spriteSheet, world.getWorld(), x, y, width);
		lastWidth = width;
	}

	private void createPillar(float x, float height) {
		new Pillar(spriteSheet, world.getWorld(), x, height);
	}
	
	//endregion
	
	//region Game Loop
	
	public void render(float delta, SpriteBatch batch) {
		camera.position.set(Convert.metersToPixels(new Vector3(player.body.getPosition().x, 0, 0)));
		
		camera.update();
		
		removeOffscreenEntities();
		
		if (player.body.getPosition().x > furthestX) {
			furthestX = player.body.getPosition().x;
			
			if (furthestX >= lastX - creationDistance) {
				createTower(lastX + r.nextFloat(lastWidth, lastWidth + maxDistance));
			}
		}
		
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
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			player.moveRight();
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			player.moveLeft();
		}
		
		if (Gdx.input.isKeyPressed(Keys.SPACE) && player.canJump) {
			player.jump();
		}
		
		floor.getPosition().set(new Vector2(player.body.getPosition().x, floorY));
		
		world.process(delta);
	}

	private void removeOffscreenEntities() {		
		Vector2 cameraPos = Convert.pixelsToMeters(new Vector2(camera.position.x, camera.position.y));
		
		Iterator<Body> it = world.getWorld().getBodies();
		while (it.hasNext()) {
			Body body = it.next();
			
			if (body == null || body.getUserData() == null) continue;
			
			if (body.getUserData() instanceof Pillar || body.getUserData() instanceof Platform) {
				if (body.getPosition().x < Convert.pixelsToMeters(camera.position.x - camera.viewportWidth / 2) - maxPlatformWidth / 2) {
					Entity e = (Entity) body.getUserData();
					
					delete(e);
				}
				
				continue;
			}
			
			if (body.getPosition().dst(cameraPos) > removalDistance) {
				Entity e = (Entity) body.getUserData();
				
				delete(e);
			}
			
		}
	}

	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		camera.position.set(0, 0, 0);
	}
	
	public void dispose() {
		world.dispose();
	}
	
	public void delete(Entity e) {
		Body body = e.body;
		world.getWorld().destroyBody(body);
	}
	
	//endregion
	
}