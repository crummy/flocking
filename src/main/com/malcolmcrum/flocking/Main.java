package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.UI.*;
import processing.core.PApplet;

import java.util.*;

public class Main extends PApplet {

	public static boolean isPaused = true;

	private List<Flock> flocks;
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
        flocks = new LinkedList<>();

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

		instinctMenu = new InstinctMenu(this, 12, flocks);
		settingsMenu = new SettingsMenu(this, 12);
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
		settingsMenu.keyReleased(key);
	}

	@Override
	public void keyPressed() {
		instinctMenu.keyPressed(key);
	}
}
