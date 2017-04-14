package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.*;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;

public class Flock {
	private static final float maxInitialSpeed = 0.5f;

	private final Rectangle bounds;
	private final Set<Boid> boids;
	private final Set<Instinct> instincts;

	private Flock(int width, int height) {
		this.instincts = new HashSet<>();
		this.boids = new HashSet<>();
		this.bounds = new Rectangle(0, width, 0, height);
	}

	void addBoids(int count) {
		for (int i = 0; i < count; ++i) {
			PVector initialPosition = new PVector(RNG.between(bounds.left, bounds.right), RNG.between(bounds.top, bounds.bottom));
			PVector initialVelocity = new PVector(RNG.between(-maxInitialSpeed, maxInitialSpeed), RNG.between(-maxInitialSpeed, maxInitialSpeed));
			Boid boid = new Boid(initialPosition, initialVelocity, instincts);
			boids.add(boid);
		}
	}

	public Set<Boid> getBoids() {
		return boids;
	}

	void update() {
		boids.forEach(Boid::update);
	}

	private Instinct addInstinct(Instinct instinct) {
		instincts.add(instinct);
		return instinct;
	}

	public Set<Instinct> getInstincts() {
		return instincts;
	}

	public static class Builder {

		private final Flock flock;

		public Builder(int width, int height) {
			this.flock = new Flock(width, height);
		}

		public Builder initialBoids(int count) {
			flock.addBoids(count);
			return this;
		}
		public Builder withDefaultInstincts() {
			flock.addInstinct(new Accelerate(flock.boids)).setWeight(0.5f);
			flock.addInstinct(new Alignment(flock.boids)).setWeight(0.5f);
			flock.addInstinct(new AvoidBoundaries(flock.boids, flock.bounds)).setWeight(1f);
			flock.addInstinct(new Cohesion(flock.boids)).setWeight(0.1f);
			flock.addInstinct(new Random(flock.boids)).setWeight(0.1f);
			flock.addInstinct(new Separation(flock.boids)).setWeight(0.5f);
			return this;
		}

		public Flock create() {
			return flock;
		}

	}
}
