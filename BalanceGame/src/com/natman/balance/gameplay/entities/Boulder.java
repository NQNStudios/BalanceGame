package com.natman.balance.gameplay.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.natman.balance.gameplay.Entity;
import com.natman.balance.utils.Convert;
import com.natman.balance.utils.SpriteSheet;

public class Boulder extends Entity {

	//region config
	
	private static final float y = Convert.pixelsToMeters(300);
	private static final float density = 15f;
	
	//endregion
	
	public Boulder(SpriteSheet sheet, World world, Object... args) {
		super(sheet, world, args);
	}

	@Override
	protected void initializeBody(World world, Object... args) {
		
		float x = (Float) args[0];
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(x, y);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(Convert.pixelsToMeters(12));
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = density;
		
		body = world.createBody(bd);
		body.createFixture(fd);
		
		shape.dispose();
		
	}

	@Override
	protected void initializeSprite(SpriteSheet sheet) {
		sprite = sheet.makeSprite("Rock");
	}

}
