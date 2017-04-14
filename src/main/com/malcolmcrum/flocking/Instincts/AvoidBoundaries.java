package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import com.malcolmcrum.flocking.Rectangle;
import processing.core.PVector;

import java.util.Set;

import static processing.core.PConstants.PI;

public class AvoidBoundaries extends Instinct {

	private static final float turningRate = PI/16;

	private final Rectangle boundary;

	public AvoidBoundaries(Set<Boid> flock, Rectangle windowBounds) {
		super(flock);
		this.boundary = windowBounds;
	}

	@Override
	public float getNeighbourRadius() {
		return 256;
	}

	@Override
	public Desire calculateDesire(Boid boid) {
		float margin = getNeighbourRadius();
		PVector desiredDirection = new PVector();
		float xStrength = 0;
		if (boid.position.x < boundary.left + margin) {
			xStrength = (boid.position.x - boundary.left)/margin;
			desiredDirection.x = 1;
		} else if (boid.position.x > boundary.right - margin) {
			xStrength = (boundary.right - boid.position.x)/margin;
			desiredDirection.x = -1;
		}
		float yStrength = 0;
		if (boid.position.y < boundary.top + margin) {
			yStrength = (boid.position.y - boundary.top)/margin;
			desiredDirection.y = 1;
		} else if (boid.position.y > boundary.bottom - margin) {
			yStrength = (boundary.bottom - boid.position.y)/margin;
			desiredDirection.y = -1;
		}

		float strength = xStrength > yStrength ? xStrength : yStrength;
		if (strength > 1) { // out of bounds
			strength = 1;
		} else if (strength < 0) {
			strength = 0;
		}
		float speed = boid.velocity.mag();
		PVector desiredVelocity = PVector.lerp(boid.velocity.copy().normalize(), desiredDirection, turningRate).mult(speed);
		return new Desire(strength, desiredVelocity);
	}
}
