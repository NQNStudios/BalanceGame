package com.natman.balance.gameplay;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.natman.balance.gameplay.entities.Boulder;
import com.natman.balance.gameplay.entities.Player;

public class ContactManager implements ContactListener {

	private Array<Boulder> rocksHit = new Array<Boulder>();
	
	private GameWorld world;
	
	public ContactManager(GameWorld world) {
		this.world = world;
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		Fixture sensor = null;
		if (fixtureA.isSensor()) {
			sensor = fixtureA;
		} else if (fixtureB.isSensor()) {
			sensor = fixtureB;
		}
		
		if (sensor != null) {
			//it's the player's sensor
			Player player = (Player) sensor.getBody().getUserData();
			player.canJump = true;
		}
		
		Entity e1 = (Entity) fixtureA.getBody().getUserData();
		Entity e2 = (Entity) fixtureB.getBody().getUserData();
		
		if (e1 == null || e2 == null) return;
		
		//check various collision types
		if (e1 instanceof Player && e2 instanceof Boulder || e2 instanceof Player && e1 instanceof Boulder) {
			//It's a player-boulder collision
			Boulder boulder = null;
			if (e1 instanceof Boulder) boulder = (Boulder) e1;
			else if (e2 instanceof Boulder) boulder = (Boulder) e2;
			
			if (!rocksHit.contains(boulder, true)) {
				rocksHit.add(boulder);
				world.bonks++;
			}
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		Fixture sensor = null;
		if (fixtureA.isSensor()) {
			sensor = fixtureA;
		} else if (fixtureB.isSensor()) {
			sensor = fixtureB;
		}
		
		if (sensor != null) {
			//it's the player's sensor
			Player player = (Player) sensor.getBody().getUserData();
			player.canJump = false;
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}
