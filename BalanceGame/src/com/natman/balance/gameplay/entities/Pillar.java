package com.natman.balance.gameplay.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.natman.balance.gameplay.Entity;
import com.natman.balance.gameplay.GameWorld;
import com.natman.balance.utils.SpriteSheet;

public class Pillar extends Entity {

	//region Config
	
	private static final float pillarWidth = 0.5f;
	
	//endregion
	
	public Pillar(SpriteSheet sheet, World world, Object... args) {
		super(sheet, world, args);
	}

	@Override
	protected void initializeBody(World world, Object... args) {
		
		float x = (Float) args[0];
		float height = (Float) args[1];
		
		setWidth(pillarWidth);
		setHeight(height);
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(x, GameWorld.floorY + GameWorld.floorHeight + height / 2);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(pillarWidth / 2, height / 2);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = 25;
		fd.friction = 1;
		
		body = world.createBody(bd);
		body.createFixture(fd);
		
		shape.dispose();
		
	}

	@Override
	protected void initializeSprite(SpriteSheet sheet) {
		sprite = sheet.makeSprite("Platform");
	}

}
