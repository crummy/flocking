package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;

/**
 * Created by crummy on 08.04.17.
 */
public class ClampSpeed implements Instinct {
	private static final float minSpeed = 1;
	private static final float maxSpeed = 4;

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
			return new Desire(0.1f, self.velocity.normalize(null).mult(maxSpeed));
		}
	}
}
