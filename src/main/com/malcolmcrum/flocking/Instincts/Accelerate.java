package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;

import static processing.core.PApplet.map;

public class Accelerate extends Instinct {

	private static final float maxSpeed = 200;

	public Accelerate(Set<Boid> flock) {
		super(flock);
	}

	@Override
	public Impulse calculateImpulse(Boid boid) {
		float speed = boid.velocity.mag();
		float urgency = map(speed, 0, maxSpeed, 1, 0);
		PVector desiredVelocity = boid.velocity.normalize(null).mult(maxSpeed);
		if (urgency < 0) {
			return Impulse.none;
		} else {
			return new Impulse(urgency, desiredVelocity);
		}
	}
}
