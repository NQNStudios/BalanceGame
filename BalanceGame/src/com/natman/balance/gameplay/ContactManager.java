package com.natman.balance.gameplay;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.natman.balance.gameplay.entities.Player;

public class ContactManager implements ContactListener {

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
