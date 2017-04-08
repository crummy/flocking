package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.*;

import java.util.HashMap;
import java.util.Map;

class Config {
	Map<Character, Runnable> settings;

	public Config() {
		settings = new HashMap<>();
		settings.put('1', () -> AvoidBoundaries.isEnabled = !AvoidBoundaries.isEnabled);
		settings.put('2', () -> AvoidOthers.isEnabled = !AvoidOthers.isEnabled);
		settings.put('3', () -> ClampSpeed.isEnabled = !ClampSpeed.isEnabled);
		settings.put('4', () -> MoveTowardsNearby.isEnabled = !MoveTowardsNearby.isEnabled);
		settings.put('5', () -> Random.isEnabled = !Random.isEnabled);
	}

	public void keyReleased(char key) {
		if (settings.containsKey(key)) {
			settings.get(key).run();
		}
	}
}
