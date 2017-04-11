package com.malcolmcrum.flocking.Renderers;

import com.malcolmcrum.flocking.Flock;
import com.malcolmcrum.flocking.Instincts.*;
import processing.core.PApplet;

public class UIRenderer implements Renderer {
	private final PApplet graphics;
	private final Flock flock;
	private int x;
	private int y;
	private static final int spacing = 12;

	public UIRenderer(PApplet graphics, Flock flock) {
		this.graphics = graphics;
		this.flock = flock;
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
}
