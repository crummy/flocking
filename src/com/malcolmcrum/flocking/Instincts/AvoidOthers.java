package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;
import processing.core.PVector;

import java.util.Set;
import java.util.stream.Collectors;

import static processing.core.PApplet.lerp;
import static processing.core.PConstants.PI;
import static processing.core.PVector.angleBetween;
import static processing.core.PVector.dist;

public class AvoidOthers implements Instinct {
	private static final float dangerCloseDistance = 8;
	private static final float tooCloseDistance = 16;
	private static final float closeAngle = PI/4; // if angle between two close birds is outside this, we don't consider them close - they'll be gone soon

	private final Bird self;
	private final Set<Bird> allBirds;

	public AvoidOthers(Bird self, Set<Bird> allBirds) {
		this.self = self;
		this.allBirds = allBirds;
	}

	@Override
	public Desire get() {
		float strength = 0;
		PVector desiredVelocity = new PVector();
		for (Bird other : getOthers()) {
			float distance = dist(self.position, other.position);

			float velocityAngles = angleBetween(self.velocity, other.velocity);
			boolean onSameCourse = velocityAngles < closeAngle;
			if (distance < dangerCloseDistance && onSameCourse) {
				strength = lerp(strength, 1, 0.75f);
			} else if (distance < tooCloseDistance && onSameCourse) {
				strength = lerp(strength, 1, 0.3f);
			}

			float angle = angleBetween(self.velocity, other.velocity);
			desiredVelocity.add(PVector.fromAngle(angle));
		}

		return new Desire(strength, desiredVelocity);
	}

	private Set<Bird> getOthers() {
		return allBirds.stream().filter(bird -> bird != self).collect(Collectors.toSet());
	}

	@Override
	public String toString() {
		return "AvoidOthers";
	}
}
