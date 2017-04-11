package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Assert;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;

public class MenuRenderer implements Renderer {
	private final PApplet graphics;
	private final int startX;
	private final int startY;
	private final int alignment;
	private final List<Supplier<String>> items;
	private final int textSize;
	private final int xStep;
	private final int yStep;

	private MenuRenderer(PApplet graphics, int x, int y, int alignment, int textSize, int xStep, int yStep, List<Supplier<String>> items) {
		this.graphics = graphics;
		this.startX = x;
		this.startY = y;
		this.alignment = alignment;
		this.textSize = textSize;
		this.xStep = xStep;
		this.yStep = yStep;
		this.items = items;
	}

	@Override
	public void draw() {
		graphics.textAlign(alignment);
		graphics.textSize(textSize);
		int x = startX;
		int y = startY;
		for (Supplier<String> item : items) {
			graphics.text(item.get(), x, y);
			x += xStep;
			y += yStep;
		}
	}

	public static class Builder {
		private final PApplet graphics;
		private int x = 0;
		private int y = 0;
		private int xStep = 16;
		private int yStep = 0;
		private int alignment = LEFT;
		private int textSize = 12;
		private List<Supplier<String>> items = new ArrayList<>();

		public Builder(PApplet graphics) {
			this.graphics = graphics;
		}

		public Builder coords(int x, int y) {
			this.x = x;
			this.y = y;
			return this;
		}

		public Builder textSize(int textSize) {
			this.textSize = textSize;
			return this;
		}

		public Builder lineSpacing(int xStep, int yStep) {
			this.xStep = xStep;
			this.yStep = yStep;
			return this;
		}

		public Builder alignment(int alignment) {
			Assert.assertTrue(alignment == LEFT || alignment == RIGHT);
			this.alignment = alignment;
			return this;
		}

		public Builder text(Supplier<String> text) {
			this.items.add(text);
			return this;
		}

		public Builder text(String text) {
			this.items.add(() -> text);
			return this;
		}

		public MenuRenderer build() {
			return new MenuRenderer(graphics, x, y, alignment, textSize, xStep, yStep, items);
		}
	}
}
