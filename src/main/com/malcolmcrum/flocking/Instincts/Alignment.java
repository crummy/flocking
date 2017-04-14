package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;

public class Alignment extends Instinct {
	public Alignment(Set<Boid> flock) {
		super(flock);
	}

	@Override
	public Desire calculateDesire(Boid boid) {
		PVector averageVelocity = new PVector();
		Set<Boid> neighbours = getNeighbours(boid);
		if (neighbours.size() == 0) {
			return Desire.none;
		}
		for (Boid neighbour : neighbours) {
			averageVelocity.add(neighbour.velocity);
		}
		averageVelocity.div(neighbours.size());
		return new Desire(1f, averageVelocity);
	}
}
