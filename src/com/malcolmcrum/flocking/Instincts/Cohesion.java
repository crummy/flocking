package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;
import processing.core.PVector;

import java.util.Set;

public class Cohesion extends Instinct {
	public Cohesion(Bird self, Set<Bird> birds, DesireMultiplier multiplier) {
		super(self, birds, multiplier);
	}

	@Override
	public Desire calculateDesire() {
		PVector center = new PVector();
		Set<Bird> neighbours = getNeighbours();
		if (neighbours.size() == 0) {
			return Desire.none;
		}
		for (Bird bird : neighbours) {
			center.add(bird.position);
		}
		center.div(neighbours.size());
		PVector towardsCenter = PVector.sub(center, self.position);
		float distanceToCenter = towardsCenter.mag();
		return new Desire(0.1f, towardsCenter.normalize().mult(distanceToCenter * 0.01f));
	}

}
