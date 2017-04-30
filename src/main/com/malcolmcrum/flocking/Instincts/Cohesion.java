package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;

public class Cohesion extends Instinct {
	public Cohesion(Set<Boid> flock) {
		super(flock);
	}

	@Override
	public PVector calculateImpulse(Boid boid) {
		PVector center = new PVector();
		Set<Boid> neighbours = getNeighbours(boid);
		if (neighbours.size() == 0) {
			return new PVector(0, 0);
		}
		for (Boid neighbour : neighbours) {
			center.add(neighbour.position);
		}
		center.div(neighbours.size());
		PVector towardsNeighbours = PVector.sub(center, boid.position);
		float distanceToCenterOfNeighbours = towardsNeighbours.mag();
		return towardsNeighbours.normalize().mult(distanceToCenterOfNeighbours * 0.01f);
	}

}
