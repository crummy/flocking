package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.Set;

public class Accelerate extends Instinct {

	static final float maxSpeed = 200;
	static final float acceleration = 0.85f;

	public Accelerate(Set<Boid> flock) {
		super(flock);
	}

	@Override
	public Impulse calculateImpulse(Boid boid) {
		if (boid.velocity.x == 0 && boid.velocity.y == 0) {
			throw new RuntimeException();
		}
		float speed = boid.velocity.mag();
		float desiredSpeed = -maxSpeed * PApplet.pow(acceleration, speed) + maxSpeed;
		PVector desiredVelocity = PVector.mult(boid.velocity.normalize(null), desiredSpeed);
		if (Float.isInfinite(desiredVelocity.x)) {
			throw new RuntimeException();
		}
		return new Impulse(1f, desiredVelocity);
	}
}
