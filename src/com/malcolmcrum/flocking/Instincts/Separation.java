package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;

import static processing.core.PConstants.PI;
import static processing.core.PVector.angleBetween;
import static processing.core.PVector.dist;

public class Separation extends Instinct {
	private static final float dangerCloseDistance = 16;
	private static final float tooCloseDistance = 32;
	private static final float closeAngle = PI/4; // if angle between two close birds is outside this, we don't consider them close - they'll be gone soon

	public Separation(Boid self, Set<Boid> boids) {
		super(self, boids);
	}

	@Override
	public Desire calculateDesire() {
		float strength = 0;
		PVector awayFromOthers = new PVector();
		for (Boid other : getNeighbours()) {
			float distance = dist(self.position, other.position);

			float velocityAngles = angleBetween(self.velocity, other.velocity);
			boolean onSameCourse = velocityAngles < closeAngle;
			if (distance < dangerCloseDistance && onSameCourse) {
				strength = 0.5f;
			} else if (distance < tooCloseDistance && onSameCourse) {
				strength = 0.1f;
			}

			float angle = angleBetween(self.velocity, other.velocity);
			awayFromOthers.add(PVector.fromAngle(angle + PI));
		}

		return new Desire(strength, awayFromOthers);
	}
}
