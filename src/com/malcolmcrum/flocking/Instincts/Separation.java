package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;
import processing.core.PVector;

import java.util.Set;

import static processing.core.PConstants.PI;
import static processing.core.PVector.angleBetween;
import static processing.core.PVector.dist;

public class Separation extends Instinct {
	public static boolean isEnabled = false;

	private static final float dangerCloseDistance = 16;
	private static final float tooCloseDistance = 32;
	private static final float closeAngle = PI/4; // if angle between two close birds is outside this, we don't consider them close - they'll be gone soon

	public Separation(Bird self, Set<Bird> birds) {
		super(self, birds);
	}

	@Override
	public void update() {
		float strength = 0;
		PVector awayFromOthers = new PVector();
		for (Bird other : getNeighbours()) {
			float distance = dist(self.position, other.position);

			float velocityAngles = angleBetween(self.velocity, other.velocity);
			boolean onSameCourse = velocityAngles < closeAngle;
			if (distance < dangerCloseDistance && onSameCourse) {
				strength = 0.5f;
			} else if (distance < tooCloseDistance && onSameCourse) {
				strength = 0.1f;
			}

			float angle = angleBetween(self.velocity, other.velocity);
			awayFromOthers.add(PVector.fromAngle(angle + PI));
		}

		desire = new Desire(strength, awayFromOthers);
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
}
