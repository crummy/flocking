package com.malcolmcrum.flocking.UI;

public interface InputHandler {

	default void keyReleased(char key) {
		switch (Character.toUpperCase(key)) {
		case 'A':
			handleLeft();
			break;
		case 'D':
			handleRight();
			break;
		case 'W':
			handleUp();
			break;
		case 'S':
			handleDown();
			break;
		}
	}

	default void handleUp() {}

	default void handleDown() {}

	default void handleRight() {}

	default void handleLeft() {}

	default void handleClick(int x, int y) {}
}
