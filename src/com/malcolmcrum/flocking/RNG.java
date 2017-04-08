package com.malcolmcrum.flocking;

import java.util.Random;

class RNG {
	private static final Random rng = new Random();

	static int between(int low, int high) {
		Assert.assertTrue(low < high);

		int range = high - low;
		return low + rng.nextInt(range);
	}

	static float between(float low, float high) {
		Assert.assertTrue(low < high);

		float range = high - low;
		return low + (rng.nextFloat() * range);
	}
}
