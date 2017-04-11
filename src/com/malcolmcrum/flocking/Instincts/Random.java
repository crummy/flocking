package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import com.malcolmcrum.flocking.RNG;
import processing.core.PVector;

import java.util.Set;

import static processing.core.PConstants.PI;

public class Random extends Instinct {
	public Random(Boid self, Set<Boid> boids) {
		super(self, boids);
	}

	@Override
	public Desire calculateDesire() {
		if (RNG.between(0, 100) == 1) {
			float offset = RNG.between(-PI/2, PI/2);
			return new Desire(0.5f, PVector.fromAngle(self.velocity.heading() + offset));
		} else {
			return Desire.none;
		}
	}
}
