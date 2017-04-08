package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.AvoidBoundaries;
import com.malcolmcrum.flocking.Instincts.AvoidOthers;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class Flock {
	private static final float maxInitialSpeed = 0.5f;

	private final Rectangle bounds;
	private final Set<Bird> birds;

	Flock(int width, int height) {
		birds = new HashSet<>();
		bounds = new Rectangle(0, width, 0, height);
	}

	void addBirds(int count) {
		for (int i = 0; i < count; ++i) {
			PVector initialPosition = new PVector(RNG.between(bounds.left, bounds.right), RNG.between(bounds.top, bounds.bottom));
			PVector initialVelocity = new PVector(RNG.between(-maxInitialSpeed, maxInitialSpeed), RNG.between(-maxInitialSpeed, maxInitialSpeed));
			Bird bird = new Bird(initialPosition, initialVelocity);
			bird.addDesire(new AvoidBoundaries(bird, bounds));
			bird.addDesire(new AvoidOthers(bird, birds));
			birds.add(bird);
		}
	}

	Set<Bird> getBirds() {
		return birds;
	}

	void update() {
		birds.forEach(bird -> {
			Set<Bird> others = birds.stream().filter(otherBird -> otherBird != bird).collect(Collectors.toSet());
			bird.update();
		});
	}
}
