package com.malcolmcrum.flocking.Renderers;

import com.malcolmcrum.flocking.Bird;
import com.malcolmcrum.flocking.Flock;
import com.malcolmcrum.flocking.Instincts.Instinct;
import processing.core.PApplet;
import processing.core.PVector;

import static processing.core.PConstants.PI;

public class BirdRenderer implements Renderer {
	public static boolean debugColours = true;

	private final PApplet graphics;
	private final Flock flock;
	private Bird selectedBird;

	public BirdRenderer(PApplet graphics, Flock flock) {
		this.graphics = graphics;
		this.flock = flock;
	}

	public void handleClick(int mouseX, int mouseY) {
		float minimumDistance = 8;
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

	private void draw(Bird bird) {
		if (debugColours && bird.getInstincts() != null) {
			int greatestDesire = bird.getInstincts().stream().sorted(Instinct::comparator).findFirst().get().toString().hashCode();
			int r = (greatestDesire & 0xFF0000) >> 16;
			int g = (greatestDesire & 0x00FF00) >> 8;
			int b = greatestDesire & 0x0000FF;
			graphics.stroke(r, g, b);
			graphics.fill(r, g, b);
		} else {
			graphics.stroke(255);
			graphics.fill(255);
		}

		int width = 8;
		int height = 12;
		graphics.pushMatrix();
		graphics.fill(255, 255);
		graphics.translate(bird.position.x, bird.position.y);
		graphics.rotate(bird.velocity.heading() + PI/2);
		graphics.triangle(-width/2, height/2, 0, -height/2, width/2, height/2);
		graphics.popMatrix();
	}

	private void drawDebug(Bird bird) {
		for (Instinct instinct : bird.getInstincts()) {
			graphics.stroke(0, 0, 255);
			graphics.noFill();
			graphics.ellipse(bird.position.x, bird.position.y, instinct.getNeighbourRadius(), instinct.getNeighbourRadius());
		}
	}
}
