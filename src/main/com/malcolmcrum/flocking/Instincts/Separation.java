package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;

import static processing.core.PApplet.map;
import static processing.core.PConstants.PI;
import static processing.core.PVector.angleBetween;
import static processing.core.PVector.dist;

public class Separation extends Instinct {
	public Separation(Boid self, Set<Boid> boids) {
		super(self, boids);
	}

	@Override
	public Desire calculateDesire() {
		PVector awayFromOthers = new PVector();
		for (Boid other : getNeighbours()) {
			float distance = dist(self.position, other.position);

			float urgency = map(distance, 0, getNeighbourRadius(), 1, 0);

			float angle = angleBetween(self.velocity, other.velocity);
			awayFromOthers.add(PVector.fromAngle(angle + PI).mult(urgency*urgency));
		}

		return new Desire(1, awayFromOthers);
	}
}
