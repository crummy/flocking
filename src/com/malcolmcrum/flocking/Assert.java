package com.malcolmcrum.flocking;

class Assert {
	static class AssertionFailure extends RuntimeException {

	}

	static void assertTrue(boolean b) {
		if (b == false) {
			throw new AssertionFailure();
		}
	}
}
