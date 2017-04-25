package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class Instinct {

	private final Set<Boid> flock;

	Instinct(Set<Boid> flock) {
		this.flock = flock;
	}

	public abstract PVector calculateImpulse(Boid self);

	public float getNeighbourRadius() {
		return 128;
	}

	Set<Boid> getNeighbours(Boid self) {
		return flock.stream()
				.filter(boid -> boid != self)
				.filter(boid -> PVector.dist(self.position, boid.position) < getNeighbourRadius())
				.filter(self::canSee)
				.collect(Collectors.toSet());
	}

	public static class Impulse {
		static Impulse none = new Impulse(0, new PVector());

		public final float urgency;
		public final PVector velocity;

		Impulse(float urgency, PVector velocity) {
			this.urgency = urgency;
			this.velocity = velocity;
		}

		@Override
		public String toString() {
			return String.format("{urgency: %.2f, speed: %.2f}", urgency, velocity.mag());
		}
	}
}
