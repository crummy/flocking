package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;

public class Cohesion extends Instinct {
	public Cohesion(Set<Boid> flock) {
		super(flock);
	}

	@Override
	public Impulse calculateImpulse(Boid boid) {
		PVector center = new PVector();
		Set<Boid> neighbours = getNeighbours(boid);
		if (neighbours.size() == 0) {
			return Impulse.none;
		}
		for (Boid neighbour : neighbours) {
			center.add(neighbour.position);
		}
		center.div(neighbours.size());
		PVector towardsNeighbours = PVector.sub(center, boid.position);
		float distanceToCenterOfNeighbours = towardsNeighbours.mag();
		return new Impulse(0.1f, towardsNeighbours.normalize().mult(distanceToCenterOfNeighbours * 0.01f));
	}

}
