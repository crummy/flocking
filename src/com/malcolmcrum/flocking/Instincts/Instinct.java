package com.malcolmcrum.flocking.Instincts;

import processing.core.PVector;

public interface Instinct {
	Desire get();

	class Desire {
		public final float strength;
		public final PVector velocity;

		Desire(float strength, PVector velocity) {
			this.strength = strength;
			this.velocity = velocity;
		}

		@Override
		public String toString() {
			return String.format("{strength: %s, velocity: %s}", strength, velocity);
		}

		static Desire none() {
			return new Desire(0, new PVector());
		}
	}
}
