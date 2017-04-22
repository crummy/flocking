package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.UI.FlockRenderer;
import processing.core.PApplet;
import processing.opengl.PShader;

import java.util.ArrayList;
import java.util.List;

public class FlockManager {
	private static List<Flock> flocks = new ArrayList<>();
	private static int selectedFlockIndex = 0;
	private final PApplet graphics;
	private final PShader shader;

	FlockManager(PApplet graphics) {
		this.graphics = graphics;
		this.shader = graphics.loadShader("shader.glsl");
		this.shader.set("width", (float)graphics.width);
		this.shader.set("height", (float)graphics.height);
	}

	public static List<Flock> getFlocks() {
		return flocks;
	}

	void update() {
		flocks.forEach(Flock::update);
	}

	void draw() {
		graphics.shader(shader);
		flocks.forEach(flock -> new FlockRenderer(graphics, flock).draw());
	}

	public void add(Flock flock) {
		flocks.add(flock);
	}

	public static Flock getSelectedFlock() {
		return flocks.get(selectedFlockIndex);
	}

	public static void selectNextFlock() {
		int nextSelectedFlockIndex = selectedFlockIndex + 1;
		if (nextSelectedFlockIndex >= flocks.size()) {
			nextSelectedFlockIndex = 0;
		}

		selectedFlockIndex = nextSelectedFlockIndex;
	}

	public static void addBoidToFlock() {
		getSelectedFlock().addBoids(1);
	}

	public static void removeBoidFromFlock() {
		getSelectedFlock().getBoids().stream().findAny().ifPresent(boid -> getSelectedFlock().getBoids().remove(boid));
	}
}
