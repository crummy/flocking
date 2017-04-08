package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;

public class ClampSpeed implements Instinct {
	private static final float minSpeed = 10;
	private static final float maxSpeed = 100;

	private final Bird self;

	public ClampSpeed(Bird self) {
		this.self = self;
	}

	@Override
	public Desire get() {
		float speed = self.velocity.mag();
		if (speed < minSpeed) {
			return new Desire(1, self.velocity.normalize(null).mult(minSpeed));
		} else if (speed > maxSpeed) {
			return new Desire(1, self.velocity.normalize(null).mult(maxSpeed));
		} else {
			// we like to go fast!
			return new Desire(0.5f, self.velocity.normalize(null).mult(maxSpeed));
		}
	}

	@Override
	public String toString() {
		return "ClampSpeed";
	}
}
