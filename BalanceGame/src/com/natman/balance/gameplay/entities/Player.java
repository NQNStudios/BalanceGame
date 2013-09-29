package com.natman.balance.gameplay.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.natman.balance.gameplay.Entity;
import com.natman.balance.utils.Convert;
import com.natman.balance.utils.SpriteSheet;

public class Player extends Entity {

	//region Config
	
	private static final float playerWidth = 1f;
	private static final float playerHeight = 2.5f;
	
	private static final float density = 12f;
	
	private static final float normalMoveSpeed = 50f;
	private static final float normalJumpSpeed = 1450f;
	
	private static final float powerUpMoveSpeed = 100f;
	private static final float powerUpJumpSpeed = 2250f;
	
	private static final float powerUpDuration = 15f;
	
	//endregion
	
	private float moveSpeed = normalMoveSpeed;
	private float jumpSpeed = normalJumpSpeed;
	
	private float powerUpTime;
	
	public boolean canJump = false;
	
	public Player(SpriteSheet sheet, World world) {
		super(sheet, world);
	}

	public void processPowerups(float delta) {
		if (moveSpeed == powerUpMoveSpeed) {
			powerUpTime -= delta;
			
			if (powerUpTime <= 0f) {
				moveSpeed = normalMoveSpeed;
			}
		}
		
		if (jumpSpeed == powerUpJumpSpeed) {
			powerUpTime -= delta;
			
			if (powerUpTime <= 0f) {
				jumpSpeed = normalJumpSpeed;
			}
		}
	}
	
	public void givePowerup(boolean jump) {
		powerUpTime = powerUpDuration;
		
		if (jump) {
			moveSpeed = normalMoveSpeed;
			jumpSpeed = powerUpJumpSpeed;
		} else {
			jumpSpeed = normalJumpSpeed;
			moveSpeed = powerUpMoveSpeed;
		}
	}
	
	@Override
	protected void initializeSprite(SpriteSheet sheet) {
		sprite = sheet.makeSprite("Player");
	}

	@Override
	protected void initializeBody(World world, Object... args) {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.fixedRotation = true;
		bd.position.set(0, 0);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(playerWidth / 2, playerHeight / 2);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = density;
		fd.friction = 0.3f;
		
		float sensorHX = Convert.pixelsToMeters(6);
		float sensorHY = Convert.pixelsToMeters(1);
		PolygonShape sensorShape = new PolygonShape();
		
		Vector2[] vertices = new Vector2[4];
		vertices[0] = new Vector2(sensorHX, -playerHeight / 2 - sensorHY); //bottom right corner
		vertices[1] = new Vector2(sensorHX, -playerHeight / 2 + sensorHY); //Top right corner
		vertices[2] = new Vector2(-sensorHX, -playerHeight / 2 + sensorHY); //Top left corner
		vertices[3] = new Vector2(-sensorHX, -playerHeight / 2 - sensorHY); //bottom left corner
		
		sensorShape.set(vertices);
		
		FixtureDef fd2 = new FixtureDef();
		fd2.isSensor = true;
		fd2.shape = sensorShape;
		
		body = world.createBody(bd);
		body.createFixture(fd);
		body.createFixture(fd2);
		
		shape.dispose();
		sensorShape.dispose();
	}
	
	public void moveLeft() {
		body.applyForceToCenter(new Vector2(-moveSpeed * density, 0), true);
	}
	
	public void moveRight() {
		body.applyForceToCenter(new Vector2(moveSpeed * density, 0), true);
	}

	public void jump() {
		body.applyForceToCenter(new Vector2(0, jumpSpeed * density), true);
		canJump = false;
	}
	
}
