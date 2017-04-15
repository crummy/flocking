package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.UI.DebugBoidRenderer;
import com.malcolmcrum.flocking.UI.InstinctMenu;
import com.malcolmcrum.flocking.UI.SettingsMenu;
import processing.core.PApplet;

public class Main extends PApplet {

	public static boolean isPaused = true;

	private FlockManager flocks;
	private DebugBoidRenderer debugBoidRenderer;
	private SettingsMenu settingsMenu;
	private InstinctMenu instinctMenu;

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
        flocks = new FlockManager(this);

        Flock flock = new Flock.Builder(width, height)
				.initialBoids(128)
				.withDefaultInstincts()
				.create();
		Flock enemyFlock = new Flock.Builder(width, height)
				.initialBoids(16)
				.withDefaultInstincts()
				.create();
		flocks.add(enemyFlock);
		flocks.add(flock);

		instinctMenu = new InstinctMenu(this, 14);
		settingsMenu = new SettingsMenu(this, 14);
		debugBoidRenderer = new DebugBoidRenderer(this);
	}

    @Override
    public void draw() {
		if (isPaused == false) {
			flocks.update();
		}

		clear();

		flocks.draw();
		debugBoidRenderer.draw();
		settingsMenu.draw();
		instinctMenu.draw();
	}

	@Override
	public void mouseClicked() {
		debugBoidRenderer.handleClick(mouseX, mouseY);
	}

	@Override
    public void keyReleased() {
		instinctMenu.keyReleased(key);
	}

	@Override
	public void keyPressed() {
		instinctMenu.keyPressed(key);
		settingsMenu.keyPressed(key);
	}

	public void addBoidToFlock() {

	}

	public void removeBoidFromFlock() {

	}
}
