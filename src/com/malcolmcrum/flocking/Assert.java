package com.malcolmcrum.flocking;

public class Assert {
	static class AssertionFailure extends RuntimeException {

	}

	public static void assertTrue(boolean b) {
		if (b == false) {
			throw new AssertionFailure();
		}
	}
}
