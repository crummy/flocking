package com.malcolmcrum.flocking.Desires;

import com.malcolmcrum.flocking.Bird;
import com.malcolmcrum.flocking.Rectangle;
import processing.core.PVector;

public class AvoidBoundaries implements Desire {
	private final Bird self;
	private final Rectangle boundaries;

	public AvoidBoundaries(Bird self, Rectangle windowBounds) {
		this.self = self;
		int margin = 64;
		this.boundaries = new Rectangle(windowBounds.left + margin, windowBounds.right - margin, windowBounds.top + margin, windowBounds.bottom - margin);
	}

	@Override
	public Change get() {
		float strength = 0;
		PVector awayFromWall = new PVector();
		if (self.position.x < boundaries.left) {
			awayFromWall.x = 10;
			strength += 0.2f;
		} else if (self.position.x > boundaries.right) {
			awayFromWall.x = -10;
			strength += 0.2f;
		}
		if (self.position.y < boundaries.top) {
			awayFromWall.y = 10;
			strength += 0.2f;
		} else if (self.position.y > boundaries.bottom) {
			awayFromWall.y = -10;
			strength += 0.2f;
		}
		return new Change(strength, awayFromWall);
	}
}
