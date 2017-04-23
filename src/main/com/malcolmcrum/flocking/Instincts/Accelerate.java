package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;

public class Accelerate extends Instinct {

	private static final float maxSpeed = 200;

	public Accelerate(Set<Boid> flock) {
		super(flock);
	}

	@Override
	public Impulse calculateImpulse(Boid boid) {
		PVector desiredVelocity = PVector.lerp(boid.velocity, boid.velocity.normalize(null).mult(maxSpeed), 0.5f);
		return new Impulse(1, desiredVelocity);
	}
}
