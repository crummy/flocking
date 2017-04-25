package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Boid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import processing.core.PVector;

import java.util.Collections;
import java.util.HashSet;

class AccelerateTest {

	@Test
	public void testDeceleration() {
		PVector velocityBefore = new PVector(Accelerate.maxSpeed + 1, Accelerate.maxSpeed + 1);
		Boid boid = new Boid(new PVector(0, 0), velocityBefore, new HashSet<>());
		Instinct accelerate = new Accelerate(Collections.singleton(boid));
		Instinct.Impulse impulse = accelerate.calculateImpulse(boid);

		PVector velocityAfter = impulse.velocity;
		Assertions.assertTrue(velocityBefore.mag() < velocityAfter.mag());
	}

	@Test
	public void testAcceleration() {
		PVector velocity = new PVector(Accelerate.maxSpeed - 1, Accelerate.maxSpeed - 1);
		Boid boid = new Boid(new PVector(0, 0), velocity, new HashSet<>());
		Instinct accelerate = new Accelerate(Collections.singleton(boid));
		Instinct.Impulse impulse = accelerate.calculateImpulse(boid);

		PVector velocityAfter = impulse.velocity;
		Assertions.assertTrue(velocity.mag() > velocityAfter.mag());
	}

}