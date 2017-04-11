package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;

import static processing.core.PApplet.map;

public class Accelerate extends Instinct {

	private static final float maxSpeed = 200;

	public Accelerate(Boid self, Set<Boid> boids) {
		super(self, boids);
	}

	@Override
	public Desire calculateDesire() {
		float speed = self.velocity.mag();
		float urgency = map(speed, 0, maxSpeed, 1, 0);
		PVector desiredVelocity = self.velocity.normalize(null).mult(maxSpeed);
		if (urgency < 0) {
			return Desire.none;
		} else {
			return new Desire(urgency, desiredVelocity);
		}
	}
}
