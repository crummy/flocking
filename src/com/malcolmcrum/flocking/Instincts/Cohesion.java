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
		if (neighbours.size() == 0) {
			return Desire.none();
		}
		for (Bird bird : neighbours) {
			center.add(bird.position);
		}
		center.div(neighbours.size());
		PVector towardsCenter = PVector.sub(center, self.position);
		float distanceToCenter = towardsCenter.mag();
		return new Desire(0.1f, towardsCenter.normalize().mult(distanceToCenter * 0.01f));
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