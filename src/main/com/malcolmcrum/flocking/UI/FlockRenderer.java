package com.malcolmcrum.flocking.UI;

import com.malcolmcrum.flocking.Boid;
import com.malcolmcrum.flocking.Flock;
import processing.core.PApplet;

import static processing.core.PConstants.PI;

public class FlockRenderer implements Renderer {
	public static boolean debugColours = false;

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
			boid.getDesires()
					.stream()
					.sorted((a, b) -> Float.compare(b.velocity.magSq(), a.velocity.magSq()))
					.findFirst()
					.ifPresent(desire -> setColours(desire.name.hashCode(), graphics));
		} else {
			setColours(flock.hashCode(), graphics);
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
