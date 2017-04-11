package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Flock;
import com.malcolmcrum.flocking.Instincts.*;
import com.malcolmcrum.flocking.Main;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.HashMap;
import java.util.function.Supplier;

public class UIRenderer implements Renderer, KeyHandler {
	private final HashMap<Character, Runnable> keyMappings;
	private final MenuRenderer flockMenu;
	private final MenuRenderer debugMenu;
	private final PApplet graphics;
	private final Flock flock;

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

		flockMenu = new MenuRenderer.Builder(graphics)
				.coords(8, 12)
				.lineSpacing(0, 16)
				.textSize(14)
				.text(instinctMenuItem(AvoidBoundaries.class))
				.text(instinctMenuItem(Separation.class))
				.text(instinctMenuItem(ClampSpeed.class))
				.text(instinctMenuItem(Cohesion.class))
				.text(instinctMenuItem(Random.class))
				.text(instinctMenuItem(Alignment.class))
				.build();

		debugMenu = new MenuRenderer.Builder(graphics)
				.coords(graphics.width - 8, 12)
				.lineSpacing(0, 16)
				.textSize(14)
				.alignment(PConstants.RIGHT)
				.text("(D) Debug info")
				.text("(R) Restart")
				.text("(Space) Pause")
				.text("Click a boid for details")
				.build();
	}

	private Supplier<String> instinctMenuItem(Class<? extends Instinct> instinct) {
		return () -> {
			setColours(instinct.hashCode(), graphics);
			return instinct.getSimpleName() + ": " + flock.getDesireMultipliers().get(instinct);
		};
	}


	@Override
	public void keyReleased(char key) {
		if (keyMappings.containsKey(key)) {
			keyMappings.get(key).run();
		}
	}

	@Override
	public void draw() {
		flockMenu.draw();
		debugMenu.draw();
	}
}
