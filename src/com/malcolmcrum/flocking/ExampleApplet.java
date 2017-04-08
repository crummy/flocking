package com.malcolmcrum.flocking;

import processing.core.*;

public class ExampleApplet extends PApplet {

    private Flock flock;

    public static void main(String args[]) {
        PApplet.main("com.malcolmcrum.flocking.ExampleApplet");
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
        flock = new Flock(width, height);
        flock.addBirds(32);
    }

    @Override
    public void draw() {
        // TODO: Do your drawing for each frame here
        clear();
        stroke(255);
        fill(255);
        flock.update();
        flock.getBirds().forEach(this::draw);
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