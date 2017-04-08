package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.*;
import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet {

    private Flock flock;
	private Config config;
	static boolean isPaused = true;
	private Bird selectedBird = null;

	public static void main(String args[]) {
        PApplet.main("com.malcolmcrum.flocking.Main");
    }

    @Override
    public void settings() {
        // TODO: Customize screen size and so on here
        size(1280, 800);
    }

	@Override
    public void setup() {
        // TODO: Your custom drawing and setup on applet start belongs here
        clear();
		config = new Config();
		flock = new Flock(width, height);
		flock.addBirds(32);
    }

    @Override
    public void draw() {
		if (isPaused == false) {
			flock.update();
		}

		clear();

		drawInstructions();
		if (selectedBird != null) {
			drawDebug(selectedBird);
		}
		flock.getBirds().forEach(this::draw);
	}

	private void drawDebug(Bird bird) {
 		for (Instinct instinct : bird.getInstincts()) {
 			stroke(0, 0, 255);
			ellipse(bird.position.x, bird.position.y, instinct.getNeighbourRadius(), instinct.getNeighbourRadius());
		}
	}

	@Override
	public void mouseClicked() {
    	float minimumDistance = 8;
    	PVector click = new PVector(mouseX, mouseY);
    	selectedBird = flock.getBirds().stream()
				.filter(bird -> PVector.dist(bird.position, click) < minimumDistance)
				.sorted((a, b) -> Float.compare(PVector.dist(a.position, click), PVector.dist(b.position, click)))
				.findFirst()
				.orElse(null);
	}

	@Override
	public void clear() {
		stroke(255);
		fill(255);
    	stroke(0, 150f);
		fill(0, 150f);
		rect(0, 0, width, height);
	}

	private void drawInstructions() {
		text("1. AvoidBoundaries: " + AvoidBoundaries.isEnabled, 4, 10);
		text("2. AvoidOthers: " + AvoidOthers.isEnabled, 4, 20);
		text("3. ClampSpeed: " + ClampSpeed.isEnabled, 4, 30);
		text("4. Cohesion: " + Cohesion.isEnabled, 4, 40);
		text("5. Random: " + Random.isEnabled, 4, 50);
		text("Space: Pause", 4, 60);
	}

	@Override
    public void keyReleased() {
		config.keyReleased(key);
	}

    private void draw(Bird bird) {
        int width = 8;
        int height = 12;
        pushMatrix();
        if (bird.isTooClose) {
            fill(255, 0, 0);
        } else {
            fill(255, 255);
        }
        translate(bird.position.x, bird.position.y);
        rotate(bird.velocity.heading() + PI/2);
        triangle(0, height, width/2, 0, width, height);
        popMatrix();
    }
}
