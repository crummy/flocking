package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;
import com.malcolmcrum.flocking.Rectangle;
import processing.core.PVector;

import java.util.Set;

import static processing.core.PConstants.PI;

public class AvoidBoundaries extends Instinct {

	private static final float turningRate = PI/64;

	private final Bird self;
	private final Rectangle boundary;
	private final int margin;

	public AvoidBoundaries(Bird self, Set<Bird> birds, DesireMultiplier desireMultiplier, Rectangle windowBounds) {
		super(self, birds, desireMultiplier);
		this.self = self;
		this.margin = 256;
		this.boundary = new Rectangle(windowBounds.left + margin, windowBounds.right - margin, windowBounds.top + margin, windowBounds.bottom - margin);
	}

	@Override
	public Desire calculateDesire() {

		PVector desiredDirection = new PVector();
		float xStrength = 0;
		if (self.position.x < boundary.left) {
			xStrength = (boundary.left - self.position.x)/margin;
			desiredDirection.x = 1;
		} else if (self.position.x > boundary.right) {
			xStrength = (self.position.x - boundary.right)/margin;
			desiredDirection.x = -1;
		}
		float yStrength = 0;
		if (self.position.y < boundary.top) {
			yStrength = (boundary.top - self.position.y)/margin;
			desiredDirection.y = 1;
		} else if (self.position.y > boundary.bottom) {
			yStrength = (self.position.y - boundary.bottom)/margin;
			desiredDirection.y = -1;
		}

		float strength = xStrength > yStrength ? xStrength : yStrength;
		if (strength > 1) { // out of bounds
			strength = 1;
		} else if (strength < 0) {
			strength = 0;
		}
		float speed = self.velocity.mag();
		PVector desiredVelocity = PVector.lerp(self.velocity.copy().normalize(), desiredDirection, turningRate).mult(speed);
		return new Desire(strength * strength, desiredVelocity);
	}
}
