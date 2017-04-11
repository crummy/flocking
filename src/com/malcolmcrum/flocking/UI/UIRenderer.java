package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Flock;
import com.malcolmcrum.flocking.Instincts.*;
import com.malcolmcrum.flocking.Main;
import processing.core.PApplet;

import java.util.HashMap;

public class UIRenderer implements Renderer, KeyHandler {
	private final PApplet graphics;
	private final Flock flock;
	private final HashMap<Character, Runnable> keyMappings;
	private int x;
	private int y;
	private static final int spacing = 12;

	public UIRenderer(PApplet graphics, Flock flock) {
		this.graphics = graphics;
		this.flock = flock;
		keyMappings = new HashMap<>();
		keyMappings.put('1', () -> flock.getDesireMultipliers().set(AvoidBoundaries.class, 1));
		keyMappings.put('2', () -> flock.getDesireMultipliers().set(Separation.class, 1));
		keyMappings.put('3', () -> flock.getDesireMultipliers().set(ClampSpeed.class, 1));
		keyMappings.put('4', () -> flock.getDesireMultipliers().set(Cohesion.class, 1));
		keyMappings.put('5', () -> flock.getDesireMultipliers().set(Random.class, 1));
		keyMappings.put('6', () -> flock.getDesireMultipliers().set(Alignment.class, 1));
		keyMappings.put(' ', () -> Main.isPaused = !Main.isPaused);
		keyMappings.put('d', () -> FlockRenderer.debugColours = !FlockRenderer.debugColours);
		keyMappings.put('r', graphics::setup);
	}

	@Override
	public void draw() {
		graphics.fill(255);
		x = 4;
		y = 12;
		text("(1) " + AvoidBoundaries.class.getSimpleName() + ": " + flock.getDesireMultipliers().get(AvoidBoundaries.class));
		text("(2) " + Separation.class.getSimpleName() + ": " + flock.getDesireMultipliers().get(Separation.class));
		text("(3) " + ClampSpeed.class.getSimpleName() + ": " + flock.getDesireMultipliers().get(ClampSpeed.class));
		text("(4) " + Cohesion.class.getSimpleName() + ": " + flock.getDesireMultipliers().get(Cohesion.class));
		text("(5) " + Random.class.getSimpleName() + ": " + flock.getDesireMultipliers().get(Random.class));
		text("(6) " + Alignment.class.getSimpleName() + ": " + flock.getDesireMultipliers().get(Alignment.class));
		text("(D) Debug info");
		text("(R) Restart");
		text("(Space) Pause");
		text("");
		text("Click a bird for details");
	}

	private void text(String string) {
		graphics.text(string, x, y);
		y += spacing;
	}

	@Override
	public void keyReleased(char key) {
		if (keyMappings.containsKey(key)) {
			keyMappings.get(key).run();
		}
	}
}
