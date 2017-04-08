package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.*;
import com.malcolmcrum.flocking.Renderers.FlockRenderer;

import java.util.HashMap;
import java.util.Map;

class InputHandler {
	private Map<Character, Runnable> keyMappings;

	InputHandler(Main main) {
		keyMappings = new HashMap<>();
		keyMappings.put('1', () -> AvoidBoundaries.isEnabled = !AvoidBoundaries.isEnabled);
		keyMappings.put('2', () -> AvoidOthers.isEnabled = !AvoidOthers.isEnabled);
		keyMappings.put('3', () -> ClampSpeed.isEnabled = !ClampSpeed.isEnabled);
		keyMappings.put('4', () -> Cohesion.isEnabled = !Cohesion.isEnabled);
		keyMappings.put('5', () -> Random.isEnabled = !Random.isEnabled);
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
