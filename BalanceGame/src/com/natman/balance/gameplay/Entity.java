package com.natman.balance.gameplay;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.natman.balance.utils.Convert;
import com.natman.balance.utils.SpriteSheet;

public abstract class Entity {

	private float width = -1;
	private float height = -1;
	
	protected Sprite sprite;
	protected Body body;
	
	public Entity(SpriteSheet sheet, World world, Object... args) {
		initializeSprite(sheet);
		initializeBody(world, args);
		
		body.setUserData(this);
	}
	
	protected abstract void initializeBody(World world, Object... args);
	protected abstract void initializeSprite(SpriteSheet sheet);
	
	public void draw(SpriteBatch batch) {
		float x = Convert.metersToPixels(body.getPosition().x) - sprite.getWidth() / 2;
		float y = Convert.metersToPixels(body.getPosition().y) - sprite.getHeight() / 2;
		
		sprite.setPosition(x, y);
		
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));
		
		if (width > 0 && height > 0) {
			sprite.setBounds(x, y, Convert.metersToPixels(width), Convert.metersToPixels(height));
		}
		
		sprite.draw(batch);
	}
	
	public void setWidth(float width) { this.width = width; }
	public void setHeight(float height) { this.height = height; }
	
}
