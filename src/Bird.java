import processing.core.PVector;

import java.util.Set;

import static processing.core.PApplet.lerp;
import static processing.core.PConstants.PI;
import static processing.core.PVector.angleBetween;
import static processing.core.PVector.dist;

class Bird {
	PVector position;
	PVector velocity;
	boolean isTooClose = false;

	private static final float tooCloseDistance = 16;
	private static final float minSpeed = 1;
	private static final float maxSpeed = 4;
	private static final float speedLerp = 0.5f;

	private static final float closeAngle = PI/4; // if angle between two close birds is outside this, we don't consider them close - they'll be gone soon
	private static final float avoidLerp = 0.1f;

	static final float fieldOfView = PI/2; // how wide can a bird see?

	private static final float turnTowardsLerp = 0.1f;

	Bird(PVector initialPosition, PVector initialVelocity) {
		this.position = initialPosition;
		this.velocity = initialVelocity;
	}

	private boolean isTooClose(Bird bird) {
		if (bird == this) {
			return false;
		}
		float distance = dist(position, bird.position);
		boolean tooClose = distance < tooCloseDistance;

		float velocityAngles = angleBetween(velocity, bird.velocity);
		boolean onSameCourse = velocityAngles < closeAngle;

		return tooClose && onSameCourse;
	}

	void update(Set<Bird> others, Rectangle bounds) {
		isTooClose = false;
		for (Bird bird : others) {
			if (isTooClose(bird)) {
				isTooClose = true;
				avoid(bird);
			}
			if (canSee(bird)) {
				turnTowards(bird);
			}
		}
		avoidBounds(bounds);
		updatePosition();
	}

	private void avoidBounds(Rectangle bounds) {
		if (position.x < bounds.left && velocity.x < 0) {
			velocity.x *= -1;
		} else if (position.x > bounds.right && velocity.x > 0) {
			velocity.x *= -1;
		}
		if (position.y < bounds.top && velocity.y < 0) {
			velocity.y *= -1;
		} else if (position.y > bounds.bottom && velocity.y > 0) {
			velocity.y *= -1;
		}
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
