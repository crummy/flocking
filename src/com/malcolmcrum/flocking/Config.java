package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.*;

import java.util.HashMap;
import java.util.Map;

class Config {
	private Map<Character, Runnable> settings;

	Config() {
		settings = new HashMap<>();
		settings.put('1', () -> AvoidBoundaries.isEnabled = !AvoidBoundaries.isEnabled);
		settings.put('2', () -> AvoidOthers.isEnabled = !AvoidOthers.isEnabled);
		settings.put('3', () -> ClampSpeed.isEnabled = !ClampSpeed.isEnabled);
		settings.put('4', () -> Cohesion.isEnabled = !Cohesion.isEnabled);
		settings.put('5', () -> Random.isEnabled = !Random.isEnabled);
		settings.put(' ', () -> Main.isPaused = !Main.isPaused);
		settings.put('d', () -> Main.debugColours = !Main.debugColours);
	}

	void keyReleased(char key) {
		if (settings.containsKey(key)) {
			settings.get(key).run();
		}
	}
}
