package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;

class SeparationTest {
	@Test
	void testPushAway() {
		PVector velocityBefore = new PVector(0, 0);
		Boid boid = new Boid(new PVector(0, 0), velocityBefore, new HashSet<>());
		Boid neighbour = new Boid(new PVector(0, 1), new PVector(0, 0), new HashSet<>());
		Set<Boid> flock = new HashSet<>();
		flock.add(neighbour);
		flock.add(boid);
		Instinct separation = new Separation(flock);

		PVector velocityAfter = separation.calculateImpulse(boid);
		Assertions.assertTrue(velocityBefore.y > velocityAfter.y);
	}
}