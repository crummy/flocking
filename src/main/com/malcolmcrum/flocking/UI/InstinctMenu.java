package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Flock;
import com.malcolmcrum.flocking.FlockManager;
import com.malcolmcrum.flocking.Instincts.Instinct;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class InstinctMenu extends Menu implements InputHandler {
	private static final float adjustmentIncrement = 0.01f;
	private int selectedInstinctIndex;

	public InstinctMenu(PApplet graphics, int textSize) {
		super(graphics, 8, 12, PConstants.LEFT, textSize, 0, textSize + 4, Colour.WHITE);
		this.selectedInstinctIndex = 0;
	}

	@Override
	public void draw() {
		super.draw();
		Flock flock = FlockManager.getSelectedFlock();
		text(flock.getName() + " (" + flock.getBoids().size() + " boids)");
		flock.getInstincts()
				.forEach(instinct -> {
					String prefix = isSelected(instinct) ? "> " : "";
					text(prefix + instinct.getClass().getSimpleName() + ": " + (instinct.getWeight()*100) + "%");
				});
	}
	
	@Override
	public void leftPressed() {
		adjustWeight(-adjustmentIncrement);
	}

	@Override
	public void rightPressed() {
		adjustWeight(adjustmentIncrement);
	}

	private void adjustWeight(float amount) {
		float weight = getSelectedInstinct().getWeight();
		float newWeight = weight + amount;
		if (newWeight < 0) {
			newWeight = 0;
		} else if (newWeight > 1) {
			newWeight = 1;
		}
		getSelectedInstinct().setWeight(newWeight);
	}

	@Override
	public void upPressed() {
		selectPrevious();
	}

	@Override
	public void downPressed() {
		selectNext();
	}

	@Override
	public void keyPressed(char key) {
		InputHandler.super.keyPressed(key);
		if (key == '\t') {
			FlockManager.selectNextFlock();
		}
	}

	private void selectNext() {
		select(selectedInstinctIndex + 1);
	}

	private void selectPrevious() {
		select(selectedInstinctIndex - 1);
	}

	// TODO improve this
	private boolean isSelected(Instinct instinct) {
		Flock flock = FlockManager.getSelectedFlock();
		return new ArrayList<>(flock.getInstincts()).get(selectedInstinctIndex).equals(instinct);
	}

	private void select(int index) {
		Flock flock = FlockManager.getSelectedFlock();
		if (index < 0) {
			selectedInstinctIndex = flock.getInstincts().size() - 1;
		} else if (index >= flock.getInstincts().size()) {
			selectedInstinctIndex = 0;
		} else {
			selectedInstinctIndex = index;
		}
	}

	private Instinct getSelectedInstinct() {
		return FlockManager.getSelectedFlock().getInstincts().get(selectedInstinctIndex);
	}
}
