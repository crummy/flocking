package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.Instinct;
import processing.core.PVector;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static processing.core.PApplet.lerp;
import static processing.core.PConstants.PI;
import static processing.core.PVector.angleBetween;

public class Bird {
	public PVector position;
	public PVector velocity;
	boolean isTooClose = false;

	private static final float minSpeed = 1;
	private static final float maxSpeed = 4;
	private static final float speedLerp = 0.5f;

	private static final float avoidLerp = 0.1f;

	static final float fieldOfView = PI/2; // how wide can a bird see?

	private static final float turnTowardsLerp = 0.1f;
	private Collection<Instinct> instincts;

	Bird(PVector initialPosition, PVector initialVelocity) {
		this.position = initialPosition;
		this.velocity = initialVelocity;
		this.instincts = new HashSet<>();
	}

	void addDesire(Instinct instinct) {
		instincts.add(instinct);
	}

	void update() {
		List<Instinct.Desire> desires = this.instincts.stream().map(Instinct::get).collect(Collectors.toList());
		float totalStrength = (float)desires.stream().mapToDouble(d -> d.strength).sum();
		desires.forEach(desire -> {
			float strengthApplied = desire.strength/totalStrength;
			velocity.lerp(PVector.add(velocity, desire.acceleration), strengthApplied);
		});
		position.lerp(PVector.add(position, velocity), 0.01f);
	}

	private void turnTowards(Bird bird) {
		velocity.lerp(bird.velocity, turnTowardsLerp);
	}

	private boolean canSee(Bird bird) {
		PVector towardsBird = PVector.sub(position, bird.position);
		return angleBetween(velocity, towardsBird) < fieldOfView/2;
	}

	private void avoid(Bird bird) {
		float angle = angleBetween(velocity, bird.velocity);
		PVector vectorAway = PVector.fromAngle(angle);
		velocity.lerp(vectorAway, avoidLerp);
	}

	private void updatePosition() {
		softClampVelocity();
		position.add(velocity);
	}

	private void softClampVelocity() {
		float speed = velocity.mag();
		if (speed > maxSpeed) {
			float newSpeed = lerp(speed, maxSpeed, speedLerp);
			velocity.setMag(newSpeed);
		} else if (speed < minSpeed) {
			float newSpeed = lerp(speed, minSpeed, speedLerp);
			velocity.setMag(newSpeed);
		}
	}
}
