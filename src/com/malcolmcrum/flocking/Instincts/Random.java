package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;
import com.malcolmcrum.flocking.RNG;
import processing.core.PVector;

import static processing.core.PConstants.PI;

public class Random implements Instinct {
	public static boolean isEnabled = false;
	private final Bird self;

	public Random(Bird self) {
		this.self = self;
	}

	@Override
	public Desire get() {
		if (RNG.between(0, 100) == 1) {
			float offset = RNG.between(-PI/2, PI/2);
			return new Desire(0.5f, PVector.fromAngle(self.velocity.heading() + offset));
		} else {
			return Desire.none();
		}
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public String toString() {
		return "Random";
	}
}
