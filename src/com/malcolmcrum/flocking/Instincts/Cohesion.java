package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;
import processing.core.PVector;

import java.util.Set;

public class Cohesion extends Instinct {
	public static boolean isEnabled = true;

	public Cohesion(Bird self, Set<Bird> birds) {
		super(self, birds);
	}

	@Override
	public Desire get() {
		PVector center = new PVector();
		Set<Bird> neighbours = getNeighbours();
		for (Bird bird : neighbours) {
			center.add(bird.position);
		}
		center.div(neighbours.size());
		return new Desire(0.01f, PVector.sub(center, self.position));
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public String toString() {
		return "MoveTowardsNearby";
	}
}
