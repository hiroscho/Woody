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
		if(Math.abs(velocityX)>Math.abs(velocityY)){
			if(velocityX>0){
				if(LevelSelect.check <= 1){
					flinged = false;
					LevelSelect.check = 1;
				}else{
					flinged = true;
					LevelSelect.check--;
					System.out.println(LevelSelect.check);
				}
			}
			if(velocityX<0){
				if(LevelSelect.check >= 3){
					flinged = false;
					LevelSelect.check = 3;
					System.out.println(LevelSelect.check);
				}else{
					flinged = true;
					LevelSelect.check++;
					System.out.println(LevelSelect.check);
				}
			}
		}
		return false; //true
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
