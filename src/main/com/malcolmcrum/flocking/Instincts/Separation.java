package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.Optional;
import java.util.Set;

import static processing.core.PVector.dist;

public class Separation extends Instinct {
	public Separation(Set<Boid> flock) {
		super(flock);
	}

	@Override
	public float getNeighbourRadius() {
		return 16;
	}

	@Override
	public PVector calculateImpulse(Boid boid) {
		Optional<Boid> nearestNeighbour = getNeighbours(boid).stream()
				.sorted((a, b) -> Float.compare(dist(a.position, boid.position), dist(b.position, boid.position)))
				.findFirst();
		if (nearestNeighbour.isPresent()) {
			Boid neighbour = nearestNeighbour.get();
			float distance = dist(boid.position, neighbour.position);
			PVector away = PVector.sub(boid.position, neighbour.position).normalize();
			float urgency = PApplet.pow((distance - getNeighbourRadius())/getNeighbourRadius() * 3, 2);
			return away.mult(urgency);
		} else {
			return new PVector(0, 0);
		}
	}
}
