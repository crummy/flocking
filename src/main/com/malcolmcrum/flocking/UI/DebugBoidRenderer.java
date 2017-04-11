package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Boid;
import com.malcolmcrum.flocking.Flock;
import com.malcolmcrum.flocking.Instincts.Instinct;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.PIE;
import static processing.core.PConstants.RIGHT;

public class DebugBoidRenderer implements Renderer, InputHandler {
	private final Flock flock;
	private final PApplet graphics;
	private Boid boid;

	public DebugBoidRenderer(Flock flock, PApplet graphics) {
		this.flock = flock;
		this.graphics = graphics;
	}

	@Override
	public void handleClick(int mouseX, int mouseY) {
		float minimumDistance = 16;
		PVector click = new PVector(mouseX, mouseY);
		boid = flock.getBoids().stream()
				.filter(boid -> PVector.dist(boid.position, click) < minimumDistance)
				.sorted((a, b) -> Float.compare(PVector.dist(a.position, click), PVector.dist(b.position, click)))
				.findFirst()
				.orElse(null);
	}

	@Override
	public void draw() {
		graphics.textAlign(LEFT);
		if (boid == null) {
			return;
		}

		int textX = (int) boid.position.x + 16;
		int textY = (int) boid.position.y;
		int spacing = 16;
		textY += spacing;
		for (Instinct instinct : getActiveInstincts()) {
			setColours(instinct.getClass().hashCode(), graphics);
			graphics.noFill();
			graphics.arc(boid.position.x, boid.position.y, instinct.getNeighbourRadius(), instinct.getNeighbourRadius(),
					-Boid.fieldOfView/2 + boid.velocity.heading(), Boid.fieldOfView/2 + boid.velocity.heading(), PIE);
			line(boid.position, PVector.add(boid.position, instinct.getDesiredVelocity()));
			graphics.text(instinct.toString(), textX, textY);
			textY += spacing;
		}
		graphics.stroke(255);
		graphics.strokeWeight(2);
		line(boid.position, PVector.add(boid.position, boid.velocity));
		graphics.strokeWeight(1);

		graphics.textAlign(RIGHT);
		graphics.fill(255);
		graphics.text("Speed: " + boid.velocity.mag(), boid.position.x - 16, boid.position.y);
		graphics.textAlign(LEFT);
	}

	private List<Instinct> getActiveInstincts() {
		if (boid == null) {
			return new ArrayList<>();
		} else {
			return boid.getInstincts()
					.stream()
					.filter(instinct -> instinct.getUrgency() > 0)
					.sorted((a, b) -> Instinct.comparator(b, a))
					.collect(Collectors.toList());
		}
	}

	private void line(PVector from, PVector to) {
		graphics.line(from.x, from.y, to.x, to.y);
	}

}