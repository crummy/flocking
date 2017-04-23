package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.UI.FlockRenderer;
import processing.core.PApplet;
import processing.opengl.PShader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlockManager {
	private static List<Flock> flocks = new ArrayList<>();
	private static int selectedFlockIndex = 0;
	private final PApplet graphics;
	private final PShader shader;

	FlockManager(PApplet graphics) {
		this.graphics = graphics;
		this.shader = graphics.loadShader("shader.glsl");
	}

	public static List<Flock> getFlocks() {
		return flocks;
	}

	void update() {
		flocks.forEach(Flock::update);
	}

	void draw() {
		if (FlockRenderer.debugColours == false) {
			List<Boid> allBoids = flocks.stream().flatMap(flock -> flock.getBoids().stream()).collect(Collectors.toList());
			float[] boidPositions = new float[allBoids.size() * 2];
			for (int i = 0; i < allBoids.size(); ++i) {
				boidPositions[i * 2] = allBoids.get(i).position.x;
				boidPositions[i * 2 + 1] = allBoids.get(i).position.y;
			}
			shader.set("totalBoids", allBoids.size());
			shader.set("boids", boidPositions);
			graphics.shader(shader);
			graphics.background(0);
			graphics.rect(0, 0, graphics.width, graphics.height);
		} else {
			this.graphics.resetShader();
		}
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
