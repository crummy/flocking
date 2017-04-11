package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;

import java.util.Set;

public class ClampSpeed extends Instinct {
	private enum State { BRAKING, ACCELERATING, COASTING }
	private State state;

	private static final float minSpeed = 90;
	private static final float maxSpeed = 100;

	public ClampSpeed(Bird self, Set<Bird> birds) {
		super(self, birds);
	}

	@Override
	public Desire calculateDesire() {
		float speed = self.velocity.mag();
		if (speed < minSpeed) {
			state = State.ACCELERATING;
			return new Desire(0.5f, self.velocity.normalize(null).mult(minSpeed));
		} else if (speed > maxSpeed) {
			state = State.BRAKING;
			return new Desire(0.5f, self.velocity.normalize(null).mult(maxSpeed));
		} else {
			state = State.COASTING;
			return new Desire(0.01f, self.velocity.normalize(null).mult(minSpeed));
		}
	}

	@Override
	public String toString() {
		return super.toString() + ", " + state;
	}
}
