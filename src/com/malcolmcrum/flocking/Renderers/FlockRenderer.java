package com.malcolmcrum.flocking.Renderers;

import com.malcolmcrum.flocking.Bird;
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
	private Bird selectedBird;

	public FlockRenderer(PApplet graphics, Flock flock) {
		this.graphics = graphics;
		this.flock = flock;
	}

	public void handleClick(int mouseX, int mouseY) {
		float minimumDistance = 16;
		PVector click = new PVector(mouseX, mouseY);
		selectedBird = flock.getBirds().stream()
				.filter(bird -> PVector.dist(bird.position, click) < minimumDistance)
				.sorted((a, b) -> Float.compare(PVector.dist(a.position, click), PVector.dist(b.position, click)))
				.findFirst()
				.orElse(null);
	}

	@Override
	public void draw() {
		flock.getBirds().forEach(this::draw);

		if (selectedBird != null) {
			drawDebug(selectedBird);
		}
	}

	private void setColours(int hash) {
		int r = (hash & 0xFF0000) >> 16;
		int g = (hash & 0x00FF00) >> 8;
		int b = hash & 0x0000FF;
		graphics.stroke(r, g, b);
		graphics.fill(r, g, b);
	}

	private void draw(Bird bird) {
		if (debugColours && bird.getInstincts() != null) {
			int greatestDesire = bird.getInstincts().stream().sorted(Instinct::comparator).findFirst().get().toString().hashCode();
			setColours(greatestDesire);
		} else {
			graphics.stroke(255);
			graphics.fill(255);
		}

		int width = 8;
		int height = 12;
		graphics.pushMatrix();
		graphics.translate(bird.position.x, bird.position.y);
		graphics.rotate(bird.velocity.heading() + PI/2);
		graphics.triangle(-width/2, height/2, 0, -height/2, width/2, height/2);
		graphics.popMatrix();
	}

	private void drawDebug(Bird bird) {
		int textX = (int)bird.position.x + 16;
		int textY = (int)bird.position.y;
		int spacing = 12;
		textY += spacing;
		for (Instinct instinct : bird.getInstincts().stream().sorted((a, b) -> Instinct.comparator(b, a)).collect(Collectors.toList())) {
			setColours(instinct.hashCode());
			graphics.noFill();
			graphics.ellipse(bird.position.x, bird.position.y, instinct.getNeighbourRadius(), instinct.getNeighbourRadius());
			if (instinct.getDesire() != null) {
				line(bird.position, PVector.add(bird.position, instinct.getDesire().velocity));
			}
			graphics.text(instinct.toString() + ": " + instinct.getDesire(), textX, textY);
			textY += spacing;
		}
		graphics.stroke(255);
		graphics.strokeWeight(2);
		line(bird.position, PVector.add(bird.position, bird.velocity));
		graphics.strokeWeight(1);

		graphics.textAlign(RIGHT);
		graphics.fill(255);
		graphics.text("Speed: " + bird.velocity.mag(), bird.position.x - 16, bird.position.y);
		graphics.textAlign(LEFT);
	}

	private void line(PVector from, PVector to) {
		graphics.line(from.x, from.y, to.x, to.y);
	}
}
