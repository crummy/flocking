package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Assert;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;

public class Menu implements Renderer, InputHandler {
	private final PApplet graphics;
	private final int startX;
	private final int startY;
	private final int alignment;
	private final List<Item> items;
	private final int textSize;
	private final int xStep;
	private final int yStep;

	private int selectedItemIndex = -1;

	private Menu(PApplet graphics, int x, int y, int alignment, int textSize, int xStep, int yStep, List<Item> items) {
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
	public void handleUp() {
		selectPrevious();
	}

	@Override
	public void handleDown() {
		selectNext();
	}

	@Override
	public void keyReleased(char key) {
		getSelectedItem().ifPresent(item -> keyReleased(key));
	}

	void selectNext() {
		select(selectedItemIndex + 1);
	}

	void selectPrevious() {
		select(selectedItemIndex - 1);
	}

	public void select(int index) {
		selectedItemIndex = index % items.size();
	}

	public Optional<Item> getSelectedItem() {
		if (selectedItemIndex == -1) {
			return Optional.empty();
		} else {
			Item selection = items.get(selectedItemIndex);
			return Optional.of(selection);
		}
	}

	@Override
	public void draw() {
		graphics.textAlign(alignment);
		graphics.textSize(textSize);
		int x = startX;
		int y = startY;
		for (Item item : items) {
			if (isSelected(item)) {
				graphics.text(">> " + item.getText(), x, y);
			} else {
				graphics.text(item.getText(), x, y);
			}
			x += xStep;
			y += yStep;
		}
	}

	private boolean isSelected(Item item) {
		return getSelectedItem().isPresent() && getSelectedItem().get() == item;
	}

	public static class Item {
		private final Supplier<String> text;
		private final InputHandler handler;

		public Item(Supplier<String> text, InputHandler inputHandler) {
			this.text = text;
			this.handler = inputHandler;
		}

		public Item(Supplier<String> text) {
			this.text = text;
			this.handler = null;
		}

		public Item(String text) {
			this.text = () -> text;
			this.handler = null;
		}

		public String getText() {
			return text.get();
		}

		public void handleKey(Character key) {
			if (handler != null) {
				handler.keyReleased(key);
			}
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
		private List<Item> items = new ArrayList<>();

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

		public Builder item(Supplier<String> text, InputHandler inputHandler) {
			this.items.add(new Item(text));
			return this;
		}

		public Builder item(String text) {
			this.items.add(new Item(text));
			return this;
		}

		public Menu build() {
			return new Menu(graphics, x, y, alignment, textSize, xStep, yStep, items);
		}
	}
}
