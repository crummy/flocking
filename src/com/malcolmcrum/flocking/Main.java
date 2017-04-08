package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Renderers.BirdRenderer;
import com.malcolmcrum.flocking.Renderers.UIRenderer;
import processing.core.PApplet;

public class Main extends PApplet {

	static boolean isPaused = true;

	private Flock flock;
	private Config config;
	private UIRenderer ui;
	private BirdRenderer birdRenderer;

	public static void main(String args[]) {
        PApplet.main(Main.class);
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
		ui = new UIRenderer(this);
		birdRenderer = new BirdRenderer(this, flock);
    }

    @Override
    public void draw() {
		if (isPaused == false) {
			flock.update();
		}

		clear();

		ui.draw();
		birdRenderer.draw();


	}

	@Override
	public void mouseClicked() {
		birdRenderer.handleClick(mouseX, mouseY);
	}

	@Override
	public void clear() {
		stroke(255);
		fill(255);
    	stroke(0, 150f);
		fill(0, 150f);
		rect(0, 0, width, height);
	}

	@Override
    public void keyReleased() {
		config.keyReleased(key);
	}
}
