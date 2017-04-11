package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Boid;
import com.malcolmcrum.flocking.Flock;
import com.malcolmcrum.flocking.Instincts.Instinct;
import processing.core.PApplet;

import static processing.core.PConstants.PI;

public class FlockRenderer implements Renderer {
	public static boolean debugColours = true;

	private final PApplet graphics;
	private final Flock flock;

	public FlockRenderer(PApplet graphics, Flock flock) {
		this.graphics = graphics;
		this.flock = flock;
	}

	@Override
	public void draw() {
		flock.getBoids().forEach(this::draw);
	}

	private void draw(Boid boid) {
		if (debugColours) {
			int greatestDesire = boid.getInstincts()
					.stream()
					.sorted((a, b) -> Instinct.comparator(b, a))
					.findFirst()
					.get()
					.getClass().hashCode();
			setColours(greatestDesire, graphics);
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
}
