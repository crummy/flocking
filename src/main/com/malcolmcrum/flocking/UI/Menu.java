package com.malcolmcrum.flocking.UI;

import processing.core.PApplet;

public abstract class Menu implements Renderer {
	private final PApplet graphics;
	private final int startX;
	private final int startY;
	private final int alignment;
	private final int textSize;
	private final int xStep;
	private final int yStep;
	private final Colour colour;

	private int x;
	private int y;

	Menu(PApplet graphics, int x, int y, int alignment, int textSize, int xStep, int yStep, Colour colour) {
		this.graphics = graphics;
		this.startX = x;
		this.startY = y;
		this.alignment = alignment;
		this.textSize = textSize;
		this.xStep = xStep;
		this.yStep = yStep;
		this.colour = colour;
	}

	@Override
	public void draw() {
		graphics.textAlign(alignment);
		graphics.textSize(textSize);
		graphics.fill(colour.r, colour.g, colour.b);
		x = startX;
		y = startY;
	}

	void text(String s) {
		graphics.text(s, x, y);
		x += xStep;
		y += yStep;
	}
}
