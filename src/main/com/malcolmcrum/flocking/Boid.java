package com.malcolmcrum.flocking;

import com.malcolmcrum.flocking.Instincts.Instinct;
import processing.core.PVector;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import static processing.core.PConstants.PI;
import static processing.core.PConstants.TWO_PI;

public class Boid {
	public PVector position;
	public PVector velocity;
	public static final float fieldOfView = 3*PI/2;

	private final Collection<Instinct> instincts;
	private Collection<Desire> desires; // stored on each frame so we can read it later for debug purposes

	public Boid(PVector initialPosition, PVector initialVelocity, Collection<Instinct> instincts) {
		this.position = initialPosition;
		this.velocity = initialVelocity;
		this.instincts = instincts;
		this.desires = new HashSet<>();
	}

	void update() {
		desires = instincts.stream()
				.map(instinct -> new Desire(instinct.getClass().getSimpleName(), instinct.calculateWeightedImpulse(this), instinct.getNeighbourRadius()))
				.collect(Collectors.toSet());
		velocity.x = 0;
		velocity.y = 0;
		desires.forEach(desire -> velocity.add(desire.velocity));
		if (Float.isInfinite(velocity.x)  || Float.isNaN(velocity.x)) {
			throw new RuntimeException();
		}
		position.lerp(PVector.add(position, velocity), 0.05f);
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

		@Override
		public String toString() {
			return String.format("%s: %.0f%% (%.1f %.1f)", name, urgency * 100, velocity.x, velocity.y);
		}
	}
}
