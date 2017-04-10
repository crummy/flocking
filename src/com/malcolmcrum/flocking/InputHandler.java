package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.*;
import com.malcolmcrum.flocking.Renderers.FlockRenderer;

import java.util.HashMap;
import java.util.Map;

class InputHandler {
	private Map<Character, Runnable> keyMappings;

	InputHandler(Main main, Flock flock) {
		keyMappings = new HashMap<>();
		keyMappings.put('1', () -> flock.setDesireMultiplier(AvoidBoundaries.class, 1));
		keyMappings.put('2', () -> flock.setDesireMultiplier(Separation.class, 1));
		keyMappings.put('3', () -> flock.setDesireMultiplier(ClampSpeed.class, 1));
		keyMappings.put('4', () -> flock.setDesireMultiplier(Cohesion.class, 1));
		keyMappings.put('5', () -> flock.setDesireMultiplier(Random.class, 1));
		keyMappings.put('6', () -> flock.setDesireMultiplier(Alignment.class, 1));
		keyMappings.put(' ', () -> Main.isPaused = !Main.isPaused);
		keyMappings.put('d', () -> FlockRenderer.debugColours = !FlockRenderer.debugColours);
		keyMappings.put('r', main::setup);
	}

	void keyReleased(char key) {
		if (keyMappings.containsKey(key)) {
			keyMappings.get(key).run();
		}
	}
}
