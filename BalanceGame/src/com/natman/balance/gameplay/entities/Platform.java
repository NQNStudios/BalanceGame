package com.natman.balance.gameplay.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.natman.balance.gameplay.Entity;
import com.natman.balance.gameplay.GameWorld;
import com.natman.balance.utils.SpriteSheet;

public class Platform extends Entity {

	//region Config
	
	private static final float platformHeight = 0.5f;
	
	//endregion
	
	public Platform(SpriteSheet sheet, World world, Object... args) {
		super(sheet, world, args);
	}

	@Override
	protected void initializeBody(World world, Object... args) {
		
		float x = (Float) args[0];
		float y = (Float) args[1];
		float width = (Float) args[2];
		
		setWidth(width);
		setHeight(platformHeight);
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(x, GameWorld.floorY + GameWorld.floorHeight + y + platformHeight / 2);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, platformHeight / 2);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = 25f;
		fd.friction = 0.3f;
		
		body = world.createBody(bd);
		body.createFixture(fd);
		
		shape.dispose();
		
	}

	@Override
	protected void initializeSprite(SpriteSheet sheet) {
		sprite = sheet.makeSprite("Platform");
	}

}
