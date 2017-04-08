package com.malcolmcrum.flocking.Desires;

import processing.core.PVector;

public interface Desire {
	Change get();

	class Change {
		public final float desireStrength;
		public final PVector accelerationChange;

		Change(float desireStrength, PVector accelerationChange) {
			this.desireStrength = desireStrength;
			this.accelerationChange = accelerationChange;
		}
	}
}
