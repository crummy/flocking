package com.malcolmcrum.flocking.Instincts;

import com.malcolmcrum.flocking.Bird;
import processing.core.PVector;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by crummy on 08.04.17.
 */
public class MoveTowardsNearby implements Instinct {
	private final Bird self;
	private final Set<Bird> allBirds;

	public MoveTowardsNearby(Bird self, Set<Bird> allBirds) {
		this.self = self;

		this.allBirds = allBirds;
	}

	@Override
	public Desire get() {
		PVector center = new PVector();
		for (Bird bird : getOthers()) {
			center.add(bird.position);
		}
		center.div(getOthers().size());
		return new Desire(0.1f, PVector.sub(center, self.position));
	}

	private Set<Bird> getOthers() {
		return allBirds.stream().filter(bird -> bird != self).collect(Collectors.toSet());
	}

	@Override
	public String toString() {
		return "MoveTowardsNearby";
	}
}
