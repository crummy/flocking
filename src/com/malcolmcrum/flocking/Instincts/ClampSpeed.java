package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;

import java.util.Set;

public class ClampSpeed extends Instinct {
	public static boolean isEnabled = true;

	private static final float minSpeed = 10;
	private static final float maxSpeed = 100;

	public ClampSpeed(Bird self, Set<Bird> birds) {
		super(self, birds);
	}

	@Override
	public void update() {
		float speed = self.velocity.mag();
		if (speed < minSpeed) {
			desire = new Desire(0.5f, self.velocity.normalize(null).mult(minSpeed));
		} else if (speed > maxSpeed) {
			desire = new Desire(0.5f, self.velocity.normalize(null).mult(maxSpeed));
		} else {
			// we like to go fast!
			desire = new Desire(0.05f, self.velocity.normalize(null).mult(maxSpeed));
		}
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public String toString() {
		return "ClampSpeed";
	}
}
