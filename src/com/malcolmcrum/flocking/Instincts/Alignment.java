package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;
import processing.core.PVector;

import java.util.Set;

public class Alignment extends Instinct {
	public Alignment(Bird self, Set<Bird> birds, DesireMultiplier multiplier) {
		super(self, birds, multiplier);
	}

	@Override
	public Desire calculateDesire() {
		PVector averageVelocity = new PVector();
		Set<Bird> neighbours = getNeighbours();
		if (neighbours.size() == 0) {
			return Desire.none;
		}
		for (Bird bird : neighbours) {
			averageVelocity.add(bird.velocity);
		}
		averageVelocity.div(neighbours.size());
		return new Desire(0.1f, averageVelocity);
	}
}
