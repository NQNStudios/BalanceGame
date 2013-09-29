package com.natman.balance.gameplay.entities;

import java.util.Random;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.natman.balance.gameplay.Entity;
import com.natman.balance.utils.Convert;
import com.natman.balance.utils.SpriteSheet;

public class PowerUp extends Entity {

	private Random r = new Random();
	public boolean jump;
	
	public PowerUp(SpriteSheet sheet, World world, Object... args) {
		super(sheet, world, args);
		
		jump = r.nextBoolean(); 
	}

	@Override
	protected void initializeBody(World world, Object... args) {
		float x = (Float) args[0];
		float y = (Float) args[1];
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(x, y);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(Convert.pixelsToMeters(9));
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.isSensor = true;
		
		body = world.createBody(bd);
		body.createFixture(fd);
		
		shape.dispose();
	}

	@Override
	protected void initializeSprite(SpriteSheet sheet) {
		if (jump) sprite = sheet.makeSprite("JumpPowerup");
		else sprite = sheet.makeSprite("SpeedPowerup");
	}
	
}
