package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.UI.FlockRenderer;
import com.malcolmcrum.flocking.UI.UIRenderer;
import processing.core.PApplet;

public class Main extends PApplet {

	public static boolean isPaused = true;

	private Flock flock;
	private UIRenderer ui;
	private FlockRenderer flockRenderer;

	public static void main(String args[]) {
        PApplet.main(Main.class);
    }

    @Override
    public void settings() {
        size(1280, 800);
    }

	@Override
    public void setup() {
        clear();
		flock = new Flock(width, height);
		flock.addBirds(128);
		ui = new UIRenderer(this, flock);
		flockRenderer = new FlockRenderer(this, flock);
	}

    @Override
    public void draw() {
		if (isPaused == false) {
			flock.update();
		}

		clear();

		flockRenderer.draw();
		ui.draw();
	}

	@Override
	public void mouseClicked() {
		flockRenderer.handleClick(mouseX, mouseY);
	}

	@Override
    public void keyReleased() {
		ui.keyReleased(key);
	}
}
