package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;
import processing.core.PVector;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class Instinct {

	protected final Bird self;
	private final Set<Bird> otherBirds;

	public Instinct(Bird self, Set<Bird> birds) {
		this.self = self;
		this.otherBirds = birds;
	}

	public abstract Desire get();

	public boolean isEnabled() {
		return true;
	}

	public float getNeighbourRadius() {
		return 64;
	}

	Set<Bird> getNeighbours() {
		return otherBirds.stream()
				.filter(bird -> bird != self)
				.filter(bird -> PVector.dist(self.position, bird.position) < getNeighbourRadius())
				.collect(Collectors.toSet());
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
			return String.format("{strength: %s, velocity: %s}", strength, velocity);
		}

		static Desire none() {
			return new Desire(0, new PVector());
		}
	}
}
