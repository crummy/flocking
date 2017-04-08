package com.malcolmcrum.flocking.Renderers;

import com.malcolmcrum.flocking.Instincts.*;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class UIRenderer implements Renderer {
	private final PApplet graphics;
	private final List<String> instructions;

	public UIRenderer(PApplet graphics) {
		this.graphics = graphics;
		this.instructions = new ArrayList<>();
		this.instructions.add("(1) AvoidBoundaries: " + AvoidBoundaries.isEnabled);
		this.instructions.add("(2) AvoidOthers: " + AvoidOthers.isEnabled);
		this.instructions.add("(3) ClampSpeed: " + ClampSpeed.isEnabled);
		this.instructions.add("(4) Cohesion: " + Cohesion.isEnabled);
		this.instructions.add("(5) Random: " + Random.isEnabled);
		this.instructions.add("(6) Debug info");
		this.instructions.add("(R) Restart");
		this.instructions.add("(Space) Pause");
		this.instructions.add("Click a bird for details");
	}

	@Override
	public void draw() {
		graphics.fill(255);
		int x = 4;
		int y = 10;
		int spacing = 12;
		for (String s : instructions) {
			graphics.text(s, x, y);
			y += spacing;
		}
	}
}
