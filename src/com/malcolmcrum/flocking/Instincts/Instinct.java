package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;
import processing.core.PVector;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class Instinct {

	final Bird self;
	Desire desire;
	private final Set<Bird> otherBirds;

	Instinct(Bird self, Set<Bird> birds) {
		this.self = self;
		this.otherBirds = birds;
	}

	public abstract void update();

	public Desire getDesire() {
		return desire;
	}

	public abstract boolean isEnabled();

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
		if (a.getDesire() == null || b.getDesire() == null) {
			return 0;
		}
		return Float.compare(a.getDesire().strength, b.getDesire().strength);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "(" + getNeighbours().size() + ")";
	}

	public static class Desire {
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

		static Desire none() {
			return new Desire(0, new PVector());
		}
	}
}
