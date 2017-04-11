package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.Instinct;
import processing.core.PVector;

import java.util.Collection;
import java.util.HashSet;

import static processing.core.PConstants.PI;
import static processing.core.PConstants.TWO_PI;

public class Boid {
	public PVector position;
	public PVector velocity;
	private final Instinct.DesireMultipliers desireMultipliers;
	private final float fieldOfView = 3*PI/2;

	private final Collection<Instinct> instincts;

	Boid(PVector initialPosition, PVector initialVelocity, Instinct.DesireMultipliers desireMultipliers) {
		this.position = initialPosition;
		this.velocity = initialVelocity;
		this.desireMultipliers = desireMultipliers;
		this.instincts = new HashSet<>();
	}

	void addDesire(Instinct instinct) {
		instincts.add(instinct);
	}

	void update() {
		instincts.forEach(Instinct::update);
		float totalUrgency = (float) instincts.stream()
				.mapToDouble(Instinct::getUrgency)
				.sum();
		instincts.stream()
				.filter(instinct -> instinct.getUrgency() > 0)
				.forEach(instinct -> {
					float desireMultiplier = desireMultipliers.get(instinct.getClass());
					float balancedUrgency = (instinct.getUrgency() * desireMultiplier) / totalUrgency;
					velocity.lerp(instinct.getDesiredVelocity(), balancedUrgency);
				});
		position.lerp(PVector.add(position, velocity), 0.05f);
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
