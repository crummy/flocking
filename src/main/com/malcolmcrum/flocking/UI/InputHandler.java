package com.malcolmcrum.flocking.UI;

public interface InputHandler {

	default void handleClick(int x, int y) {}

	default void keyPressed(char key) {
		switch (Character.toUpperCase(key)) {
		case 'A':
			leftPressed();
			break;
		case 'D':
			rightPressed();
			break;
		case 'W':
			upPressed();
			break;
		case 'S':
			downPressed();
			break;
		}
	}

	default void downPressed() {}

	default void upPressed() {}

	default void rightPressed() {}

	default void leftPressed() {}
}
