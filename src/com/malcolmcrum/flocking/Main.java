package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Renderers.FlockRenderer;
import com.malcolmcrum.flocking.Renderers.UIRenderer;
import processing.core.PApplet;

public class Main extends PApplet {

	static boolean isPaused = true;

	private Flock flock;
	private InputHandler inputHandler;
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
		inputHandler = new InputHandler(this);
		flock = new Flock(width, height);
		flock.addBirds(32);
		ui = new UIRenderer(this);
		flockRenderer = new FlockRenderer(this, flock);
    }

    @Override
    public void draw() {
		if (isPaused == false) {
			flock.update();
		}

		clear();

		ui.draw();
		flockRenderer.draw();
	}

	@Override
	public void mouseClicked() {
		flockRenderer.handleClick(mouseX, mouseY);
	}

	@Override
    public void keyReleased() {
		inputHandler.keyReleased(key);
	}
}
