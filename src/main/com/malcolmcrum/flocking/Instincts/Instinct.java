package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Assert;
import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class Instinct {

	private final Set<Boid> flock;
	private float weight;

	Instinct(Set<Boid> flock) {
		this.flock = flock;
		this.weight = 0.5f;
	}

	public void setWeight(float weight) {
		Assert.assertTrue(weight >= 0);
		Assert.assertTrue(weight <= 1);
		this.weight = weight;
	}

	public float getWeight() {
		return weight;
	}

	public Desire calculateWeightedDesire(Boid self) {
		Desire desire = calculateDesire(self);
		return new Desire(desire.urgency * weight, desire.velocity);
	}

	abstract Desire calculateDesire(Boid self);

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

	public static class Desire {
		static Desire none = new Desire(0, new PVector());

		public final float urgency;
		public final PVector velocity;

		Desire(float urgency, PVector velocity) {
			this.urgency = urgency;
			this.velocity = velocity;
		}

		@Override
		public String toString() {
			return String.format("{urgency: %.2f, speed: %.2f}", urgency, velocity.mag());
		}
	}
}
