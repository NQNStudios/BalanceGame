package com.natman.balance.gameplay;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.natman.balance.utils.Convert;
import com.natman.balance.utils.SpriteSheet;

public abstract class Entity {

	protected Sprite sprite;
	protected Body body;
	
	public Entity(SpriteSheet sheet, Body body) {
		this.body = body;
		body.setUserData(this);
		
		initializeSprite(sheet);
	}
	
	protected abstract void initializeSprite(SpriteSheet sheet);
	
	public void draw(SpriteBatch batch) {
		float x = Convert.metersToPixels(body.getPosition().x) - sprite.getWidth() / 2;
		float y = Convert.metersToPixels(body.getPosition().y) - sprite.getHeight() / 2;
		
		sprite.setPosition(x, y);
		
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));
		
		sprite.draw(batch);
	}
	
}
