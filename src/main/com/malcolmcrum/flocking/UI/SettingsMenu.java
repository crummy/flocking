package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Main;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsMenu extends Menu implements InputHandler {

	private final HashMap<Character, Runnable> keyMappings;
	private List<String> items;

	public SettingsMenu(PApplet graphics, int textSize) {
		super(graphics, graphics.width - 8, 12, PConstants.RIGHT, textSize, 0, textSize + 4, Colour.WHITE);
		items = new ArrayList<>();
		items.add("    C: Debug colours");
		items.add("    R: Restart");
		items.add("  TAB: Change flocks");
		items.add("SPACE: Pause");
		items.add("Click a boid for details");

		keyMappings = new HashMap<>();
		keyMappings.put(' ', () -> Main.isPaused = !Main.isPaused);
		keyMappings.put('c', () -> FlockRenderer.debugColours = !FlockRenderer.debugColours);
		keyMappings.put('r', graphics::setup);
	}

	@Override
	public void draw() {
		super.draw();
		items.forEach(this::text);
	}

	@Override
	public void keyPressed(char key) {
		if (keyMappings.containsKey(key)) {
			keyMappings.get(key).run();
		}
	}
}
