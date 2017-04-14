package com.malcolmcrum.flocking;

import org.junit.jupiter.api.Test;
import processing.core.PVector;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoidTest {
	@Test
	void canSeeBirdInFront() {
		Boid self = new Boid(new PVector(10, 0), new PVector(0, 1), new HashSet<>());
		Boid other = new Boid(new PVector(1, 2), new PVector(0, 0), new HashSet<>());

		assertTrue(self.canSee(other));
	}

	@Test
	void cantSeeBirdBehind() {
		Boid self = new Boid(new PVector(10, 0), new PVector(0, 1), new HashSet<>());
		Boid other = new Boid(new PVector(10, -2), new PVector(0, 0), new HashSet<>());

		assertFalse(self.canSee(other));
	}

}