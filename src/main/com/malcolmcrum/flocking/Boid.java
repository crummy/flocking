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
	private Collection<Desire> desires; // stored on each frame so we can read it later for debug purposes

	Boid(PVector initialPosition, PVector initialVelocity, Set<Instinct> instincts) {
		this.position = initialPosition;
		this.velocity = initialVelocity;
		this.instincts = instincts;
	}

	void update() {
		desires = instincts.stream()
				.map(instinct -> new Desire(instinct.getClass().getName(), instinct.calculateWeightedImpulse(this), instinct.getNeighbourRadius()))
				.collect(Collectors.toSet());
		float totalUrgency = (float) desires.stream().mapToDouble(desire -> desire.urgency).sum();
		desires.forEach(desire -> {
			float balancedUrgency = desire.urgency / totalUrgency;
			velocity.lerp(desire.velocity, balancedUrgency);
		});
	}

	public Collection<Desire> getDesires() {
		return desires;
	}

	public boolean canSee(Boid other) {
		PVector towardsOther = PVector.sub(other.position, position);
		float angle = PVector.angleBetween(towardsOther, velocity);
		angle = angle < 0 ? angle + TWO_PI : angle;
		return angle < fieldOfView/2;
	}

	/**
	 * Just a wrapper class to aid in debug output (otherwise we don't know what the name of each impulse's desire is)
	 */
	public class Desire {
		public final String name;
		public final PVector velocity;
		public final float urgency;
		public final float radius;

		public Desire(String name, Instinct.Impulse impulse, float radius) {
			this.name = name;
			this.velocity = impulse.velocity;
			this.urgency = impulse.urgency;
			this.radius = radius;
		}
	}
}
