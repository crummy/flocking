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
		instincts.forEach(Instinct::update);

		List<Instinct.Desire> desires = instincts.stream()
				.filter(Instinct::isEnabled)
				.map(Instinct::getDesire)
				.collect(Collectors.toList());

		float totalStrength = (float) desires.stream().mapToDouble(d -> d.strength).sum();
		desires.stream()
				.filter(desire -> desire.strength > 0)
				.forEach(desire -> {
					Assert.assertTrue(desire.strength <= 1);
					float strengthApplied = desire.strength / totalStrength;
					velocity.lerp(desire.velocity, strengthApplied * strengthApplied);
				});
		position.lerp(PVector.add(position, velocity), 0.05f);
	}

	public Collection<Instinct> getInstincts() {
		return instincts;
	}
}
