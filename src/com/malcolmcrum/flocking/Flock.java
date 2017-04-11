package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.*;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;

import static com.malcolmcrum.flocking.Instincts.Instinct.DesireMultipliers;

public class Flock {
	private static final float maxInitialSpeed = 0.5f;

	private final Rectangle bounds;
	private final Set<Bird> birds;
	private final DesireMultipliers desireMultipliers;

	Flock(int width, int height) {
		desireMultipliers = new DesireMultipliers();
		birds = new HashSet<>();
		bounds = new Rectangle(0, width, 0, height);
	}

	void addBirds(int count) {
		for (int i = 0; i < count; ++i) {
			PVector initialPosition = new PVector(RNG.between(bounds.left, bounds.right), RNG.between(bounds.top, bounds.bottom));
			PVector initialVelocity = new PVector(RNG.between(-maxInitialSpeed, maxInitialSpeed), RNG.between(-maxInitialSpeed, maxInitialSpeed));
			Bird bird = new Bird(initialPosition, initialVelocity, desireMultipliers);
			bird.addDesire(new ClampSpeed(bird, birds));
			bird.addDesire(new AvoidBoundaries(bird, birds, bounds));
			bird.addDesire(new Separation(bird, birds));
			bird.addDesire(new Cohesion(bird, birds));
			bird.addDesire(new Random(bird, birds));
			bird.addDesire(new Alignment(bird, birds));
			birds.add(bird);
		}
	}

	public Set<Bird> getBirds() {
		return birds;
	}

	void update() {
		birds.forEach(Bird::update);
	}

	public DesireMultipliers getDesireMultipliers() {
		return desireMultipliers;
	}
}
