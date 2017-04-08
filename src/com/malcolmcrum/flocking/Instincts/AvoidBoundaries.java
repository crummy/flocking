package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;
import com.malcolmcrum.flocking.Rectangle;
import processing.core.PVector;

import java.util.Set;

public class AvoidBoundaries extends Instinct {
	public static boolean isEnabled = true;

	private final Bird self;
	private final Rectangle boundary;
	private final int margin;
	private final float aversion;

	public AvoidBoundaries(Bird self, Set<Bird> birds, Rectangle windowBounds) {
		super(self, birds);
		this.self = self;
		this.margin = 256;
		this.aversion = 2.5f;
		this.boundary = new Rectangle(windowBounds.left + margin, windowBounds.right - margin, windowBounds.top + margin, windowBounds.bottom - margin);
	}

	@Override
	public void update() {

		PVector awayFromWall = self.velocity.copy();
		float xStrength = 0;
		if (self.position.x < boundary.left && self.velocity.x < 0) {
			xStrength = (boundary.left - self.position.x)/margin;
			awayFromWall.x += aversion * xStrength;
		} else if (self.position.x > boundary.right && self.velocity.x > 0) {
			xStrength = (self.position.x - boundary.right)/margin;
			awayFromWall.x += -aversion * xStrength;
		}
		float yStrength = 0;
		if (self.position.y < boundary.top && self.velocity.y < 0) {
			yStrength = (boundary.top - self.position.y)/margin;
			awayFromWall.y += aversion * yStrength;
		} else if (self.position.y > boundary.bottom && self.velocity.y > 0) {
			yStrength = (self.position.y - boundary.bottom)/margin;
			awayFromWall.y += -aversion * yStrength;
		}

		float strength = xStrength > yStrength ? xStrength : yStrength;
		if (strength > 1) { // out of bounds
			strength = 1;
		} else if (strength < 0) {
			strength = 0;
		}
		desire = new Desire(strength, awayFromWall);
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
}
