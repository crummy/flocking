package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Flock;
import com.malcolmcrum.flocking.Instincts.*;
import com.malcolmcrum.flocking.Main;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class UIRenderer implements Renderer, InputHandler {
	private final HashMap<Character, Runnable> keyMappings;
	private final Menu flockMenu;
	private final Menu debugMenu;
	private final PApplet graphics;
	private final Flock flock;

	private final static float adjustmentIncrement = 0.01f;
	private Set<Character> keysDown;

	public UIRenderer(PApplet graphics, Flock flock) {
		this.graphics = graphics;
		this.flock = flock;
		this.keysDown = ConcurrentHashMap.newKeySet();

		flockMenu = new Menu.Builder(graphics)
				.coords(8, 12)
				.lineSpacing(0, 16)
				.textSize(14)
				.item(instinctMenuItem(AvoidBoundaries.class), adjustUrgency(AvoidBoundaries.class))
				.item(instinctMenuItem(Separation.class), adjustUrgency(Separation.class))
				.item(instinctMenuItem(Accelerate.class), adjustUrgency(Accelerate.class))
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
		keyMappings.put(' ', () -> Main.isPaused = !Main.isPaused);
		keyMappings.put('c', () -> FlockRenderer.debugColours = !FlockRenderer.debugColours);
		keyMappings.put('r', graphics::setup);
	}

	private InputHandler adjustUrgency(Class<? extends Instinct> instinct) {
		return new InputHandler() {
			@Override
			public void rightPressed() {
				float currentUrgency = flock.getDesireMultipliers().get(instinct);
				if (currentUrgency < 1) {
					float newUrgency = currentUrgency + adjustmentIncrement;
					newUrgency = newUrgency > 1 ? 1 : newUrgency;
					flock.getDesireMultipliers().set(instinct, newUrgency);
				}
			}

			@Override
			public void leftPressed() {
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
			return String.format("%s: %.0f%%", instinct.getSimpleName(), flock.getDesireMultipliers().get(instinct) * 100);
		};
	}


	@Override
	public void keyReleased(char key) {
		keysDown.remove(key);
		if (keyMappings.containsKey(key)) {
			keyMappings.get(key).run();
		}
		flockMenu.keyReleased(key);
	}

	@Override
	public void keyPressed(char key) {
		keysDown.add(key);
	}

	@Override
	public void draw() {
		flockMenu.draw();
		debugMenu.draw();
		keysDown.forEach(flockMenu::keyPressed);
	}
}
