package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.Instinct;
import processing.core.PVector;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static processing.core.PConstants.PI;
import static processing.core.PConstants.TWO_PI;

public class Boid {
	public PVector position;
	public PVector velocity;
	public static final float fieldOfView = 3*PI/2;

	private final Collection<Instinct> instincts;

	Boid(PVector initialPosition, PVector initialVelocity, Set<Instinct> instincts) {
		this.position = initialPosition;
		this.velocity = initialVelocity;
		this.instincts = instincts;
	}

	void update() {
		Collection<Instinct.Desire> desires = instincts.stream()
				.map(instinct -> instinct.calculateWeightedDesire(this))
				.collect(Collectors.toSet());
		float totalUrgency = (float)desires.stream().mapToDouble(desire -> desire.urgency).sum();
		desires.forEach(desire -> {
			float balancedUrgency = desire.urgency / totalUrgency;
			velocity.lerp(desire.velocity, balancedUrgency);
		});
	}

	public Collection<Instinct> getInstincts() {
		return instincts;
	}

	public boolean canSee(Boid other) {
		PVector towardsOther = PVector.sub(other.position, position);
		float angle = PVector.angleBetween(towardsOther, velocity);
		angle = angle < 0 ? angle + TWO_PI : angle;
		return angle < fieldOfView/2;
	}
}
