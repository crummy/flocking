package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;
import processing.core.PVector;

import java.util.Set;

public class Alignment extends Instinct {
	public static boolean isEnabled = true;

	public Alignment(Bird self, Set<Bird> birds) {
		super(self, birds);
	}

	@Override
	public void update() {
		PVector averageVelocity = new PVector();
		Set<Bird> neighbours = getNeighbours();
		if (neighbours.size() == 0) {
			desire = Desire.none();
			return;
		}
		for (Bird bird : neighbours) {
			averageVelocity.add(bird.velocity);
		}
		averageVelocity.div(neighbours.size());
		desire = new Desire(0.1f, averageVelocity);
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
}
