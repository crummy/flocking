package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Assert;
import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Instinct {

	final Boid self;
	private Desire desire;
	private final Set<Boid> otherBoids;

	Instinct(Boid self, Set<Boid> boids) {
		this.self = self;
		this.otherBoids = boids;
		this.desire = Desire.none;
	}

	public void update() {
		desire = calculateDesire();
	}

	public abstract Desire calculateDesire();

	public float getUrgency() {
		return desire.urgency;
	}

	public PVector getDesiredVelocity() {
		return desire.velocity;
	}

	public float getNeighbourRadius() {
		return 128;
	}

	Set<Boid> getNeighbours() {
		return otherBoids.stream()
				.filter(bird -> bird != self)
				.filter(bird -> PVector.dist(self.position, bird.position) < getNeighbourRadius())
				.collect(Collectors.toSet());
	}

	public static int comparator(Instinct a, Instinct b) {
		return Float.compare(a.getUrgency(), b.getUrgency());
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "(" + getNeighbours().size() + ")";
	}

	public static class Desire {
		static Desire none = new Desire(0, new PVector());

		final float urgency;
		final PVector velocity;

		Desire(float urgency, PVector velocity) {
			this.urgency = urgency;
			this.velocity = velocity;
		}

		@Override
		public String toString() {
			return String.format("{urgency: %.2f, speed: %.2f}", urgency, velocity.mag());
		}
	}

	public static class DesireMultipliers {
		private Map<Class<? extends Instinct>, Float> multipliers;

		public DesireMultipliers() {
			this.multipliers = new HashMap<>();
			this.multipliers.put(Alignment.class, 0.5f);
			this.multipliers.put(AvoidBoundaries.class, 1f);
			this.multipliers.put(ClampSpeed.class, 0.5f);
			this.multipliers.put(Cohesion.class, 0.5f);
			this.multipliers.put(Separation.class, 0.5f);
			this.multipliers.put(Random.class, 0.5f);
		}

		public float get(Class<? extends Instinct> instinct) {
			return multipliers.get(instinct);
		}

		public void set(Class<? extends Instinct> instinct, float multiplier) {
			Assert.assertTrue(multiplier >= 0);
			Assert.assertTrue(multiplier <= 1);
			this.multipliers.put(instinct, multiplier);
		}
	}
}
