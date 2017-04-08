package com.malcolmcrum.flocking;

import java.util.Random;

public class RNG {
	private static final Random rng = new Random();

	public static int between(int low, int high) {
		Assert.assertTrue(low < high);

		int range = high - low;
		return low + rng.nextInt(range);
	}

	public static float between(float low, float high) {
		Assert.assertTrue(low < high);

		float range = high - low;
		return low + (rng.nextFloat() * range);
	}
}
