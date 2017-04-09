# Flocking Experiment

Flocking is the behaviour that emerges when group of moving entities follow some simple rules about how to behave based on neighbours.

This project allows the entities ("birds") to flock based on "instincts" which return desires of different strengths based on the environment.
For example, if a bird has no neighbours, it won't have a strong desire to stick with its neighbours. But as a bird nears the boundary of
the screen, it will feel a stronger desire to turn around. These desires are added up, weighted, and thus the bird chooses its velocity.

## Instructions

Open in IntelliJ, open com.malcolmcrum.flocking.Main, right click and Run.

Keyboard shortcuts are available in the upper left. You can toggle individual desires, view debug colours, and pause. Click a bird (easier
when paused) to view information about its current desires.