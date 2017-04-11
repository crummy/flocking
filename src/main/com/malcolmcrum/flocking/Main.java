package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.UI.DebugBoidRenderer;
import com.malcolmcrum.flocking.UI.FlockRenderer;
import com.malcolmcrum.flocking.UI.UIRenderer;
import processing.core.PApplet;

public class Main extends PApplet {

	public static boolean isPaused = true;

	private Flock flock;
	private UIRenderer ui;
	private FlockRenderer flockRenderer;
	private DebugBoidRenderer debugBoidRenderer;

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
		flock.addBoids(128);
		ui = new UIRenderer(this, flock);
		flockRenderer = new FlockRenderer(this, flock);
		debugBoidRenderer = new DebugBoidRenderer(flock, this);
	}

    @Override
    public void draw() {
		if (isPaused == false) {
			flock.update();
		}

		clear();

		flockRenderer.draw();
		debugBoidRenderer.draw();
		ui.draw();
	}

	@Override
	public void mouseClicked() {
		debugBoidRenderer.handleClick(mouseX, mouseY);
	}

	@Override
    public void keyReleased() {
		ui.keyReleased(key);
	}

	@Override
	public void keyPressed() {
		ui.keyPressed(key);
	}
}
