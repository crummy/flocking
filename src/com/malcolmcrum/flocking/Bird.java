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
	private int tick = 0;

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

		tick++;
		if (tick % 10 == 0) {
			float totalStrength = (float) desires.stream().mapToDouble(d -> d.strength).sum();
			desires.forEach(desire -> {
				float strengthApplied = desire.strength / totalStrength;
				velocity.lerp(desire.velocity, strengthApplied * strengthApplied);
			});
		}
		position.lerp(PVector.add(position, velocity), 0.05f);
	}
}
