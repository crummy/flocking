package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.*;
import processing.core.PVector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.malcolmcrum.flocking.Instincts.Instinct.*;

public class Flock {
	private static final float maxInitialSpeed = 0.5f;

	private final Rectangle bounds;
	private final Set<Bird> birds;
	private final Map<Class<? extends Instinct>, DesireMultiplier> desireMultipliers;

	Flock(int width, int height) {
		desireMultipliers = createDesireMultipliers();
		birds = new HashSet<>();
		bounds = new Rectangle(0, width, 0, height);
	}

	private Map<Class<? extends Instinct>, DesireMultiplier> createDesireMultipliers() {
		Map<Class<? extends Instinct>, DesireMultiplier> multipliers = new HashMap<>();
		multipliers.put(ClampSpeed.class, new DesireMultiplier(1));
		multipliers.put(AvoidBoundaries.class, new DesireMultiplier(1));
		multipliers.put(Separation.class, new DesireMultiplier(1));
		multipliers.put(Cohesion.class, new DesireMultiplier(1));
		multipliers.put(Random.class, new DesireMultiplier(1));
		multipliers.put(Alignment.class, new DesireMultiplier(1));
		return multipliers;
	}

	void addBirds(int count) {
		for (int i = 0; i < count; ++i) {
			PVector initialPosition = new PVector(RNG.between(bounds.left, bounds.right), RNG.between(bounds.top, bounds.bottom));
			PVector initialVelocity = new PVector(RNG.between(-maxInitialSpeed, maxInitialSpeed), RNG.between(-maxInitialSpeed, maxInitialSpeed));
			Bird bird = new Bird(initialPosition, initialVelocity);
			bird.addDesire(new ClampSpeed(bird, birds, desireMultipliers.get(ClampSpeed.class)));
			bird.addDesire(new AvoidBoundaries(bird, birds, desireMultipliers.get(AvoidBoundaries.class), bounds));
			bird.addDesire(new Separation(bird, birds, desireMultipliers.get(Separation.class)));
			bird.addDesire(new Cohesion(bird, birds, desireMultipliers.get(Cohesion.class)));
			bird.addDesire(new Random(bird, birds, desireMultipliers.get(Random.class)));
			bird.addDesire(new Alignment(bird, birds, desireMultipliers.get(Alignment.class)));
			birds.add(bird);
		}
	}

	public Set<Bird> getBirds() {
		return birds;
	}

	void update() {
		birds.forEach(Bird::update);
	}
}
