package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Assert;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;

public class MenuRenderer implements Renderer {
	private final PApplet graphics;
	private final float x;
	private final float y;
	private final int alignment;
	private final List<String> items;

	public MenuRenderer(PApplet graphics, float x, float y, int alignment, List<String> items) {
		this.graphics = graphics;
		this.x = x;
		this.y = y;
		this.alignment = alignment;
		this.items = items;
		reset();
	}

	private void reset() {

	}

	@Override
	public void draw() {
		graphics.textAlign(alignment);
		graphics.textSize(size);
		int
		for (String item : items) {
			graphics.text(item, x, y);
		}
	}

	public static class Builder {
		private final PApplet graphics;
		private float x = 0;
		private float y = 0;
		private int alignment = LEFT;
		private List<String> items = new ArrayList<>();

		public Builder(PApplet graphics) {
			this.graphics = graphics;
		}

		public Builder x(float x) {
			this.x = x;
			return this;
		}

		public Builder y(float y) {
			this.y = y;
			return this;
		}

		public Builder alignment(int alignment) {
			Assert.assertTrue(alignment == LEFT || alignment == RIGHT);
			this.alignment = alignment;
			return this;
		}

		public Builder text(String text) {
			this.items.add(text);
			return this;
		}

		public MenuRenderer build() {
			return new MenuRenderer(graphics, x, y, alignment, items);
		}
	}
}
