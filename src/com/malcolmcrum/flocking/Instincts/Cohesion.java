package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;

public class Cohesion extends Instinct {
	public Cohesion(Boid self, Set<Boid> boids) {
		super(self, boids);
	}

	@Override
	public Desire calculateDesire() {
		PVector center = new PVector();
		Set<Boid> neighbours = getNeighbours();
		if (neighbours.size() == 0) {
			return Desire.none;
		}
		for (Boid boid : neighbours) {
			center.add(boid.position);
		}
		center.div(neighbours.size());
		PVector towardsCenter = PVector.sub(center, self.position);
		float distanceToCenter = towardsCenter.mag();
		return new Desire(0.1f, towardsCenter.normalize().mult(distanceToCenter * 0.01f));
	}

}
