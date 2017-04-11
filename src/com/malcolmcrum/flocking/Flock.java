package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.*;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;

import static com.malcolmcrum.flocking.Instincts.Instinct.DesireMultipliers;

public class Flock {
	private static final float maxInitialSpeed = 0.5f;

	private final Rectangle bounds;
	private final Set<Boid> boids;
	private final DesireMultipliers desireMultipliers;

	Flock(int width, int height) {
		desireMultipliers = new DesireMultipliers();
		boids = new HashSet<>();
		bounds = new Rectangle(0, width, 0, height);
	}

	void addBirds(int count) {
		for (int i = 0; i < count; ++i) {
			PVector initialPosition = new PVector(RNG.between(bounds.left, bounds.right), RNG.between(bounds.top, bounds.bottom));
			PVector initialVelocity = new PVector(RNG.between(-maxInitialSpeed, maxInitialSpeed), RNG.between(-maxInitialSpeed, maxInitialSpeed));
			Boid boid = new Boid(initialPosition, initialVelocity, desireMultipliers);
			boid.addDesire(new Accelerate(boid, boids));
			boid.addDesire(new AvoidBoundaries(boid, boids, bounds));
			boid.addDesire(new Separation(boid, boids));
			boid.addDesire(new Cohesion(boid, boids));
			boid.addDesire(new Random(boid, boids));
			boid.addDesire(new Alignment(boid, boids));
			boids.add(boid);
		}
	}

	public Set<Boid> getBoids() {
		return boids;
	}

	void update() {
		boids.forEach(Boid::update);
	}

	public DesireMultipliers getDesireMultipliers() {
		return desireMultipliers;
	}
}
