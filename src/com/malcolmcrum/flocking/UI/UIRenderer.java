package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Flock;
import com.malcolmcrum.flocking.Instincts.*;
import com.malcolmcrum.flocking.Main;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.HashMap;
import java.util.function.Supplier;

public class UIRenderer implements Renderer, InputHandler {
	private final HashMap<Character, Runnable> keyMappings;
	private final Menu flockMenu;
	private final Menu debugMenu;
	private final PApplet graphics;
	private final Flock flock;

	private final static float adjustmentIncrement = 0.05f;

	public UIRenderer(PApplet graphics, Flock flock) {
		this.graphics = graphics;
		this.flock = flock;

		flockMenu = new Menu.Builder(graphics)
				.coords(8, 12)
				.lineSpacing(0, 16)
				.textSize(14)
				.item(instinctMenuItem(AvoidBoundaries.class), adjustUrgency(AvoidBoundaries.class))
				.item(instinctMenuItem(Separation.class), adjustUrgency(Separation.class))
				.item(instinctMenuItem(ClampSpeed.class), adjustUrgency(ClampSpeed.class))
				.item(instinctMenuItem(Cohesion.class), adjustUrgency(Cohesion.class))
				.item(instinctMenuItem(Random.class), adjustUrgency(Random.class))
				.item(instinctMenuItem(Alignment.class), adjustUrgency(Alignment.class))
				.build();

		debugMenu = new Menu.Builder(graphics)
				.coords(graphics.width - 8, 12)
				.lineSpacing(0, 16)
				.textSize(14)
				.alignment(PConstants.RIGHT)
				.item("(c) Debug colours")
				.item("(R) Restart")
				.item("(Space) Pause")
				.item("Click a boid for details")
				.build();

		keyMappings = new HashMap<>();
		keyMappings.put('1', () -> flock.getDesireMultipliers().set(AvoidBoundaries.class, 1));
		keyMappings.put('2', () -> flock.getDesireMultipliers().set(Separation.class, 1));
		keyMappings.put('3', () -> flock.getDesireMultipliers().set(ClampSpeed.class, 1));
		keyMappings.put('4', () -> flock.getDesireMultipliers().set(Cohesion.class, 1));
		keyMappings.put('5', () -> flock.getDesireMultipliers().set(Random.class, 1));
		keyMappings.put('6', () -> flock.getDesireMultipliers().set(Alignment.class, 1));
		keyMappings.put(' ', () -> Main.isPaused = !Main.isPaused);
		keyMappings.put('c', () -> FlockRenderer.debugColours = !FlockRenderer.debugColours);
		keyMappings.put('r', graphics::setup);
	}

	private InputHandler adjustUrgency(Class<? extends Instinct> instinct) {
		return new InputHandler() {
			@Override
			public void handleRight() {
				float currentUrgency = flock.getDesireMultipliers().get(instinct);
				if (currentUrgency < 1) {
					float newUrgency = currentUrgency + adjustmentIncrement;
					newUrgency = newUrgency > 1 ? 1 : newUrgency;
					flock.getDesireMultipliers().set(instinct, newUrgency);
				}
			}

			@Override
			public void handleLeft() {
				float currentUrgency = flock.getDesireMultipliers().get(instinct);
				if (currentUrgency > 0) {
					float newUrgency = currentUrgency - adjustmentIncrement;
					newUrgency = newUrgency < 0 ? 0 : newUrgency;
					flock.getDesireMultipliers().set(instinct, newUrgency);
				}
			}
		};
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
		flockMenu.keyReleased(key);
	}

	@Override
	public void draw() {
		flockMenu.draw();
		debugMenu.draw();
	}
}
