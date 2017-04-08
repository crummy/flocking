package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;
import com.malcolmcrum.flocking.Rectangle;
import processing.core.PVector;

public class AvoidBoundaries implements Instinct {
	private final Bird self;
	private final Rectangle boundaries;

	public AvoidBoundaries(Bird self, Rectangle windowBounds) {
		this.self = self;
		int margin = 64;
		this.boundaries = new Rectangle(windowBounds.left + margin, windowBounds.right - margin, windowBounds.top + margin, windowBounds.bottom - margin);
	}

	@Override
	public Desire get() {
		float strength = 0;
		PVector awayFromWall = self.velocity;
		if (self.position.x < boundaries.left) {
			awayFromWall.x = 10;
			strength += 0.5f;
		} else if (self.position.x > boundaries.right) {
			awayFromWall.x = -10;
			strength += 0.5f;
		}
		if (self.position.y < boundaries.top) {
			awayFromWall.y = 10;
			strength += 0.5f;
		} else if (self.position.y > boundaries.bottom) {
			awayFromWall.y = -10;
			strength += 0.5f;
		}
		return new Desire(strength, awayFromWall);
	}

	@Override
	public String toString() {
		return "AvoidBoundaries";
	}
}
