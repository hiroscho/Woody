package de.woody.game;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

import de.woody.game.screens.LevelSelect;

public class Swipe implements GestureListener {

	public boolean flinged = false;
	public float rightone = 0, leftone = 0;

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if (Math.abs(velocityX) > Math.abs(velocityY)) {
			if (velocityX > 10) { // 0
				LevelSelect.getInstance().check--;
				if (LevelSelect.getInstance().checking()) {
					flinged = false;
//					System.out.println("RECHTS STOP STOP");
				} else {
					flinged = true;
//					System.out.println("RECHTS " + LevelSelect.getInstance().check);
					return true;
				}
			} else if (velocityX < -10) { // 0
				LevelSelect.getInstance().check++;
				if (LevelSelect.getInstance().checking()) {
//					System.out.println("LINKS STOP STOP");
					flinged = false;
				} else {
					flinged = true;
//					System.out.println("LINKS " + LevelSelect.getInstance().check);
					return true;
				}
			} else {
				flinged = false;
			}
		} else {

			// Ignore the input, because we don't care about up/down swipes.
		}
		return false; // true
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pinchStop() {
		// TODO Auto-generated method stub

	}

}
