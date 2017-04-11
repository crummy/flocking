package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;

public class Alignment extends Instinct {
	public Alignment(Boid self, Set<Boid> boids) {
		super(self, boids);
	}

	@Override
	public Desire calculateDesire() {
		PVector averageVelocity = new PVector();
		Set<Boid> neighbours = getNeighbours();
		if (neighbours.size() == 0) {
			return Desire.none;
		}
		for (Boid boid : neighbours) {
			averageVelocity.add(boid.velocity);
		}
		averageVelocity.div(neighbours.size());
		return new Desire(0.1f, averageVelocity);
	}
}
