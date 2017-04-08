package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Desires.Desire;
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
	private Collection<Desire> desires;

	Bird(PVector initialPosition, PVector initialVelocity) {
		this.position = initialPosition;
		this.velocity = initialVelocity;
		this.desires = new HashSet<>();
	}

	void addDesire(Desire desire) {
		desires.add(desire);
	}

	void update() {
		List<Desire.Change> changes = desires.stream().map(Desire::get).collect(Collectors.toList());
		changes.sort((a, b) -> Float.compare(a.desireStrength, b.desireStrength));
		PVector acceleration = changes.get(0).accelerationChange;
		velocity.add(acceleration);
		position.add(velocity);
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
