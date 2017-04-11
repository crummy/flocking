package com.malcolmcrum.flocking.UI;

import processing.core.PApplet;

public interface Renderer {
	void draw();

	default void setColours(int hash, PApplet graphics) {
		int r = (hash & 0xFF0000) >> 16;
		int g = (hash & 0x00FF00) >> 8;
		int b = hash & 0x0000FF;
		graphics.stroke(r, g, b);
		graphics.fill(r, g, b);
	}
}
