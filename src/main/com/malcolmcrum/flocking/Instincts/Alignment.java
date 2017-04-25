package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;

public class Alignment extends Instinct {
	public Alignment(Set<Boid> flock) {
		super(flock);
	}

	private static final float factor = 0.25f;

	@Override
	public float getNeighbourRadius() {
		return 64;
	}

	@Override
	public Impulse calculateImpulse(Boid boid) {
		PVector averageVelocity = new PVector();
		Set<Boid> neighbours = getNeighbours(boid);
		if (neighbours.size() == 0) {
			return Impulse.none;
		}
		for (Boid neighbour : neighbours) {
			averageVelocity.add(PVector.mult(neighbour.velocity, factor));
		}
		averageVelocity.div(neighbours.size());
		return new Impulse(1f, averageVelocity);
	}
}
