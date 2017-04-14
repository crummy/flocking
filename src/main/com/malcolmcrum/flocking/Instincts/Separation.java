package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;

import static processing.core.PApplet.map;
import static processing.core.PConstants.PI;
import static processing.core.PVector.angleBetween;
import static processing.core.PVector.dist;

public class Separation extends Instinct {
	public Separation(Set<Boid> flock) {
		super(flock);
	}

	@Override
	public Impulse calculateImpulse(Boid boid) {
		PVector awayFromOthers = new PVector();
		for (Boid neighbour : getNeighbours(boid)) {
			float distance = dist(boid.position, neighbour.position);

			float urgency = map(distance, 0, getNeighbourRadius(), 1, 0);

			float angle = angleBetween(boid.velocity, neighbour.velocity);
			awayFromOthers.add(PVector.fromAngle(angle + PI).mult(urgency*urgency));
		}

		return new Impulse(1, awayFromOthers);
	}
}
