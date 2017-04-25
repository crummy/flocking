package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import com.malcolmcrum.flocking.Rectangle;
import processing.core.PVector;

import java.util.Set;

public class AvoidBoundaries extends Instinct {

	private final Rectangle boundary;

	public AvoidBoundaries(Set<Boid> flock, Rectangle windowBounds) {
		super(flock);
		this.boundary = windowBounds;
	}

	@Override
	public float getNeighbourRadius() {
		return 128;
	}

	@Override
	public Impulse calculateImpulse(Boid boid) {
		float margin = getNeighbourRadius();

		float x = 0;
		if (boid.position.x < boundary.left + margin) {
			x = (margin - boundary.left) - boid.position.x;
		} else if (boid.position.x > boundary.right - margin) {
			x = (boundary.right - margin) - boid.position.x;
		}

		float y = 0;
		if (boid.position.y < boundary.top + margin) {
			y = (margin - boundary.top) - boid.position.y;
		} else if (boid.position.y > boundary.bottom - margin) {
			y = (boundary.top - margin) - boid.position.y;
		}

		if (Float.isInfinite(x) || Float.isInfinite(y)) {
			throw new RuntimeException();
		}

		PVector desiredDirection = new PVector(x, y);
		return new Impulse(1f, desiredDirection);
	}
}
