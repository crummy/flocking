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
	public void update() {
		PVector center = new PVector();
		Set<Bird> neighbours = getNeighbours();
		if (neighbours.size() == 0) {
			desire = Desire.none();
			return;
		}
		for (Bird bird : neighbours) {
			center.add(bird.position);
		}
		center.div(neighbours.size());
		PVector towardsCenter = PVector.sub(center, self.position);
		float distanceToCenter = towardsCenter.mag();
		desire = new Desire(0.01f, towardsCenter.normalize().mult(distanceToCenter * 0.01f));
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
