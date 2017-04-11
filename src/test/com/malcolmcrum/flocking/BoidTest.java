package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.Instinct;
import org.junit.jupiter.api.Test;
import processing.core.PVector;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoidTest {
	@Test
	void canSeeBirdInFront() {
		Boid self = new Boid(new PVector(10, 0), new PVector(0, 1), new Instinct.DesireMultipliers());
		Boid other = new Boid(new PVector(1, 2), new PVector(0, 0), new Instinct.DesireMultipliers());

		assertTrue(self.canSee(other));
	}

	@Test
	void cantSeeBirdBehind() {
		Boid self = new Boid(new PVector(10, 0), new PVector(0, 1), new Instinct.DesireMultipliers());
		Boid other = new Boid(new PVector(10, -2), new PVector(0, 0), new Instinct.DesireMultipliers());

		assertFalse(self.canSee(other));
	}

}