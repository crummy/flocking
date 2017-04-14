package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.UI.DebugBoidRenderer;
import com.malcolmcrum.flocking.UI.FlockRenderer;
import com.malcolmcrum.flocking.UI.UIRenderer;
import processing.core.PApplet;

import java.util.LinkedList;
import java.util.List;

public class Main extends PApplet {

	public static boolean isPaused = true;

	private List<Flock> flocks;
	private UIRenderer ui;
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
        flocks = new LinkedList<>();

        Flock flock = new Flock(width, height);
		Flock enemyFlock = new Flock(width, height);
		flock.addBoids(128);
		enemyFlock.addBoids(16);
		flocks.add(enemyFlock);
		flocks.add(flock);

		ui = new UIRenderer(this, flocks);
		debugBoidRenderer = new DebugBoidRenderer(this, flocks);
	}

    @Override
    public void draw() {
		if (isPaused == false) {
			flocks.forEach(Flock::update);
		}

		clear();

		flocks.forEach(flock -> new FlockRenderer(this, flock).draw());
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
