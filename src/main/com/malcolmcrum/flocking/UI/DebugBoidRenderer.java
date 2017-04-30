package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Boid;
import com.malcolmcrum.flocking.FlockManager;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static processing.core.PConstants.*;

public class DebugBoidRenderer implements Renderer, InputHandler {
	private final PApplet graphics;
	private Boid boid;

	public DebugBoidRenderer(PApplet graphics) {
		this.graphics = graphics;
	}

	@Override
	public void handleClick(int mouseX, int mouseY) {
		float minimumDistance = 16;
		PVector click = new PVector(mouseX, mouseY);
		boid = FlockManager.getFlocks().stream()
				.flatMap(flock -> flock.getBoids().stream())
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
		for (Boid.Desire desire : getActiveDesires()) {
			setColours(desire.name.hashCode(), graphics);
			graphics.noFill();
			graphics.arc(boid.position.x, boid.position.y, desire.radius, desire.radius,
					-Boid.fieldOfView/2 + boid.velocity.heading(), Boid.fieldOfView/2 + boid.velocity.heading(), PIE);
			line(boid.position, PVector.add(boid.position, desire.velocity));
			graphics.text(desire.toString(), textX, textY);
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

	private List<Boid.Desire> getActiveDesires() {
		if (boid == null) {
			return new ArrayList<>();
		} else {
			return boid.getDesires().stream()
					.filter(desire -> desire.velocity.mag() > 0)
					.sorted((a, b) -> Float.compare(b.velocity.magSq(), a.velocity.magSq()))
					.collect(Collectors.toList());
		}
	}

	private void line(PVector from, PVector to) {
		graphics.line(from.x, from.y, to.x, to.y);
	}

}
