package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import com.malcolmcrum.flocking.Rectangle;
import processing.core.PVector;

import java.util.Set;

import static processing.core.PConstants.PI;

public class AvoidBoundaries extends Instinct {

	private static final float turningRate = PI/16;

	private final Boid self;
	private final Rectangle boundary;

	public AvoidBoundaries(Boid self, Set<Boid> boids, Rectangle windowBounds) {
		super(self, boids);
		this.self = self;
		this.boundary = windowBounds;
	}

	@Override
	public float getNeighbourRadius() {
		return 256;
	}

	@Override
	public Desire calculateDesire() {
		float margin = getNeighbourRadius();
		PVector desiredDirection = new PVector();
		float xStrength = 0;
		if (self.position.x < boundary.left + margin) {
			xStrength = (self.position.x - boundary.left)/margin;
			desiredDirection.x = 1;
		} else if (self.position.x > boundary.right - margin) {
			xStrength = (boundary.right - self.position.x)/margin;
			desiredDirection.x = -1;
		}
		float yStrength = 0;
		if (self.position.y < boundary.top + margin) {
			yStrength = (self.position.y - boundary.top)/margin;
			desiredDirection.y = 1;
		} else if (self.position.y > boundary.bottom - margin) {
			yStrength = (boundary.bottom - self.position.y)/margin;
			desiredDirection.y = -1;
		}

		float strength = xStrength > yStrength ? xStrength : yStrength;
		if (strength > 1) { // out of bounds
			strength = 1;
		} else if (strength < 0) {
			strength = 0;
		}
		float speed = self.velocity.mag();
		PVector desiredVelocity = PVector.lerp(self.velocity.copy().normalize(), desiredDirection, turningRate).mult(speed);
		return new Desire(strength, desiredVelocity);
	}
}
