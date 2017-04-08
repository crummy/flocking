package com.malcolmcrum.flocking.Renderers;

import com.malcolmcrum.flocking.Instincts.*;
import processing.core.PApplet;

public class UIRenderer implements Renderer {
	private final PApplet graphics;
	private int x;
	private int y;
	private static final int spacing = 12;

	public UIRenderer(PApplet graphics) {
		this.graphics = graphics;
	}

	@Override
	public void draw() {
		graphics.fill(255);
		x = 4;
		y = 12;
		text("(1) AvoidBoundaries: " + AvoidBoundaries.isEnabled);
		text("(2) AvoidOthers: " + AvoidOthers.isEnabled);
		text("(3) ClampSpeed: " + ClampSpeed.isEnabled);
		text("(4) Cohesion: " + Cohesion.isEnabled);
		text("(5) Random: " + Random.isEnabled);
		text("(6) Debug info");
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
