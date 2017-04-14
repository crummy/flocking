package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Flock;
import com.malcolmcrum.flocking.Instincts.Instinct;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.List;

public class InstinctMenu extends Menu implements InputHandler {
	private List<Flock> flocks;
	private Flock flock;
	private int selectedInstinctIndex;

	public InstinctMenu(PApplet graphics, int textSize, List<Flock> flocks) {
		super(graphics, 8, 12, PConstants.LEFT, textSize, 0, textSize + 4);
		this.flock = flocks.get(0);
		this.flocks = flocks;
		this.selectedInstinctIndex = 0;
	}

	@Override
	public void draw() {
		super.draw();
		text("Flock " + (selectedInstinctIndex + 1) + " (" + flock.getBoids().size() + " boids)");
		flock.getInstincts()
				.forEach(instinct -> {
					String prefix = isSelected(instinct) ? "> " : "";
					text(prefix + instinct.getClass().getSimpleName() + ": " + (instinct.getWeight()*100) + "%");
				});
	}

	@Override
	public void upReleased() {
		selectPrevious();
	}

	@Override
	public void downReleased() {
		selectNext();
	}

	@Override
	public void keyPressed(char key) {
		InputHandler.super.keyPressed(key);
		if (key == '\t') {
			int currentSelection = flocks.indexOf(flock);
			int nextSelection = currentSelection + 1;
			if (nextSelection > flocks.size() - 1) {
				nextSelection = 0;
			}
			flock = flocks.get(nextSelection);
		}
	}

	private void selectNext() {
		select(selectedInstinctIndex + 1);
	}

	private void selectPrevious() {
		select(selectedInstinctIndex - 1);
	}

	private boolean isSelected(Instinct instinct) {
		return new ArrayList<>(flock.getInstincts()).get(selectedInstinctIndex).equals(instinct);
	}

	private void select(int index) {
		if (index < 0) {
			selectedInstinctIndex = flock.getInstincts().size() - 1;
		} else if (index >= flock.getInstincts().size()) {
			selectedInstinctIndex = 0;
		} else {
			selectedInstinctIndex = index;
		}
	}
}
