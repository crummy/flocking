package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.Instinct;
import processing.core.PVector;

import java.util.Collection;
import java.util.HashSet;

public class Bird {
	public PVector position;
	public PVector velocity;
	private final Instinct.DesireMultipliers desireMultipliers;

	private final Collection<Instinct> instincts;

	Bird(PVector initialPosition, PVector initialVelocity, Instinct.DesireMultipliers desireMultipliers) {
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
}
