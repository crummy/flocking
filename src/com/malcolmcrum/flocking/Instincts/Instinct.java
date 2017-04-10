package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Assert;
import com.malcolmcrum.flocking.Bird;
import processing.core.PVector;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class Instinct {

	final Bird self;
	private Desire desire;
	private final Set<Bird> otherBirds;
	private final DesireMultiplier desireMultiplier;

	Instinct(Bird self, Set<Bird> birds, DesireMultiplier desireMultiplier) {
		this.self = self;
		this.otherBirds = birds;
		this.desireMultiplier = desireMultiplier;
		this.desire = Desire.none;
	}

	public void update() {
		desire = calculateDesire();
	}

	public abstract Desire calculateDesire();

	public float getUrgency() {
		return desire.strength * desireMultiplier.get();
	}

	public PVector getDesiredVelocity() {
		return desire.velocity;
	}

	public float getNeighbourRadius() {
		return 128;
	}

	Set<Bird> getNeighbours() {
		return otherBirds.stream()
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

		public final float strength;
		public final PVector velocity;

		Desire(float strength, PVector velocity) {
			this.strength = strength;
			this.velocity = velocity;
		}

		@Override
		public String toString() {
			return String.format("{strength: %.2f, speed: %.2f}", strength, velocity.mag());
		}
	}

	public static class DesireMultiplier {
		private float multiplier;

		public DesireMultiplier(float initialMultiplier) {
			this.multiplier = initialMultiplier;
		}

		public float get() {
			return multiplier;
		}

		public void setMultiplier(float multiplier) {
			Assert.assertTrue(multiplier >= 0);
			Assert.assertTrue(multiplier <= 1);
			this.multiplier = multiplier;
		}
	}
}
