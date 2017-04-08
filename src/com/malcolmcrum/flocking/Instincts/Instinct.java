package com.malcolmcrum.flocking.Instincts;

import processing.core.PVector;

public interface Instinct {
	Desire get();

	class Desire {
		public final float strength;
		public final PVector acceleration;

		Desire(float strength, PVector acceleration) {
			this.strength = strength;
			this.acceleration = acceleration;
		}
	}
}
