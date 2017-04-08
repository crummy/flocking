package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.Instinct;
import processing.core.PVector;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Bird {
	public PVector position;
	public PVector velocity;
	boolean isTooClose = false;

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
		List<Instinct.Desire> desires = this.instincts.stream()
				.filter(Instinct::isEnabled)
				.map(Instinct::get)
				.collect(Collectors.toList());

		float totalStrength = (float) desires.stream().mapToDouble(d -> d.strength).sum();
		desires.forEach(desire -> {
			if (desire.strength > 1) {
				System.out.println("WARNING! Desire " + desire + " strength is " + desire.strength);
			} else if (desire.strength > 0) {
				float strengthApplied = desire.strength / totalStrength;
				velocity.lerp(desire.velocity, strengthApplied * strengthApplied);
			}
		});
		position.lerp(PVector.add(position, velocity), 0.05f);
	}
}
