package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import com.malcolmcrum.flocking.RNG;
import processing.core.PVector;

import java.util.Set;

import static processing.core.PConstants.PI;

public class Random extends Instinct {
	public Random(Set<Boid> flock) {
		super(flock);
	}

	@Override
	public Desire calculateDesire(Boid boid) {
		if (RNG.between(0, 10) == 1) {
			float offset = RNG.between(-PI/2, PI/2);
			return new Desire(1f, PVector.fromAngle(boid.velocity.heading() + offset));
		} else {
			return Desire.none;
		}
	}
}
