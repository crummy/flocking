package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PVector;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class Instinct {

	private final Set<Boid> flock;

	Instinct(Set<Boid> flock) {
		this.flock = flock;
	}

	public abstract PVector calculateImpulse(Boid self);

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

}
