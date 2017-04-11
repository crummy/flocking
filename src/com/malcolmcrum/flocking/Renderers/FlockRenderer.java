package com.malcolmcrum.flocking.Renderers;

import com.malcolmcrum.flocking.Boid;
import com.malcolmcrum.flocking.Flock;
import com.malcolmcrum.flocking.Instincts.Instinct;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.stream.Collectors;

import static processing.core.PConstants.*;

public class FlockRenderer implements Renderer {
	public static boolean debugColours = true;

	private final PApplet graphics;
	private final Flock flock;
	private Boid selectedBoid;

	public FlockRenderer(PApplet graphics, Flock flock) {
		this.graphics = graphics;
		this.flock = flock;
	}

	public void handleClick(int mouseX, int mouseY) {
		float minimumDistance = 16;
		PVector click = new PVector(mouseX, mouseY);
		selectedBoid = flock.getBoids().stream()
				.filter(bird -> PVector.dist(bird.position, click) < minimumDistance)
				.sorted((a, b) -> Float.compare(PVector.dist(a.position, click), PVector.dist(b.position, click)))
				.findFirst()
				.orElse(null);
	}

	@Override
	public void draw() {
		flock.getBoids().forEach(this::draw);

		if (selectedBoid != null) {
			drawDebug(selectedBoid);
		}
	}

	private void setColours(int hash) {
		int r = (hash & 0xFF0000) >> 16;
		int g = (hash & 0x00FF00) >> 8;
		int b = hash & 0x0000FF;
		graphics.stroke(r, g, b);
		graphics.fill(r, g, b);
	}

	private void draw(Boid boid) {
		if (debugColours && boid.getInstincts() != null) {
			int greatestDesire = boid.getInstincts()
					.stream()
					.sorted((a, b) -> Instinct.comparator(b, a))
					.findFirst()
					.get()
					.getClass().hashCode();
			setColours(greatestDesire);
		} else {
			graphics.stroke(255);
			graphics.fill(255);
		}

		int width = 8;
		int height = 12;
		graphics.pushMatrix();
		graphics.translate(boid.position.x, boid.position.y);
		graphics.rotate(boid.velocity.heading() + PI/2);
		graphics.triangle(-width/2, height/2, 0, -height/2, width/2, height/2);
		graphics.popMatrix();
	}

	private void drawDebug(Boid boid) {
		int textX = (int) boid.position.x + 16;
		int textY = (int) boid.position.y;
		int spacing = 12;
		textY += spacing;
		for (Instinct instinct : boid.getInstincts().stream().sorted((a, b) -> Instinct.comparator(b, a)).collect(Collectors.toList())) {
			setColours(instinct.getClass().hashCode());
			graphics.noFill();
			graphics.ellipse(boid.position.x, boid.position.y, instinct.getNeighbourRadius(), instinct.getNeighbourRadius());
			line(boid.position, PVector.add(boid.position, instinct.getDesiredVelocity()));
			graphics.text(instinct.toString() + ": " + instinct.getDesiredVelocity(), textX, textY);
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

	private void line(PVector from, PVector to) {
		graphics.line(from.x, from.y, to.x, to.y);
	}
}
