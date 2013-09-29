package com.natman.balance.gameplay.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.natman.balance.gameplay.Entity;
import com.natman.balance.utils.SpriteSheet;

public class Player extends Entity {

	//region Config
	
	private static final float playerWidth = 1f;
	private static final float playerHeight = 2.5f;
	
	//endregion
	
	public Player(SpriteSheet sheet, World world) {
		super(sheet, world);
	}

	@Override
	protected void initializeSprite(SpriteSheet sheet) {
		sprite = sheet.makeSprite("Player");
	}

	@Override
	protected void initializeBody(World world, Object... args) {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.fixedRotation = false;
		bd.position.set(0, 30);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(playerWidth / 2, playerHeight / 2);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		
		body = world.createBody(bd);
		body.createFixture(fd);
		
		shape.dispose();
	}

}
